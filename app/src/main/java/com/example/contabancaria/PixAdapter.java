package com.example.contabancaria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PixAdapter extends ArrayAdapter<Pix> {

    public PixAdapter(Context context, List<Pix> pixList) {
        super(context, 0, pixList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pix pix = getItem(position);

        // Verifique se a view existente está sendo reutilizada, caso contrário, infle a nova view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pix, parent, false);
        }

        // Encontre os TextViews para preencher com os dados da transação
        TextView tvTipoChave = convertView.findViewById(R.id.tvTipoChave);
        TextView tvChave = convertView.findViewById(R.id.tvChave);

        // Popule os dados nas views
        tvTipoChave.setText(String.format("Tipo: " + pix.getTipoChave()));
        tvChave.setText(pix.getChave());

        // Retorne a view preenchida para ser exibida no ListView
        return convertView;
    }

}
