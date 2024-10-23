package com.example.contabancaria;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PixActivity extends AppCompatActivity {
    private Conta conta;
    private PixAdapter adapter; // Declare o adapter como um atributo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pix);

        conta = (Conta) getIntent().getSerializableExtra("conta");
        ListView listView = findViewById(R.id.listViewPix);
        List<Pix> pixList = conta.getPix();
        adapter = new PixAdapter(this, pixList);
        listView.setAdapter(adapter);

        if (pixList.isEmpty()) {
            Log.i("PIX", "Nenhuma Chave Pix encontrada.");
        }
    }

    public void CadastrarPix(View view) {
        // Inflate o layout para o dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_chave_pix, null);
        final EditText editTextTipoChave = dialogView.findViewById(R.id.editTextTipoChave);
        final EditText editTextChave = dialogView.findViewById(R.id.editTextChave);

        // Cria o AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Digite os dados");

        // Botão Confirmar
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tipoChave = editTextTipoChave.getText().toString();
                String chave = editTextChave.getText().toString();

                // Lógica para processar os dados inseridos
                if (tipoChave.isEmpty() || chave.isEmpty()) {
                    Toast.makeText(PixActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                } else {
                    // Criar um novo objeto Pix
                    Pix novaChavePix = new Pix(chave, tipoChave);

                    // Adicionar a chave Pix à conta
                    //conta.adicionarChavePix(novaChavePix);

                    // Adicionar a nova chave ao adapter
                    adapter.add(novaChavePix); // Usar o adapter inicializado

                    // Notifica o adapter sobre a mudança
                    adapter.notifyDataSetChanged();

                    // Exibir uma mensagem de confirmação
                    Toast.makeText(PixActivity.this, "Chave Pix adicionada: " + chave + ", Tipo: " + tipoChave, Toast.LENGTH_LONG).show();
                }
            }
        });

        // Botão Cancelar
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Fecha o dialog
            }
        });

        // Exibe o diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void TransferirViaPix(View view) {
    }
}
