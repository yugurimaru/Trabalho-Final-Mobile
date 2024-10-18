package com.example.contabancaria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class RetirarActivity extends AppCompatActivity {
    private Conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retirar);

        conta = (Conta) getIntent().getSerializableExtra("conta");
    }

    public void Confirmar(View view) {

        EditText valor = findViewById(R.id.editText_retirar);

        try {
            double valor_double = Double.parseDouble(valor.getText().toString());

            conta.retirar(valor_double);

            Intent intent = new Intent(this,HomeActivity.class);
            intent.putExtra("conta", (Serializable) conta);
            startActivity(intent);


        }catch (Exception e){
            Log.i("Retirar","Erro no parse");
        }



    }
}