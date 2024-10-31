package com.example.contabancaria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.DAO.RepositorioExtrato;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;
import com.example.contabancaria.classes.Extrato;

import java.io.Serializable;

import com.example.contabancaria.DAO.RepositorioExtrato;

public class DepositarActivity extends AppCompatActivity {
    private Conta conta;
    private RepositorioExtrato repositorioExtrato;
    private RepositorioConta repositorioConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_depositar);

        conta = (Conta) getIntent().getSerializableExtra("conta");
        repositorioExtrato = new RepositorioExtrato(this);
        repositorioConta = new RepositorioConta(this);
    }

    public void Confirmar(View view) {

        EditText valor = findViewById(R.id.editText_depositar);

        try {
            double valor_double = Double.parseDouble(valor.getText().toString());

            conta.depositar(valor_double, repositorioExtrato, repositorioConta);

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("conta", conta);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            Log.i("Depositar", "Erro no parse");
        }
    }
}
