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
    private Conta conta;
    private RepositorioConta repositorioConta;
    private RepositorioPix repositorioPix;
    private RepositorioExtrato repositorioExtrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transferir_via_pix);
        conta = (Conta) getIntent().getSerializableExtra("conta");

        repositorioConta = new RepositorioConta(this);
        repositorioPix = new RepositorioPix(this);
        repositorioExtrato = new RepositorioExtrato(this);

    }

    public void Transferir(View view) {

        EditText chavePix = findViewById(R.id.editText_chavePixTransferencia);
        EditText valor = findViewById(R.id.editText_valorTransferencia);

        String chavePix_str = chavePix.getText().toString().trim();
        String valor_str = valor.getText().toString().trim();

        if (chavePix_str.isEmpty() || valor_str.isEmpty()) {
            //Toast.makeText(this, "Chave Pix e Valor são obrigatórios", Toast.LENGTH_SHORT).show();
            Log.e("TransferenciaPix", "Erro empty");
            return;
        }

        Pix chavePixObj = repositorioPix.buscarChavePix(chavePix_str);

        if (chavePixObj == null) {
            //Toast.makeText(this, "Chave Pix não encontrada", Toast.LENGTH_SHORT).show();
            Log.e("TransferenciaPix", "Chave Pix nao encontrada");
            return;
        }

        Conta contaRecebedor = repositorioConta.buscarContaPorId(chavePixObj.getId_conta());

        double valor_double;
        try {
            valor_double = Double.parseDouble(valor_str);
        } catch (NumberFormatException e) {
            //Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            Log.e("TransferenciaPix", "Valor invalido");
            return;
        }

        if (conta.getSaldo() < valor_double) {
            //Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
            Log.e("TransferenciaPix", "Saldo insuficiente");
            return;
        }

        Conta contaRecebedora = repositorioConta.buscarContaPorId(chavePixObj.getId_conta());
        if (contaRecebedora == null) {
            //Toast.makeText(this, "Conta do recebedor nao encontrada", Toast.LENGTH_SHORT).show();
            Log.e("TransferenciaPix", "Conta recebedora nao encontrada");
            return;
        }

        // Atualiza os saldos
        conta.setSaldo(conta.getSaldo() - valor_double);
        contaRecebedora.setSaldo(contaRecebedora.getSaldo() + valor_double);

        // Atualiza os repositórios
        repositorioConta.atualizarSaldo(conta.getId(), conta.getSaldo());
        repositorioConta.atualizarSaldo(contaRecebedora.getId(), contaRecebedora.getSaldo());

        Extrato extratoRemetente = new Extrato("Transferência Pix", -valor_double, conta.getSaldo());
        Extrato extratoRecebedor = new Extrato("Recebimento Pix", valor_double, contaRecebedor.getSaldo()+ valor_double);

        // Registra a transação no extrato
        repositorioExtrato.adicionarExtrato(extratoRemetente, conta.getId()); // Transferência para o remetente
        repositorioExtrato.adicionarExtrato(extratoRecebedor,chavePixObj.getId_conta()); // Transferência para o recebedor

        Toast.makeText(this, "Transferência realizada com sucesso", Toast.LENGTH_SHORT).show();
        finish(); // Finaliza a atividade




    }
}