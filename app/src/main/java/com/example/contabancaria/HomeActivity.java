package com.example.contabancaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        setTitle("Home");
    }


    public void depositar(View view) {
        Intent intent = new Intent(this,DepositarActivity.class);
        startActivity(intent);

    }

    public void retirar(View view) {
        Intent intent = new Intent(this,RetirarActivity.class);
        startActivity(intent);

    }

    public void extrato(View view) {
        Intent intent = new Intent(this,ExtratoActivity.class);
        startActivity(intent);

    }

    public void pix(View view) {
        Intent intent = new Intent(this,PixActivity.class);
        startActivity(intent);
    }
}