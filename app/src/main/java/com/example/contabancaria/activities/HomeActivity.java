package com.example.contabancaria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

public class HomeActivity extends AppCompatActivity {
    private int contaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        contaId = getIntent().getIntExtra("CONTA_ID", -1);

        if (contaId != -1) {
            RepositorioConta repositorioConta = new RepositorioConta(this);
            Conta conta = repositorioConta.buscarContaPorId(contaId);

            if (conta != null) {
                TextView welcoming = findViewById(R.id.textView_home);
                welcoming.setText(String.format("Seja bem-vindo, %s", conta.getUsuario()));
            } else {
                Log.e("HomeActivity", "Conta não encontrada no banco de dados");
            }
        } else {
            Log.e("HomeActivity", "ID da conta invalido");
        }
    }

    public void depositar(View view) {
        Intent intent = new Intent(this, DepositarActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);
    }

    public void retirar(View view) {
        Intent intent = new Intent(this, RetirarActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);
    }

    public void extrato(View view) {
        Intent intent = new Intent(this, ExtratoActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);
    }

    public void pix(View view) {
        Intent intent = new Intent(this, HomePixActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);
    }

    public void sair(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
