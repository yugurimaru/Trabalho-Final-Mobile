package com.example.contabancaria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class RetirarActivity extends AppCompatActivity {
    private Classe_conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retirar);

        conta = (Classe_conta) getIntent().getSerializableExtra("conta");
    }

    public void Confirmar(View view) {

        EditText valor = findViewById(R.id.editText_retirar);

        try {

            double valor_double = Double.parseDouble(valor.getText().toString());
            if((conta.getSaldo() - valor_double) > 0.0){
                Classe_extrato extrato = new Classe_extrato("Retirada", conta.getSaldo());
                conta.setExtrato(extrato);
                conta.setSaldo(conta.getSaldo() - valor_double);
                Intent intent = new Intent(this,HomeActivity.class);
                intent.putExtra("conta", (Serializable) conta);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Saldo insuficiente na conta!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        }catch (Exception e){
            Log.i("Retirar","Erro no parse");
        }

    }
}