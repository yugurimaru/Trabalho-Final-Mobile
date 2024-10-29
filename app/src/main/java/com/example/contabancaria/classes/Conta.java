package com.example.contabancaria.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String usuario;
    private String senha;
    private double saldo;
    private List<Pix> pix;
    private List<Extrato> extrato;

    public Conta(int id, double saldoInicial, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo");
        }
        this.saldo = saldoInicial;
        this.pix = new ArrayList<>();
        this.extrato = new ArrayList<>();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Pix> getPix() {
        return pix;
    }

    public void setPix(List<Pix> pix) {
        this.pix = pix;
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

    public void adicionarChavePix(Pix pix){
        if (pix == null){
            throw new IllegalArgumentException("A chave Pix não pode ser nula");
        }
        this.pix.add(pix);
    }


}
