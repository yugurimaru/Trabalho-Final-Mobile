package com.example.contabancaria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

import java.io.Serializable;

public class HomeActivity extends AppCompatActivity {
    private Conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        conta = (Conta) getIntent().getSerializableExtra("conta");

        TextView boasvindas = findViewById(R.id.textView_home);

        boasvindas.setText("Seja bem vindo " + conta.getUsuario());

    }


    public void depositar(View view) {
        Intent intent = new Intent(this,DepositarActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);

    }

    public void retirar(View view) {
        Intent intent = new Intent(this,RetirarActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);

    }

    public void extrato(View view) {
        Intent intent = new Intent(this,ExtratoActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);

    }

    public void pix(View view) {
        Intent intent = new Intent(this, HomePixActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);
    }

    public void sair(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}