package com.example.contabancaria.classes;

import java.io.Serializable;

public class Pix implements Serializable {

    private int id_conta;
    private String chave;
    private String TipoChave;

    public Pix(int id_conta,String chave, String tipoChave) {
        this.id_conta = id_conta;
        this.chave = chave;
        this.TipoChave = tipoChave;
    }

    public int getId_conta() {
        return id_conta;
    }

    public void setId_conta(int id_conta) {
        this.id_conta = id_conta;
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
        this.TipoChave = tipoChave;
    }

    @Override
    public String toString() {
        return "Pix{" +
                "id_conta=" + id_conta +
                ", chave='" + chave + '\'' +
                ", TipoChave='" + TipoChave + '\'' +
                '}';
    }
}
