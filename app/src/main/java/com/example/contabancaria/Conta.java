package com.example.contabancaria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private double saldo;
    private Pix chavePix;
    private List<Extrato> extrato;

    public Conta(int id, double saldoInicial) {
        this.id = id;
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo");
        }
        this.saldo = saldoInicial;
        this.chavePix = null; // Inicia sem chave PIX
        this.extrato = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public Pix getChavePix() {
        return chavePix;
    }

    public void setChavePix(Pix chavePix) {
        this.chavePix = chavePix;
    }

    public List<Extrato> getExtrato() {
        return extrato;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor de deposito deve ser maior que zero");
        }
        saldo += valor;
        adicionarExtrato("Deposito", valor);
    }

    public void retirar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor de retirada deve ser maior que zero");
        }
        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        saldo -= valor;
        adicionarExtrato("Retirada", valor);
    }

    private void adicionarExtrato(String tipoTransacao, double valor) {
        Extrato novaTransacao = new Extrato(tipoTransacao, valor, saldo);
        extrato.add(novaTransacao);
    }
}
