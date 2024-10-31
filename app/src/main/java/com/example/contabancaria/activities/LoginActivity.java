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

        // Inicializar o repositório de contas
        repositorioConta = new RepositorioConta(this);
    }

    public void Entrar(View view) {
        EditText usuario = findViewById(R.id.editText_usuario);
        EditText senha = findViewById(R.id.editText_senha);

        String usuario_str = usuario.getText().toString();
        String senha_str = senha.getText().toString();

        if (usuario_str.isEmpty() || senha_str.isEmpty()) {
            Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar se o usuário existe no banco de dados
        Conta conta = repositorioConta.buscarContaPorUsuarioSenha(usuario_str, senha_str);
        if (conta != null) {
            // Login bem-sucedido
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("conta", (Serializable) conta);
            startActivity(intent);
            finish();
        } else {
            // Login falhou
            Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void CriarConta(View view) {

        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
        finish();
    }
}