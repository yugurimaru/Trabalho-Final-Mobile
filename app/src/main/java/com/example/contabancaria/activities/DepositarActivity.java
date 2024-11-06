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
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

public class DepositarActivity extends AppCompatActivity {
    private Conta conta;
    private int contaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_depositar);
        setTitle("Depositar");

        contaId = getIntent().getIntExtra("CONTA_ID", -1);

        if (contaId != -1) {
            RepositorioConta repositorioConta = new RepositorioConta(this);
            conta = repositorioConta.buscarContaPorId(contaId);

        } else {
            Log.e("DepositarActivity", "ID da conta inválido");
            finish();
        }
    }

    public void Confirmar(View view) {
        EditText ETvalor = findViewById(R.id.editText_depositar);

        try {
            double valor = Double.parseDouble(ETvalor.getText().toString());

            conta.depositar(valor);

            Toast.makeText(this, "Deposito realizado com sucesso", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("CONTA_ID", contaId);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            Log.i("DepositarActivity", "Erro no parse do valor");
        }
    }
}
