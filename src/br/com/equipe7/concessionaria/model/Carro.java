package br.com.equipe7.concessionaria.model;

public class Carro extends Veiculo implements IPromocional {
    private int numPortas;

    public Carro(String marca, String modelo, double preco, int numPortas) {
        super(marca, modelo, preco);
        this.numPortas = numPortas;
    }

    @Override
    public String getDetalhes() {
        return "[CARRO] " + getMarca() + " " + getModelo() + " | R$ " + getPreco() + " | Portas: " + numPortas;
    }

    @Override
    public double calcularDesconto(double porcentagem) {
        return getPreco() - (getPreco() * (porcentagem / 100));
    }
    
    public int getNumPortas() { return numPortas; }
}
