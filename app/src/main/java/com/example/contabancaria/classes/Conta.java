package com.example.contabancaria.classes;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.DAO.RepositorioExtrato;
import com.example.contabancaria.DAO.RepositorioPix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String usuario;
    private String senha;
    private double saldo;


    public Conta(int id, double saldoInicial, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo");
        }
        this.saldo = saldoInicial;
    }

    public void depositar(double valor, RepositorioExtrato repositorioExtrato, RepositorioConta repositorioConta) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor de depósito deve ser maior que zero");
        }
        saldo += valor;
        adicionarExtrato(repositorioExtrato, repositorioConta, "Deposito", valor);
    }

    public void retirar(double valor, RepositorioExtrato repositorioExtrato, RepositorioConta repositorioConta) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor de retirada deve ser maior que zero");
        }
        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        saldo -= valor;

        adicionarExtrato(repositorioExtrato, repositorioConta, "Retirada", valor);
    }

    private void adicionarExtrato(RepositorioExtrato repositorioExtrato,RepositorioConta repositorioConta, String tipoTransacao, double valor) {

        Extrato novaTransacao = new Extrato(tipoTransacao, valor, saldo);

        repositorioConta.atualizarSaldo(this.id, saldo);
        repositorioExtrato.adicionarExtrato(novaTransacao, this.id);
    }

    public void adicionarChavePix(RepositorioPix repositorioPix, Pix pix){
        if (pix == null){
            throw new IllegalArgumentException("A chave Pix não pode ser nula");
        }
        repositorioPix.adicionarChavesPix(pix, this.id);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

}
