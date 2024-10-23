package com.example.contabancaria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PixAdapter extends ArrayAdapter<Pix> {
    private List<Pix> pixList; // Adicione essa linha para armazenar a lista

    public PixAdapter(Context context, List<Pix> pixList) {
        super(context, 0, pixList);
        this.pixList = pixList; // Inicializa a lista
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
        tvTipoChave.setText(pix.getTipoChave());
        tvChave.setText(pix.getChave());

        // Retorne a view preenchida para ser exibida no ListView
        return convertView;
    }

    // Método para adicionar uma nova chave Pix
    public void add(Pix pix) {
        pixList.add(pix); // Adiciona a nova chave à lista
    }
}
