package br.com.equipe7.concessionaria.model;

public class Carro extends Veiculo implements IPromocional {
    private int numPortas;

    public Carro(String marca, String modelo, int ano, String cor, double preco, int numPortas) {
        super(marca, modelo, ano, cor, preco); // Passando a cor
        this.numPortas = numPortas;
    }

    @Override
    public String getDetalhes() {
        return String.format("[CARRO] %s %s %s (%d) | R$ %.2f | %d Portas", 
                getMarca(), getModelo(), getCor(), getAno(), getPreco(), numPortas);
    }

    @Override
    public double calcularDesconto(double porcentagem) {
        return getPreco() - (getPreco() * (porcentagem / 100));
    }
    
    public int getNumPortas() { return numPortas; }
}