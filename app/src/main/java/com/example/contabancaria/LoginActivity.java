package com.example.contabancaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        setTitle("Login");
    }

    Conta conta = new Conta(1,0);



    public void Entrar(View view) {

        EditText usuario = findViewById(R.id.editText_usuario);
        EditText senha = findViewById(R.id.editText_senha);

        String usuario_str = usuario.getText().toString();
        String senha_str = senha.getText().toString();

        List<String> listaUsuario = Arrays.asList("admin");

        if(usuario_str.isEmpty() || senha_str.isEmpty()){
            Toast.makeText(this, "Usuario ou senha Invalidos!",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        if(listaUsuario.contains(usuario_str) && senha_str.equals("123")){

            

            Intent intent = new Intent(this,HomeActivity.class);
            intent.putExtra("conta", (Serializable) conta);
            startActivity(intent);


        }else{
            Toast.makeText(this, "Usuario ou senha Invalidos!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

    }
}