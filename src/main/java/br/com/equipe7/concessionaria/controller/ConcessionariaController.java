package br.com.equipe7.concessionaria.controller;

import br.com.equipe7.concessionaria.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConcessionariaController {
    private List<Veiculo> estoque;
    private List<Venda> vendas = new ArrayList<>();

    public ConcessionariaController() {
        this.estoque = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo v) {
        estoque.add(v);
    }

    public List<Veiculo> getEstoque() {
        return estoque;
    }

    public boolean removerVeiculo(int index) {
        if (index >= 0 && index < estoque.size()) {
            estoque.remove(index);
            return true;
        }
        return false;
    }
    
    public void venderVeiculo(Veiculo veiculo, boolean financiar, double entrada, int parcelas, double juros) {
        double valorFinal = veiculo.getPreco();
        if (financiar) {
            Financiamento f = new Financiamento(valorFinal, entrada, parcelas, juros);
            valorFinal = f.calcularTotal();
        }
        vendas.add(new Venda(veiculo, valorFinal));
        estoque.remove(veiculo);
    }

    public String gerarRelatorio() {
        double valorTotalEstoque = 0;
        int qtdCarros = 0;
        int qtdMotos = 0;
        
        for (Veiculo v : estoque) {
            valorTotalEstoque += v.getPreco();
            if (v instanceof Carro) qtdCarros++;
            else if (v instanceof Moto) qtdMotos++;
        }
        
        double totalVendas = 0;
        for (Venda vd : vendas) {
            totalVendas += vd.getValorFinal();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO GERAL ===\n\n");
        sb.append("ESTOQUE:\n");
        sb.append("- Total Veículos: " + estoque.size() + "\n");
        sb.append("- Carros: " + qtdCarros + " | Motos: " + qtdMotos + "\n");
        sb.append(String.format("- Valor em Estoque: R$ %.2f\n\n", valorTotalEstoque));
        sb.append("VENDAS:\n");
        sb.append("- Veículos Vendidos: " + vendas.size() + "\n");
        sb.append(String.format("- Faturamento Total: R$ %.2f\n", totalVendas));
        return sb.toString();
    }

    public void salvarDados() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("banco_dados.txt"));
        for (Veiculo v : estoque) {
            String linha = "";
            // ORDEM: TIPO;MARCA;MODELO;ANO;COR;PRECO;EXTRA
            if (v instanceof Carro) {
                linha = "CARRO;" + v.getMarca() + ";" + v.getModelo() + ";" + v.getAno() + ";" + v.getCor() + ";" + v.getPreco() + ";" + ((Carro) v).getNumPortas();
            } else if (v instanceof Moto) {
                linha = "MOTO;" + v.getMarca() + ";" + v.getModelo() + ";" + v.getAno() + ";" + v.getCor() + ";" + v.getPreco() + ";" + ((Moto) v).getCilindradas();
            }
            writer.write(linha);
            writer.newLine();
        }
        writer.close();
    }

    public void carregarDados() throws IOException {
        File arquivo = new File("banco_dados.txt");
        if (!arquivo.exists()) return;

        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        estoque.clear(); 
        
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            try {
                if (dados[0].equals("CARRO")) {
                    estoque.add(new Carro(dados[1], dados[2], Integer.parseInt(dados[3]), dados[4], Double.parseDouble(dados[5]), Integer.parseInt(dados[6])));
                } else if (dados[0].equals("MOTO")) {
                    estoque.add(new Moto(dados[1], dados[2], Integer.parseInt(dados[3]), dados[4], Double.parseDouble(dados[5]), Integer.parseInt(dados[6])));
                }
            } catch (Exception e) {
                System.out.println("Erro ao ler linha: " + linha);
            }
        }
        reader.close();
    }
}