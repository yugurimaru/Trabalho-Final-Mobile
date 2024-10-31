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

import java.io.Serializable;

public class RetirarActivity extends AppCompatActivity {
    private Conta conta;
    private RepositorioExtrato repositorioExtrato;
    private RepositorioConta repositorioConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retirar);

        conta = (Conta) getIntent().getSerializableExtra("conta");
        repositorioExtrato = new RepositorioExtrato(this);
        repositorioConta = new RepositorioConta(this);
    }

    public void Confirmar(View view) {

        EditText valor = findViewById(R.id.editText_retirar);

        try {
            double valor_double = Double.parseDouble(valor.getText().toString());

            conta.retirar(valor_double, repositorioExtrato, repositorioConta);

            Intent intent = new Intent(this,HomeActivity.class);
            intent.putExtra("conta", (Serializable) conta);
            startActivity(intent);
            finish();


        }catch (Exception e){
            Log.i("Retirar","Erro no parse");
        }
    }
}