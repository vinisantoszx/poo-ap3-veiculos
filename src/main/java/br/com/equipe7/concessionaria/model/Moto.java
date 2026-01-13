package br.com.equipe7.concessionaria.model;

public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String marca, String modelo, int ano, String cor, double preco, int cilindradas) {
        super(marca, modelo, ano, cor, preco);
        this.cilindradas = cilindradas;
    }

    @Override
    public String getDetalhes() {
        return String.format("[MOTO] %s %s %s (%d) | R$ %.2f | %dcc", 
                getMarca(), getModelo(), getCor(), getAno(), getPreco(), cilindradas);
    }
    
    public int getCilindradas() { return cilindradas; }
}