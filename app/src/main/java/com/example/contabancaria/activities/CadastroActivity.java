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
import com.example.contabancaria.DAO.RepositorioExtrato;
import com.example.contabancaria.DAO.RepositorioPix;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;

import java.io.Serializable;

public class CadastroActivity extends AppCompatActivity {
    private RepositorioConta repositorioConta;
    private RepositorioExtrato repositorioExtrato;
    private RepositorioPix repositorioPix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro");

        repositorioConta = new RepositorioConta(this);
        repositorioExtrato = new RepositorioExtrato(this);
        repositorioPix = new RepositorioPix(this);

    }

    public void Cadastrar(View view) {
        EditText ETusuario = findViewById(R.id.editText_usuarioCadastro);
        EditText ETsenha = findViewById(R.id.editText_senhaCadastro);

        String usuario = ETusuario.getText().toString().trim();
        String senha = ETsenha.getText().toString().trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (repositorioConta.buscarContaPorUsuarioSenha(usuario, senha) == null) {
            Conta conta = new Conta(usuario, senha, 0, repositorioExtrato, repositorioConta, repositorioPix);
            repositorioConta.adicionarConta(conta);
            Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Usuário já cadastrado!", Toast.LENGTH_SHORT).show();
        }
    }


    public void Entrar(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}