package com.example.contabancaria;

import java.io.Serializable;

public class Pix implements Serializable {

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

    public Pix(int id, long pix) {
        this.id = id;
        this.pix = pix;
    }

    public Pix(int id) {
        this.id = id;
    }
}
