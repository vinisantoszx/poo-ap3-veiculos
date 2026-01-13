package br.com.equipe7.concessionaria.model;

public class Venda {
    private Veiculo veiculo;
    private double valorFinal;

    public Venda(Veiculo veiculo, double valorFinal) {
        this.veiculo = veiculo;
        this.valorFinal = valorFinal;
    }

    public Veiculo getVeiculo() { return veiculo; }
    public double getValorFinal() { return valorFinal; }
}