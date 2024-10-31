package com.example.contabancaria.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contabancaria.classes.Extrato;

import java.util.ArrayList;
import java.util.List;

public class RepositorioExtrato extends SQLiteOpenHelper {

    public RepositorioExtrato(@Nullable Context context) {
        super(context, "banco_contas", null, 1); // Compartilhando o mesmo banco
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createExtratoTable = "CREATE TABLE extrato (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "conta_id INTEGER, " +
                "tipo_transacao TEXT NOT NULL, " +
                "valor REAL NOT NULL, " +
                "saldo_atual REAL NOT NULL, " +
                "FOREIGN KEY(conta_id) REFERENCES conta(id) ON DELETE CASCADE" +
                ")";
        db.execSQL(createExtratoTable);
        Log.i("RepositorioExtrato", "Tabela extrato criada.");
    }

    // Novo método para adicionar um único extrato
    public void adicionarExtrato(Extrato extrato, int contaId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlInsertExtrato = "INSERT INTO extrato (conta_id, tipo_transacao, valor, saldo_atual) VALUES (" +
                contaId + ", '" + extrato.getTipoTransacao() + "', " + extrato.getValor() + ", " + extrato.getSaldoAtual() + ")";
        db.execSQL(sqlInsertExtrato);

        Log.i("RepositorioExtrato", "Transação de Extrato adicionada.");
    }

    public List<Extrato> listarExtratos(int contaId) {
        List<Extrato> extratos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para selecionar todos os extratos da conta específica
        String sqlSelectExtratos = "SELECT tipo_transacao, valor, saldo_atual FROM extrato WHERE conta_id = ?";
        Cursor cursor = db.rawQuery(sqlSelectExtratos, new String[]{String.valueOf(contaId)});

        if (cursor.moveToFirst()) {
            do {
                String tipoTransacao = cursor.getString(cursor.getColumnIndex("tipo_transacao"));
                double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
                double saldoAtual = cursor.getDouble(cursor.getColumnIndex("saldo_atual"));

                // Criação do objeto Extrato e adição à lista
                Extrato extrato = new Extrato(tipoTransacao, valor, saldoAtual);
                extratos.add(extrato);
            } while (cursor.moveToNext());
        }

        cursor.close(); // Fechar o cursor
        db.close(); // Fechar o banco de dados

        Log.i("RepositorioExtrato", "Lista de extratos obtida.");
        return extratos; // Retornar a lista de extratos
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS extrato");
        onCreate(db);
    }
}

