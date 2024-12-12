package com.example.contabancaria.classes;

import android.widget.Toast;

import com.example.contabancaria.DAO.RepositorioConta;
import com.example.contabancaria.DAO.RepositorioExtrato;
import com.example.contabancaria.DAO.RepositorioPix;

import java.io.Serializable;

public class Conta {
    private int id;
    private final String usuario;
    private final String senha;
    private double saldo;

    private final RepositorioExtrato repositorioExtrato;
    private final RepositorioConta repositorioConta;
    private final RepositorioPix repositorioPix;

    //Criação de novas contas
    public Conta(String usuario, String senha, double saldoInicial, RepositorioExtrato repositorioExtrato, RepositorioConta repositorioConta, RepositorioPix repositorioPix) {
        validarSaldoInicial(saldoInicial);
        this.usuario = usuario;
        this.senha = senha;
        this.saldo = saldoInicial;
        this.repositorioExtrato = repositorioExtrato;
        this.repositorioConta = repositorioConta;
        this.repositorioPix = repositorioPix;
    }
    //Conta existentes com ID
    public Conta(int id, String usuario, String senha, double saldo,
                 RepositorioExtrato repositorioExtrato, RepositorioConta repositorioConta, RepositorioPix repositorioPix) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.saldo = saldo;
        this.repositorioExtrato = repositorioExtrato;
        this.repositorioConta = repositorioConta;
        this.repositorioPix = repositorioPix;
    }

    private void validarSaldoInicial(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo");
        }
    }

    public synchronized void depositar(double valor) {
        validarValor(valor);
        saldo += valor;
        atualizarSaldoComExtrato("Depósito", valor);
    }

    public synchronized void retirar(double valor) {
        validarValor(valor);
        validarSaldoSuficiente(valor);
        saldo -= valor;
        atualizarSaldoComExtrato("Retirada", -valor);
    }

    private void validarValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        if (valor > 1000000) {
            throw new IllegalArgumentException("valores acima de 1.000.000 (Um milhão) são inválidos");
        }
    }

    private void validarSaldoSuficiente(double valor) {
        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }

    private void atualizarSaldoComExtrato(String tipoTransacao, double valor) {
        Extrato novaTransacao = new Extrato(tipoTransacao, valor, saldo);
        repositorioConta.atualizarSaldo(id, saldo);
        repositorioExtrato.adicionarExtrato(novaTransacao, id);
    }

    public void adicionarChavePix(Pix pix) {
        if (pix == null) {
            throw new IllegalArgumentException("A chave Pix nao pode ser nula");
        }
        if(repositorioPix.chavePixJaExiste(pix.getChave())){
            throw new IllegalArgumentException("Chave Pix ja está cadastrada");
        }
        repositorioPix.adicionarChavesPix(pix, id);

    }

    public void validarTransferenciaPix(Conta contaDestino, double valor) {
        if (contaDestino == null) {
            throw new IllegalArgumentException("Conta destino não encontrada");
        }

        if (contaDestino.getId() == this.id) {
            throw new IllegalArgumentException("Nao e possível transferir para a propria conta");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferencia deve ser maior que zero");
        }

        if (this.saldo < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferencia");
        }
    }

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
