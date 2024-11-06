package com.example.contabancaria.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.DAO.RepositorioPix;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    private RepositorioConta repositorioConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        repositorioConta = new RepositorioConta(this);
    }

    public void Entrar(View view) {
        EditText ETusuario = findViewById(R.id.editText_usuario);
        EditText ETsenha = findViewById(R.id.editText_senha);

        String usuario = ETusuario.getText().toString();
        String senha = ETsenha.getText().toString();

        if (usuario.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        Conta conta = repositorioConta.buscarContaPorUsuarioSenha(usuario, senha);
        if (conta != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("CONTA_ID", conta.getId());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void CriarConta(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
        finish();
    }
}