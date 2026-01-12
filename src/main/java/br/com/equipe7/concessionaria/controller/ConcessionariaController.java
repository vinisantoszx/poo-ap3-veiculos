package br.com.equipe7.concessionaria.controller;

import br.com.equipe7.concessionaria.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConcessionariaController {
    private List<Veiculo> estoque;

    public ConcessionariaController() {
        this.estoque = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo v) {
        estoque.add(v);
    }

    public List<Veiculo> getEstoque() {
        return estoque;
    }

    public void salvarDados() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("banco_dados.txt"));
        
        for (Veiculo v : estoque) {
            String linha = "";
            if (v instanceof Carro) {
                linha = "CARRO;" + v.getMarca() + ";" + v.getModelo() + ";" + v.getPreco() + ";" + ((Carro) v).getNumPortas();
            } else if (v instanceof Moto) {
                linha = "MOTO;" + v.getMarca() + ";" + v.getModelo() + ";" + v.getPreco() + ";" + ((Moto) v).getCilindradas();
            }
            writer.write(linha);
            writer.newLine();
        }
        writer.close();
    }
    
    public boolean removerVeiculo(int index) {
        if (index >= 0 && index < estoque.size()) {
            estoque.remove(index);
            return true;
        }
        return false;
    }
    
    public String gerarRelatorio() {
        double valorTotalEstoque = 0;
        int qtdCarros = 0;
        int qtdMotos = 0;
        
        for (Veiculo v : estoque) {
            valorTotalEstoque += v.getPreco();
            if (v instanceof Carro) {
                qtdCarros++;
            } else if (v instanceof Moto) {
                qtdMotos++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE VENDA-REVISÃO ===\n\n");
        sb.append("REVISÃO DE ESTOQUE:\n");
        sb.append("- Total de Veículos: " + estoque.size() + "\n");
        sb.append("- Carros: " + qtdCarros + "\n");
        sb.append("- Motos: " + qtdMotos + "\n\n");
        
        sb.append("PROJEÇÃO DE VENDAS:\n");
        sb.append(String.format("- Valor Total do Estoque: R$ %.2f\n", valorTotalEstoque));
        
        return sb.toString();
    }


    public void carregarDados() throws IOException {
        File arquivo = new File("banco_dados.txt");
        if (!arquivo.exists()) return;

        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        estoque.clear(); 
        
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            if (dados[0].equals("CARRO")) {
                estoque.add(new Carro(dados[1], dados[2], Double.parseDouble(dados[3]), Integer.parseInt(dados[4])));
            } else if (dados[0].equals("MOTO")) {
                estoque.add(new Moto(dados[1], dados[2], Double.parseDouble(dados[3]), Integer.parseInt(dados[4])));
            }
        }
        reader.close();
    }
}
