package com.example.contabancaria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

import java.io.Serializable;

public class HomePixActivity extends AppCompatActivity {
    private Conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pix);
        conta = (Conta) getIntent().getSerializableExtra("conta");

    }

    public void CadastrarPix(View view) {
        Intent intent = new Intent(this, CadastrarPixActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);

    }

    public void TransferirViaPix(View view) {
        Intent intent = new Intent(this,ListarPixActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);
    }

    public void ListarPix(View view) {
        Intent intent = new Intent(this,ListarPixActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);

    }

    public void Voltar(View view) {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);
    }
}
