package com.example.contabancaria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.DAO.RepositorioPix;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;
import com.example.contabancaria.classes.Pix;

import java.io.Serializable;

public class CadastrarPixActivity extends AppCompatActivity {
    private Conta conta;
    private int contaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_pix);
        setTitle("Cadastro Pix");

        contaId = getIntent().getIntExtra("CONTA_ID", -1);

        if (contaId != -1) {
            RepositorioConta repositorioConta = new RepositorioConta(this);
            conta = repositorioConta.buscarContaPorId(contaId);

            if (conta == null) {
                Log.e("CadastrarPixActivity", "Conta não encontrada");
                finish();
            }
        } else {
            Log.e("CadastrarPixActivity", "ID da conta inválido");
            finish();
        }
    }

    public void Confirmar(View view) {

        EditText ETtipoChave = findViewById(R.id.editText_TipoChavePix);
        EditText ETchavePix = findViewById(R.id.editText_ChavePix);

        String TipoChavePix = ETtipoChave.getText().toString().trim();
        String ChavePix = ETchavePix.getText().toString();

        Pix pix = new Pix(conta.getId(),ChavePix, TipoChavePix);

        conta.adicionarChavePix(pix);

        Intent intent = new Intent(this, HomePixActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);
        finish();

    }
}