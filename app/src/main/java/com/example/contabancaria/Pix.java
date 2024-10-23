package com.example.contabancaria;

import java.io.Serializable;

public class Pix implements Serializable {

    private String chave;
    private String TipoChave;

    public Pix(String chave, String tipoChave) {
        this.chave = chave;
        TipoChave = tipoChave;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getTipoChave() {
        return TipoChave;
    }

    public void setTipoChave(String tipoChave) {
        if(tipoChave != null) {
            this.TipoChave = tipoChave;
        }
    }
}
