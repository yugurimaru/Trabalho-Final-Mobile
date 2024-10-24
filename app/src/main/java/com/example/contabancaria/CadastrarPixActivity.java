package com.example.contabancaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class CadastrarPixActivity extends AppCompatActivity {
    private Conta conta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_pix);
        conta = (Conta) getIntent().getSerializableExtra("conta");
    }

    public void Confirmar(View view) {

        EditText TipoChavePix = findViewById(R.id.editText_TipoChavePix);
        EditText ChavePix = findViewById(R.id.editText_ChavePix);

        String TipoChavePix_str = TipoChavePix.getText().toString().trim();
        String ChavePix_str = ChavePix.getText().toString();

        Pix pix = new Pix(ChavePix_str, TipoChavePix_str);

        conta.adicionarChavePix(pix);

        Intent intent = new Intent(this, HomePixActivity.class);
        intent.putExtra("conta", (Serializable) conta);
        startActivity(intent);

    }
}