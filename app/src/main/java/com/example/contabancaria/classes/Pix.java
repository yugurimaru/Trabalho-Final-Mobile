package com.example.contabancaria.classes;

import java.io.Serializable;

public class Pix implements Serializable {

    private String chave;
    private String TipoChave;

    public Pix(String chave, String tipoChave) {
        this.chave = chave;
        setTipoChave(tipoChave);
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
        if (tipoChave == null ) {
            throw new IllegalArgumentException("O tipo de chave está nulo");
        }
        else if(!tipoChave.equalsIgnoreCase("cpf") && !tipoChave.equalsIgnoreCase("telefone")){
            throw new IllegalArgumentException("O tipo de chave deve ser CPF ou TELEFONE");
        }
        if(tipoChave.equalsIgnoreCase("cpf")){
            tipoChave = "CPF";
        }
        if(tipoChave.equalsIgnoreCase("telefone")){
            tipoChave = "TELEFONE";
        }
        this.TipoChave = tipoChave;
    }
}
