package com.example.contabancaria.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.DAO.RepositorioPix;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

import java.io.Serializable;

public class HomePixActivity extends AppCompatActivity {
    private int contaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pix);
        setTitle("Home Pix");

        contaId = getIntent().getIntExtra("CONTA_ID", -1);

        if (contaId != -1) {
            RepositorioConta repositorioConta = new RepositorioConta(this);
            Conta conta = repositorioConta.buscarContaPorId(contaId);

        } else {
            Log.e("HomeActivity", "ID da conta inválido");
        }
    }

    public void CadastrarPix(View view) {
        Intent intent = new Intent(this, CadastrarPixActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);

    }

    public void TransferirViaPix(View view) {
        Intent intent = new Intent(this,TransferirViaPixActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);
    }

    public void ListarPix(View view) {
        Intent intent = new Intent(this,ListarPixActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);

    }

    public void Voltar(View view) {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);
        finish();
    }
}
