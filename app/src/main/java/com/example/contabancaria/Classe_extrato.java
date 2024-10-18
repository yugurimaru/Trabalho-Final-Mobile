package com.example.contabancaria;

import java.io.Serializable;

public class Classe_extrato implements Serializable {

    private String tipoTransacao;
    private double valor;
    private double saldoAtual;
    private String data;

        public Classe_extrato(String tipoTransacao, double valor, double saldoAtual, String data) {
            this.tipoTransacao = tipoTransacao;
            this.valor = valor;
            this.saldoAtual = saldoAtual;
            this.data = data;
        }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
