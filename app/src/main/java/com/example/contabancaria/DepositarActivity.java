package com.example.contabancaria;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DepositarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_depositar);
    }

    public void Confirmar(View view) {

        EditText valor = findViewById(R.id.editTextNumber_depositar);

        try {

            float valor_float = Float.parseFloat(valor.getText().toString());


        }catch (Exception e){
            Log.i("Depositar","Erro no parse");
        }
    }
}