package com.example.contabancaria.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contabancaria.classes.Pix;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPix extends SQLiteOpenHelper {

    public RepositorioPix(@Nullable Context context) {
        super(context, "banco_contas", null, 1); // Compartilhando o mesmo banco
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPixTable = "CREATE TABLE pix (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "conta_id INTEGER, " +
                "chave TEXT NOT NULL, " +
                "tipo_chave TEXT NOT NULL, " +
                "FOREIGN KEY(conta_id) REFERENCES conta(id) ON DELETE CASCADE" +
                ")";
        db.execSQL(createPixTable);
        Log.i("RepositorioPix", "Tabela pix criada.");
    }

    public void adicionarChavesPix(Pix pix, int contaId) {
        SQLiteDatabase db = this.getWritableDatabase();

            String sqlInsertPix = "INSERT INTO pix (conta_id, chave, tipo_chave) VALUES (" +
                    contaId + ", '" + pix.getChave() + "', '" + pix.getTipoChave() + "')";
            db.execSQL(sqlInsertPix);
        Log.i("RepositorioPix", "Chaves Pix adicionadas.");
    }

    public Pix buscarChavePix(String chavePix) {
        SQLiteDatabase db = this.getReadableDatabase();
        Pix pix = null;


        String query = "SELECT * FROM pix WHERE chave = ?";
        Cursor cursor = db.rawQuery(query, new String[]{chavePix});

        try {
            if (cursor != null && cursor.moveToFirst()) {

                int contaId = cursor.getInt(cursor.getColumnIndexOrThrow("conta_id"));
                String tipoChave = cursor.getString(cursor.getColumnIndexOrThrow("tipo_chave"));
                String chave = cursor.getString(cursor.getColumnIndexOrThrow("chave"));


                pix = new Pix(contaId, chave, tipoChave);
                Log.i("Pix:", pix.toString());
            }
        } catch (Exception e) {
            Log.e("RepositorioPix", "Erro ao buscar chave Pix: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return pix;
    }

    public boolean chavePixJaExiste(String chavePix) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean existeChave = false;

        String query = "SELECT 1 FROM pix WHERE chave = ?";
        try {
            cursor = db.rawQuery(query, new String[]{chavePix});

            // Verifica se o cursor retornou algum resultado
            if (cursor != null && cursor.moveToFirst()) {
                existeChave = true;
            }
        } catch (Exception e) {
            Log.e("RepositorioPix", "Erro ao buscar chave Pix: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return existeChave;
    }


    public List<Pix> listarChavesPix(int contaId) {
        List<Pix> pixList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = "SELECT conta_id, chave, tipo_chave FROM pix WHERE conta_id = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(contaId)});

        if (cursor.moveToFirst()) {
            do {
                int conta_id = cursor.getInt(cursor.getColumnIndexOrThrow("conta_id"));
                String chave = cursor.getString(cursor.getColumnIndexOrThrow("chave"));
                String tipoChave = cursor.getString(cursor.getColumnIndexOrThrow("tipo_chave"));
                Pix pix = new Pix(conta_id, chave, tipoChave);
                pixList.add(pix);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        Log.i("RepositorioPix", "Lista de chaves Pix carregada.");
        return pixList;
    }

    public void excluirChavePix(String chave) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlDeletePix = "DELETE FROM pix WHERE chave = ?";
        db.execSQL(sqlDeletePix, new String[]{chave});

        db.close();
        Log.i("RepositorioPix", "Chave Pix '" + chave + "' excluída.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pix");
        onCreate(db);
    }

    public void resetarBanco(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS extrato");
        db.execSQL("DROP TABLE IF EXISTS pix");
        db.execSQL("DROP TABLE IF EXISTS conta");

        onCreate(db); // Recria as tabelas
        Log.i("BancoDeDados", "Banco de dados resetado.");
    }
}
