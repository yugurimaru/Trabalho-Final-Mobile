package com.example.contabancaria.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioExtrato;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Extrato;

import java.util.List;

public class ExtratoActivity extends AppCompatActivity {
    private int contaId;
    private RepositorioExtrato repositorioExtrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);
        setTitle("Extrato");

        contaId = getIntent().getIntExtra("CONTA_ID", -1);
        repositorioExtrato = new RepositorioExtrato(this);

        ListView listView = findViewById(R.id.listViewListagem);
        List<Extrato> extratoList = repositorioExtrato.listarExtratos(contaId);

        if (extratoList.isEmpty()) {
            Log.i("ExtratoActivity", "Nenhuma transação encontrada.");
        } else {
            ExtratoAdapter adapter = new ExtratoAdapter(this, extratoList);
            listView.setAdapter(adapter);
        }
    }
}
