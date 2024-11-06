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
        repositorioPix = new RepositorioPix(context);
        repositorioExtrato = new RepositorioExtrato(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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

        repositorioPix.onCreate(db);
        repositorioExtrato.onCreate(db);
    }

    public void adicionarConta(Conta conta) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String sqlInsertConta = "INSERT INTO conta (usuario, senha, saldo) VALUES ('" +
                    conta.getUsuario() + "', '" + conta.getSenha() + "', " + conta.getSaldo() + ")";
            db.execSQL(sqlInsertConta);
            Log.i("RepositorioConta", "Conta inserida.");

            int contaId = getLastInsertedId(db);
            Log.i("RepositorioConta", "Último ID inserido: " + contaId);
        } finally {
            db.close();
        }
    }

    public void atualizarSaldo(int id, double saldo) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String sqlUpdateConta = "UPDATE conta SET saldo = ? WHERE id = ?";
            db.execSQL(sqlUpdateConta, new Object[]{saldo, id});
            Log.i("RepositorioConta", "Saldo atualizado para o ID: " + id);
        } catch (Exception e) {
            Log.e("RepositorioConta", "Erro ao atualizar saldo: ", e);
        } finally {
            db.close();
        }
    }

    public Conta buscarContaPorUsuarioSenha(String usuario, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Conta conta = null;
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM conta WHERE usuario = ? AND senha = ?";
            cursor = db.rawQuery(query, new String[]{usuario, senha});

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                double saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("saldo"));
                conta = new Conta(id, usuario, senha, saldo, repositorioExtrato, this, repositorioPix);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return conta;
    }

    public Conta buscarContaPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Conta conta = null;
        Cursor cursor = null;
        try {
            cursor = db.query("conta", new String[]{"id", "saldo", "usuario", "senha"},
                    "id=?", new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int contaId = cursor.getInt(cursor.getColumnIndex("id"));
                double saldo = cursor.getDouble(cursor.getColumnIndex("saldo"));
                String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
                String senha = cursor.getString(cursor.getColumnIndex("senha"));

                conta = new Conta(contaId, usuario, senha, saldo, repositorioExtrato, this, repositorioPix);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return conta;
    }

    private int getLastInsertedId(SQLiteDatabase db) {
        return (int) db.compileStatement("SELECT last_insert_rowid()").simpleQueryForLong();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS conta");
        repositorioPix.onUpgrade(db, oldVersion, newVersion);
        repositorioExtrato.onUpgrade(db, oldVersion, newVersion);
        onCreate(db);
    }
}
