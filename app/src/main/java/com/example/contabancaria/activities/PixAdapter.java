package com.example.contabancaria.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.contabancaria.R;
import com.example.contabancaria.classes.Pix;

import java.util.List;

public class PixAdapter extends ArrayAdapter<Pix> {

    public PixAdapter(Context context, List<Pix> pixList) {
        super(context, 0, pixList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pix pix = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pix, parent, false);
        }

        TextView tvTipoChave = convertView.findViewById(R.id.tvTipoChave);
        TextView tvChave = convertView.findViewById(R.id.tvChave);

        tvTipoChave.setText(String.format("Tipo: " + pix.getTipoChave()));
        tvChave.setText(pix.getChave());
        
        return convertView;
    }

}
