package com.example.contabancaria.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.DAO.RepositorioExtrato;
import com.example.contabancaria.DAO.RepositorioPix;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;
import com.example.contabancaria.classes.Extrato;
import com.example.contabancaria.classes.Pix;

public class TransferirViaPixActivity extends AppCompatActivity {
    private int contaId;
    private RepositorioConta repositorioConta;
    private RepositorioPix repositorioPix;
    private RepositorioExtrato repositorioExtrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transferir_via_pix);
        setTitle("Transferencia Pix");

        contaId = getIntent().getIntExtra("CONTA_ID", -1);


        if (contaId == -1) {
            Log.e("TransferirViaPixActivity", "ID da conta invalido");
            finish();
            return;
        }

        repositorioConta = new RepositorioConta(this);
        repositorioPix = new RepositorioPix(this);
        repositorioExtrato = new RepositorioExtrato(this);
    }

    public void Transferir(View view) {
        EditText ETchavePixDestino = findViewById(R.id.editText_chavePixTransferencia);
        EditText ETvalor = findViewById(R.id.editText_valorTransferencia);

        String chavePixDestino = ETchavePixDestino.getText().toString().trim();
        String valor = ETvalor.getText().toString().trim();

        if (chavePixDestino.isEmpty() || valor.isEmpty()) {
            Toast.makeText(this, "Chave Pix e Valor são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        Pix pixDestino = repositorioPix.buscarChavePix(chavePixDestino);

        if (pixDestino == null) {
            Toast.makeText(this, "Chave Pix não encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorDouble;
        try {
            valorDouble = Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Conta contaRemetente = repositorioConta.buscarContaPorId(contaId);
        if (contaRemetente == null) {
            Toast.makeText(this, "Conta remetente não encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        Conta contaRecebedora = repositorioConta.buscarContaPorId(pixDestino.getId_conta());
        if (contaRecebedora == null) {
            Toast.makeText(this, "Conta destino não encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            contaRemetente.validarTransferenciaPix(contaRecebedora, valorDouble);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        contaRemetente.setSaldo(contaRemetente.getSaldo() - valorDouble);
        contaRecebedora.setSaldo(contaRecebedora.getSaldo() + valorDouble);

        repositorioConta.atualizarSaldo(contaRemetente.getId(), contaRemetente.getSaldo());
        repositorioConta.atualizarSaldo(contaRecebedora.getId(), contaRecebedora.getSaldo());

        Extrato extratoRemetente = new Extrato("Transferência Pix", -valorDouble, contaRemetente.getSaldo());
        Extrato extratoRecebedor = new Extrato("Recebimento Pix", valorDouble, contaRecebedora.getSaldo());

        repositorioExtrato.adicionarExtrato(extratoRemetente, contaRemetente.getId());
        repositorioExtrato.adicionarExtrato(extratoRecebedor, contaRecebedora.getId());

        Toast.makeText(this, "Transferência realizada com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }
}