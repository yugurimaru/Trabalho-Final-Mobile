package com.example.contabancaria;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ExtratoActivity extends AppCompatActivity {
    private Classe_conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extrato);

        conta = (Classe_conta) getIntent().getSerializableExtra("conta");
    }


    public void Atualizar(View view) {

        Log.i("Extrato","Saldo: "+ conta.getSaldo());
        //Log.i("Extrato","Operecao: "+ conta.getExtrato().getOperacao()+"\nSaldo Antigo: "+conta.getExtrato().getSaldoAntigo());

    }
}