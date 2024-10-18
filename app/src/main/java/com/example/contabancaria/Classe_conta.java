package com.example.contabancaria;

import java.io.Serializable;
import java.util.List;

public class Classe_conta implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private double saldo;
    private Classe_pix chavePix;
    private List<Classe_extrato> extrato;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Classe_pix getChavePix() {
        return chavePix;
    }

    public void setChavePix(Classe_pix chavePix) {
        this.chavePix = chavePix;
    }

    public List<Classe_extrato> getExtrato() {
        return extrato;
    }

    public void setExtrato(List<Classe_extrato> extrato) {
        this.extrato = extrato;
    }

    public Classe_conta(int id, double valor, Classe_pix chavePix, List<Classe_extrato> extrato) {
        this.id = id;
        this.saldo = valor;
        this.chavePix = chavePix;
        this.extrato = extrato;
    }
}
