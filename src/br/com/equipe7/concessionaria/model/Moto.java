package br.com.equipe7.concessionaria.model;

public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String marca, String modelo, double preco, int cilindradas) {
        super(marca, modelo, preco);
        this.cilindradas = cilindradas;
    }

    @Override
    public String getDetalhes() {
        return "[MOTO] " + getMarca() + " " + getModelo() + " | R$ " + getPreco() + " | " + cilindradas + "cc";
    }
    
    public int getCilindradas() { return cilindradas; }
}
