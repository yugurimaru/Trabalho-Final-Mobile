package com.example.contabancaria.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contabancaria.classes.Conta;

public class RepositorioConta extends SQLiteOpenHelper {
    private SQLiteDatabase db;
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

    public Conta buscarContaPorId(int id) {
        db = this.getReadableDatabase();
        Conta conta = null;

        // Consulta para buscar a conta pelo ID
        Cursor cursor = db.query("conta", new String[]{"id", "saldo", "usuario", "senha"},
                "id=?", new String[]{String.valueOf(id)},
                null, null, null);

        // Verifica se há resultados
        if (cursor != null && cursor.moveToFirst()) {
            // Cria uma nova Conta a partir dos dados do cursor
            int contaId = cursor.getInt(cursor.getColumnIndex("id"));
            double saldo = cursor.getDouble(cursor.getColumnIndex("saldo"));
            String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
            String senha = cursor.getString(cursor.getColumnIndex("senha"));

            conta = new Conta(contaId, saldo, usuario, senha);
        }

        // Fecha o cursor e retorna a conta
        if (cursor != null) {
            cursor.close();
        }

        return conta;
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