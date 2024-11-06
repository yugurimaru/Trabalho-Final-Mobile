package com.example.contabancaria.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioPix;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Pix;

import java.util.List;

public class ListarPixActivity extends AppCompatActivity {
    private int contaId;
    private RepositorioPix repositorioPix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_pix);
        setTitle("Listagem Pix");

        contaId = getIntent().getIntExtra("CONTA_ID", -1);

        if (contaId == -1) {
            Log.e("ListarPixActivity", "ID da conta inválido");
            finish();
            return;
        }

        repositorioPix = new RepositorioPix(this);

        ListView listView = findViewById(R.id.listViewPix);
        List<Pix> pixList = repositorioPix.listarChavesPix(contaId);

        if (pixList.isEmpty()) {
            //Log.i("PIX", "Nenhuma Chave Pix encontrada.");
            Toast.makeText(this, "Não há registro de Chaves Pix", Toast.LENGTH_LONG).show();
        } else {
            PixAdapter adapter = new PixAdapter(this, pixList);
            listView.setAdapter(adapter);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    new AlertDialog.Builder(ListarPixActivity.this)
                            .setTitle("Excluir Chave Pix")
                            .setMessage("Deseja excluir esta Chave Pix?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Pix chavePix = pixList.get(position);
                                    repositorioPix.excluirChavePix(chavePix.getChave());
                                    pixList.remove(position);
                                    adapter.notifyDataSetChanged();

                                    Intent intent = new Intent(ListarPixActivity.this, HomePixActivity.class);
                                    intent.putExtra("CONTA_ID", contaId);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("Não", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    return true;
                }
            });
        }
    }
}
