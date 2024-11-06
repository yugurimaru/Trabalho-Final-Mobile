package com.example.contabancaria.classes;

import java.io.Serializable;

public class Extrato implements Serializable {

    private String tipoTransacao;
    private double valor;
    private double saldoAtual;

    public Extrato(String tipoTransacao, double valor, double saldoAtual) {
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.saldoAtual = saldoAtual;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        if (tipoTransacao != null) {
            this.tipoTransacao = tipoTransacao;
        }
    }

    public double getValor() {
        return valor;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

}
