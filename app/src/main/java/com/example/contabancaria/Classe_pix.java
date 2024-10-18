package com.example.contabancaria;

import java.io.Serializable;

public class Classe_pix implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private long pix;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPix() {
        return pix;
    }

    public void setPix(long pix) {
        this.pix = pix;
    }

    public Classe_pix(int id, long pix) {
        this.id = id;
        this.pix = pix;
    }

    public Classe_pix(int id) {
        this.id = id;
    }
}
