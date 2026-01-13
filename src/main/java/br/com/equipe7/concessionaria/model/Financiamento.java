package br.com.equipe7.concessionaria.model;

public class Financiamento {
    private double valorTotalVeiculo;
    private double entrada;
    private int parcelas;
    private double juros;

    public Financiamento(double valorTotalVeiculo, double entrada, int parcelas, double juros) {
        this.valorTotalVeiculo = valorTotalVeiculo;
        this.entrada = entrada;
        this.parcelas = parcelas;
        this.juros = juros;
    }

    public double calcularTotal() {
        double valorFinanciado = valorTotalVeiculo - entrada;
        double montanteFinanciado = valorFinanciado * Math.pow(1 + juros, parcelas);
        return entrada + montanteFinanciado;
    }
}