package br.com.equipe7.concessionaria.view;

import br.com.equipe7.concessionaria.controller.ConcessionariaController;
import br.com.equipe7.concessionaria.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SistemaConcessionaria extends JFrame {
    private ConcessionariaController controller = new ConcessionariaController();

    private JComboBox<String> comboTipo;
    private JTextField txtMarca, txtModelo, txtPreco, txtExtra;
    private JLabel lblExtra;
    private JTextArea areaListagem;

    public SistemaConcessionaria() {
        setTitle("Sistema Equipe 7 - Gestão de Concessionária");
        setSize(700, 550); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCadastro = new JPanel(new GridLayout(5, 2, 5, 5));
        panelCadastro.setBorder(BorderFactory.createTitledBorder("Novo Veículo"));

        panelCadastro.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Carro", "Moto"});
        panelCadastro.add(comboTipo);

        panelCadastro.add(new JLabel("Marca:"));
        txtMarca = new JTextField();
        panelCadastro.add(txtMarca);

        panelCadastro.add(new JLabel("Modelo:"));
        txtModelo = new JTextField();
        panelCadastro.add(txtModelo);

        panelCadastro.add(new JLabel("Preço (R$):"));
        txtPreco = new JTextField();
        panelCadastro.add(txtPreco);

        lblExtra = new JLabel("Nº Portas:");
        panelCadastro.add(lblExtra);
        txtExtra = new JTextField();
        panelCadastro.add(txtExtra);

        add(panelCadastro, BorderLayout.NORTH);

        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaListagem), BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnListar = new JButton("Listar Estoque");
        JButton btnRemover = new JButton("Remover");
        JButton btnRelatorio = new JButton("Relatório"); 
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCarregar = new JButton("Carregar");

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnListar);
        panelBotoes.add(btnRemover);
        panelBotoes.add(btnRelatorio); 
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCarregar);
        add(panelBotoes, BorderLayout.SOUTH);

        comboTipo.addActionListener(e -> {
            if (comboTipo.getSelectedItem().equals("Carro")) {
                lblExtra.setText("Nº Portas:");
            } else {
                lblExtra.setText("Cilindradas (cc):");
            }
        });

        btnAdicionar.addActionListener(e -> {
            try {
                String marca = txtMarca.getText();
                String modelo = txtModelo.getText();
                double preco = Double.parseDouble(txtPreco.getText());
                int extra = Integer.parseInt(txtExtra.getText());

                Veiculo v;
                if (comboTipo.getSelectedItem().equals("Carro")) {
                    v = new Carro(marca, modelo, preco, extra);
                } else {
                    v = new Moto(marca, modelo, preco, extra);
                }

                controller.adicionarVeiculo(v);
                JOptionPane.showMessageDialog(this, "Veículo adicionado!");

                txtMarca.setText("");
                txtModelo.setText("");
                txtPreco.setText("");
                txtExtra.setText("");

                atualizarListagem();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados: " + ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> atualizarListagem());

        btnRemover.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Digite o NÚMERO do veículo para remover (veja na lista):");

            if (input != null && !input.isEmpty()) {
                try {
                    int numeroUsuario = Integer.parseInt(input);
                    int indexReal = numeroUsuario - 1;

                    boolean sucesso = controller.removerVeiculo(indexReal);

                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Veículo removido com sucesso!");
                        atualizarListagem();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro: Número não encontrado na lista.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor, digite apenas números inteiros.");
                }
            }
        });

        btnRelatorio.addActionListener(e -> {
            String textoRelatorio = controller.gerarRelatorio();
            JOptionPane.showMessageDialog(this, textoRelatorio, "Relatório Geral", JOptionPane.INFORMATION_MESSAGE);
        });

        btnSalvar.addActionListener(e -> {
            try {
                controller.salvarDados();
                JOptionPane.showMessageDialog(this, "Dados salvos em banco_dados.txt!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        btnCarregar.addActionListener(e -> {
            try {
                controller.carregarDados();
                atualizarListagem();
                JOptionPane.showMessageDialog(this, "Dados carregados com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar: " + ex.getMessage());
            }
        });
    }

    private void atualizarListagem() {
        areaListagem.setText("");
        areaListagem.append("--- ESTOQUE ATUAL ---\n");

        List<Veiculo> estoque = controller.getEstoque();

        if (estoque.isEmpty()) {
            areaListagem.append("Nenhum veículo cadastrado.\n");
        } else {
            for (int i = 0; i < estoque.size(); i++) {
                Veiculo v = estoque.get(i);
                areaListagem.append("[" + (i + 1) + "] " + v.getDetalhes() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaConcessionaria().setVisible(true));
    }
}