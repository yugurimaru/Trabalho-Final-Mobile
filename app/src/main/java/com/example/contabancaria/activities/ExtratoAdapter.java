package com.example.contabancaria.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.contabancaria.R;
import com.example.contabancaria.classes.Extrato;

import java.util.List;

public class ExtratoAdapter extends ArrayAdapter<Extrato> {

    public ExtratoAdapter(Context context, List<Extrato> extratoList) {
        super(context, 0, extratoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtenha o item de dados para esta posição
        Extrato extrato = getItem(position);

        // Verifique se a view existente está sendo reutilizada, caso contrário, infle a nova view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_extrato, parent, false);
        }

        // Encontre os TextViews para preencher com os dados da transação
        TextView tvTipoTransacao = convertView.findViewById(R.id.tvTipoTransacao);
        TextView tvValor = convertView.findViewById(R.id.tvValor);
        TextView tvSaldoAposTransacao = convertView.findViewById(R.id.tvSaldoAposTransacao);

        // Popule os dados nas views
        tvTipoTransacao.setText(extrato.getTipoTransacao());
        tvValor.setText(String.format("R$ %.2f", extrato.getValor()));
        tvSaldoAposTransacao.setText(String.format("Saldo após: R$ %.2f", extrato.getSaldoAtual()));

        // Retorne a view preenchida para ser exibida no ListView
        return convertView;
    }
}

