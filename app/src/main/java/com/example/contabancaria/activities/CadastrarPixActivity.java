package com.example.contabancaria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.R;
import com.example.contabancaria.classes.Conta;
import com.example.contabancaria.classes.Pix;

public class CadastrarPixActivity extends AppCompatActivity {
    private Conta conta;
    private int contaId;
    private RadioGroup radioGroupTipoChave;
    private EditText editTextChavePixCPF;
    private EditText editTextChavePixTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pix);
        setTitle("Cadastro Pix");

        radioGroupTipoChave = findViewById(R.id.radioGroupTipoChave);
        editTextChavePixCPF = findViewById(R.id.editText_chavePixCPF);
        editTextChavePixTelefone = findViewById(R.id.editText_chavePixTelefone);

        RadioButton rbCpf = findViewById(R.id.radioButton_cpf);
        RadioButton rbTelefone = findViewById(R.id.radioButton_telefone);

        contaId = getIntent().getIntExtra("CONTA_ID", -1);
        if (contaId != -1) {
            RepositorioConta repositorioConta = new RepositorioConta(this);
            conta = repositorioConta.buscarContaPorId(contaId);
        } else {
            Log.e("CadastrarPixActivity", "ID da conta invalido");
            finish();
            return;
        }

        configurarVisibilidadeEAplicarMascara();

        radioGroupTipoChave.setOnCheckedChangeListener((group, checkedId) -> configurarVisibilidadeEAplicarMascara());
    }

    private void configurarVisibilidadeEAplicarMascara() {
        RadioButton rbCpf = findViewById(R.id.radioButton_cpf);
        RadioButton rbTelefone = findViewById(R.id.radioButton_telefone);

        if (rbCpf.isChecked()) {
            editTextChavePixCPF.setVisibility(View.VISIBLE);
            editTextChavePixTelefone.setVisibility(View.INVISIBLE);
            aplicarMascaraCpf(editTextChavePixCPF);
        } else if (rbTelefone.isChecked()) {
            editTextChavePixTelefone.setVisibility(View.VISIBLE);
            editTextChavePixCPF.setVisibility(View.INVISIBLE);
            aplicarMascaraTelefone(editTextChavePixTelefone);
        }
    }

    private void aplicarMascaraCpf(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.length() > 14) {
                    text = text.substring(0, 14);
                    editText.setText(text);
                    editText.setSelection(text.length());
                }
                if (text.length() == 3 || text.length() == 7) {
                    editText.setText(text + ".");
                    editText.setSelection(text.length() + 1);
                } else if (text.length() == 11) {
                    editText.setText(text + "-");
                    editText.setSelection(text.length() + 1);
                }
            }
        });
    }

    private void aplicarMascaraTelefone(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();

                if (text.length() > 15) {
                    text = text.substring(0, 15);
                    editText.setText(text);
                    editText.setSelection(text.length());
                    return;
                }

                if (text.length() == 1) {
                    text = "(" + text;
                } else if (text.length() == 3) {
                    text = text + ") ";
                } else if (text.length() == 10) {
                    text = text + "-";
                }

                if (!editable.toString().equals(text)) {
                    editText.setText(text);
                    editText.setSelection(text.length());
                }
            }
        });
    }

    public void Confirmar(View view) {
        RadioButton rbTelefone = findViewById(R.id.radioButton_telefone);
        RadioButton rbCpf = findViewById(R.id.radioButton_cpf);

        String TipoChavePix = "";
        String ChavePix = "";

        if (rbTelefone.isChecked()) {
            TipoChavePix = "Telefone";
            ChavePix = editTextChavePixTelefone.getText().toString().trim().replaceAll("[()-]", "");
        } else if (rbCpf.isChecked()) {
            TipoChavePix = "CPF";
            ChavePix = editTextChavePixCPF.getText().toString().replaceAll("[.-]", "");;
        }

        if(ChavePix.length() != 12 && !rbCpf.isChecked()){
            Toast.makeText(this, "CPF inválido", Toast.LENGTH_SHORT).show();
            finish();
        }else if(ChavePix.length() != 11 && !rbTelefone.isChecked()){
            Toast.makeText(this, "Telefone inválido", Toast.LENGTH_SHORT).show();
            finish();
        }

        Pix pix = new Pix(conta.getId(), ChavePix, TipoChavePix);
        conta.adicionarChavePix(pix);

        Intent intent = new Intent(this, HomePixActivity.class);
        intent.putExtra("CONTA_ID", contaId);
        startActivity(intent);
        finish();
    }
}
