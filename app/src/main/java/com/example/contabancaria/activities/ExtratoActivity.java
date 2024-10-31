package com.example.contabancaria.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioExtrato;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;
import com.example.contabancaria.classes.Extrato;

import java.util.List;

public class ExtratoActivity extends AppCompatActivity {
    private Conta conta;
    private RepositorioExtrato repositorioExtrato; // Adiciona uma referência ao repositório

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);

        // Obtém a conta passada pela Intent
        conta = (Conta) getIntent().getSerializableExtra("conta");

        // Inicializa o repositório de extrato
        repositorioExtrato = new RepositorioExtrato(this);

        // Referência ao ListView
        ListView listView = findViewById(R.id.listViewListagem);

        // Obter a lista de extratos da conta através do repositório
        List<Extrato> extratoList = repositorioExtrato.listarExtratos(conta.getId());

        // Verifique se a lista está vazia e configure o adapter
        if (extratoList.isEmpty()) {
            Log.i("Extrato", "Nenhuma transacao encontrada.");
        } else {
            // Cria e configura o adapter
            ExtratoAdapter adapter = new ExtratoAdapter(this, extratoList);
            listView.setAdapter(adapter);
        }
    }
}


//    public void Atualizar(View view) {
//        // Log de atualização de extrato
//        if (conta.getExtrato().isEmpty()) {
//            Log.i("Extrato", "Nenhuma transacao encontrada.");
//        } else {
//            for (Extrato transacao : conta.getExtrato()) {
//                Log.i("Extrato", "Tipo transacao: " + transacao.getTipoTransacao());
//                Log.i("Extrato", "Valor: " + transacao.getValor());
//                Log.i("Extrato", "Saldo apos transacao: " + transacao.getSaldoAtual());
//            }
//        }
//    }

