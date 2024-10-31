package com.example.contabancaria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

import java.io.Serializable;

public class CadastroActivity extends AppCompatActivity {
    private RepositorioConta repositorioConta;
    private Conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro");

        repositorioConta = new RepositorioConta(this);

    }

    public void Cadastrar(View view) {

        EditText usuario = findViewById(R.id.editText_usuarioCadastro);
        EditText senha = findViewById(R.id.editText_senhaCadastro);

        String usuario_str = usuario.getText().toString();
        String senha_str = senha.getText().toString();

        if (usuario_str.isEmpty() || senha_str.isEmpty()) {
            Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se a conta já existe
        conta = repositorioConta.buscarContaPorUsuarioSenha(usuario_str, senha_str);

        if (conta == null) {
            // Obtém o próximo ID disponível
            int proximoId = repositorioConta.obterProximoId();

            // Cria uma nova conta com o próximo ID e saldo 0
            conta = new Conta(proximoId, 0, usuario_str, senha_str);

            // Cadastrato bem-sucedido
            repositorioConta.adicionarConta(conta);
            Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
            // Direciona para página de Login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Cadastro falhou
            Toast.makeText(this, "Usuário já cadastrado!", Toast.LENGTH_SHORT).show();
        }
    }

    public void Entrar(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}