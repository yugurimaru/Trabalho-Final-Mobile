package com.example.contabancaria.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;
import com.example.contabancaria.classes.Pix;

import java.util.List;

public class ListarPixActivity extends AppCompatActivity {
    private Conta conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_pix);

        // Obtém a conta passada pela Intent
        conta = (Conta) getIntent().getSerializableExtra("conta");

        // Referência ao ListView
        ListView listView = findViewById(R.id.listViewPix);

        // Obter a lista de chaves Pix da conta
        List<Pix> pixList = conta.getPix();

        // Verifique se a lista está vazia e configure o adapter
        if (pixList.isEmpty()) {
            Log.i("PIX", "Nenhuma Chave Pix encontrada.");
        } else {
            PixAdapter adapter = new PixAdapter(this, pixList);
            listView.setAdapter(adapter);

            // Configurando o long click para deletar o item
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    // Exibe um diálogo para confirmar a exclusão
                    new AlertDialog.Builder(ListarPixActivity.this)
                            .setTitle("Excluir Chave Pix")
                            .setMessage("Deseja excluir esta Chave Pix?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // Remove o item da lista de chaves Pix
                                    conta.getPix().remove(position);

                                    // Notifica o adapter que os dados mudaram
                                    adapter.notifyDataSetChanged();

                                    // Após a exclusão, navega de volta para a HomePixActivity
                                    Intent intent = new Intent(ListarPixActivity.this, HomePixActivity.class);
                                    intent.putExtra("conta", conta); // Passa a conta atualizada
                                    startActivity(intent);
                                    finish(); // Finaliza a atividade atual
                                }
                            })
                            .setNegativeButton("Não", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    return true; // Retorna true para indicar que o evento foi consumido
                }
            });
        }
    }
}
