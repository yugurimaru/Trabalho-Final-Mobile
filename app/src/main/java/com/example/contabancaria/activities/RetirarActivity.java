package com.example.contabancaria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.DAO.RepositorioExtrato;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

public class RetirarActivity extends AppCompatActivity {
    private Conta conta;
    private int contaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retirar);
        setTitle("Retirar");

        contaId = getIntent().getIntExtra("CONTA_ID", -1);

        if (contaId != -1) {
            RepositorioConta repositorioConta = new RepositorioConta(this);
            conta = repositorioConta.buscarContaPorId(contaId);

            if (conta == null) {
                Log.e("DepositarActivity", "Conta não encontrada");
                finish();
            }
        } else {
            Log.e("DepositarActivity", "ID da conta invalido");
            finish();
        }
    }

    public void Confirmar(View view) {
        EditText ETvalor = findViewById(R.id.editTextNumber_retirar);

        try {
            double valor = Double.parseDouble(ETvalor.getText().toString());

            conta.retirar(valor);

            Toast.makeText(this, "Retirada realizada com sucesso", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("CONTA_ID", contaId);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            //Log.i("RetirarActivity", "Erro no parse");
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
        }
    }
}
