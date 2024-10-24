package com.example.contabancaria;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.Extrato;
import com.example.contabancaria.ExtratoAdapter;

import java.util.List;

public class ExtratoActivity extends AppCompatActivity {
    private Conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);

        // Obtém a conta passada pela Intent
        conta = (Conta) getIntent().getSerializableExtra("conta");

        // Referência ao ListView
        ListView listView = findViewById(R.id.listViewListagem);

        // Obter a lista de extratos da conta
        List<Extrato> extratoList = conta.getExtrato();

        // Verifique se a lista está vazia e configure o adapter
        if (extratoList.isEmpty()) {
            Log.i("Extrato", "Nenhuma transacao encontrada.");
        } else {
            // Cria e configura o adapter
            ExtratoAdapter adapter = new ExtratoAdapter(this, extratoList);
            listView.setAdapter(adapter);
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
}
