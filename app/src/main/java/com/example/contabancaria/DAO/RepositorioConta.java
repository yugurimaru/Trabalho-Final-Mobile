package com.example.contabancaria.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contabancaria.classes.Conta;

public class RepositorioConta extends SQLiteOpenHelper {

    private RepositorioPix repositorioPix;
    private RepositorioExtrato repositorioExtrato;

    public RepositorioConta(@Nullable Context context) {
        super(context, "banco_contas", null, 1);
        // Instanciando repositórios auxiliares
        repositorioPix = new RepositorioPix(context);
        repositorioExtrato = new RepositorioExtrato(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criando a tabela Conta
        String createContaTable = "CREATE TABLE conta (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario TEXT NOT NULL, " +
                "senha TEXT NOT NULL, " +
                "saldo REAL NOT NULL)";
        db.execSQL(createContaTable);
        Log.i("RepositorioConta", "Tabela conta criada.");

        String sqlInsertAdmin = "INSERT INTO conta (usuario, senha, saldo) VALUES ('admin', '123', 0.0)";
        db.execSQL(sqlInsertAdmin);
        Log.i("RepositorioConta", "Conta admin criada.");

        // Criando as tabelas Pix e Extrato no repositório correspondente
        repositorioPix.onCreate(db);
        repositorioExtrato.onCreate(db);
    }

    public void adicionarConta(Conta conta) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Inserir a Conta
        String sqlInsertConta = "INSERT INTO conta (usuario, senha, saldo) VALUES ('" +
                conta.getUsuario() + "', '" + conta.getSenha() + "', " + conta.getSaldo() + ")";
        db.execSQL(sqlInsertConta);
        Log.i("RepositorioConta", "Conta inserida.");

        // Obter o ID da conta recém-criada
        int contaId = getLastInsertedId(db);

        // Delegar inserção de Pix e Extrato aos respectivos repositórios
        repositorioPix.adicionarChavesPix(db, contaId, conta.getPix());
        repositorioExtrato.adicionarExtrato(db, contaId, conta.getExtrato());

        Log.i("RepositorioConta", "Conta, Pix e Extrato inseridos.");
    }

    public Conta buscarContaPorUsuarioSenha(String usuario, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM conta WHERE usuario = ? AND senha = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuario, senha});

        if (cursor.moveToFirst()) {
            // Usuário encontrado
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            double saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("saldo"));
            Conta conta = new Conta(id, saldo, usuario, senha);
            cursor.close();
            return conta;
        }

        // Usuário não encontrado
        cursor.close();
        return null;
    }

    private int getLastInsertedId(SQLiteDatabase db) {
        return (int) db.compileStatement("SELECT last_insert_rowid()").simpleQueryForLong();
    }

    public int obterProximoId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(id) FROM conta";  // Supondo que a tabela seja "contas"
        Cursor cursor = db.rawQuery(query, null);

        int proximoId = 1; // Valor inicial

        if (cursor.moveToFirst()) {
            int maiorId = cursor.getInt(0);
            proximoId = maiorId + 1;
        }

        cursor.close();
        return proximoId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS conta");
        repositorioPix.onUpgrade(db, oldVersion, newVersion);
        repositorioExtrato.onUpgrade(db, oldVersion, newVersion);
        onCreate(db);
    }
}