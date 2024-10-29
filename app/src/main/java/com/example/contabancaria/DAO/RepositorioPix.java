package com.example.contabancaria.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contabancaria.classes.Pix;

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

    public void adicionarChavesPix(SQLiteDatabase db, int contaId, List<Pix> pixList) {
        for (Pix pix : pixList) {
            String sqlInsertPix = "INSERT INTO pix (conta_id, chave, tipo_chave) VALUES (" +
                    contaId + ", '" + pix.getChave() + "', '" + pix.getTipoChave() + "')";
            db.execSQL(sqlInsertPix);
        }
        Log.i("RepositorioPix", "Chaves Pix adicionadas.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pix");
        onCreate(db);
    }
}
