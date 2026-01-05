package br.com.equipe7.concessionaria.model;

public abstract class Veiculo {
    private String marca;
    private String modelo;
    protected double preco;
    
    public static int totalVeiculos = 0;

    public Veiculo(String marca, String modelo, double preco) {
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        totalVeiculos++;
    }

    public Veiculo(String marca, String modelo) {
        this(marca, modelo, 0.0);
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getPreco() { return preco; }

    public abstract String getDetalhes();
}
