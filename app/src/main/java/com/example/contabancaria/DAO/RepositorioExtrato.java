package com.example.contabancaria.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contabancaria.classes.Extrato;

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

    public void adicionarExtrato(SQLiteDatabase db, int contaId, List<Extrato> extratoList) {
        for (Extrato extrato : extratoList) {
            String sqlInsertExtrato = "INSERT INTO extrato (conta_id, tipo_transacao, valor, saldo_atual) VALUES (" +
                    contaId + ", '" + extrato.getTipoTransacao() + "', " + extrato.getValor() + ", " + extrato.getSaldoAtual() + ")";
            db.execSQL(sqlInsertExtrato);
        }
        Log.i("RepositorioExtrato", "Transações de Extrato adicionadas.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS extrato");
        onCreate(db);
    }
}
