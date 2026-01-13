package br.com.equipe7.concessionaria.model;

public abstract class Veiculo {
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    protected double preco;
    
    private boolean revisado = false;
    private double custoRevisao;
    
    public static int totalVeiculos = 0;

    public Veiculo(String marca, String modelo, int ano, String cor, double preco) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.preco = preco;
        totalVeiculos++;
    }

    /*MARCA*/
    public String getMarca() {
        return marca; 
    }
    public void setMarca(String marca) { 
        this.marca = marca; 
    }
    
    /*MODELO*/
    public String getModelo() {
        return modelo; 
    }
    public void setModelo(String modelo) { 
        this.modelo = modelo;
    }

    /*ANO*/
    public int getAno() { 
        return ano; 
    }
    public void setAno(int ano) {
        this.ano = ano; 
    }
    
    /*COR*/
    public String getCor() { 
        return cor; 
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    
    /*PREÇO*/
    public double getPreco() {
        return preco; 
    }
    public void setPreco(double preco) {
        this.preco = preco; 
    }
    
    /*REVISÃO*/
    public boolean isRevisado() { 
        return revisado; 
    }

    public abstract String getDetalhes();
    
    public void realizarRevisao(double custo) {
        this.revisado = true;
        this.custoRevisao = custo;
    }
}