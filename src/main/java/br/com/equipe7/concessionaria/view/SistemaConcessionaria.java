package br.com.equipe7.concessionaria.view;

import br.com.equipe7.concessionaria.controller.ConcessionariaController;
import br.com.equipe7.concessionaria.model.*;
import javax.swing.*;
import java.awt.*;

public class SistemaConcessionaria extends JFrame {
    private ConcessionariaController controller = new ConcessionariaController();
    
    private JComboBox<String> comboTipo;
    private JTextField txtMarca, txtModelo, txtPreco, txtExtra;
    private JLabel lblExtra; 
    private JTextArea areaListagem;

    public SistemaConcessionaria() {
        setTitle("Sistema Equipe 7 - Gestão de Concessionária");
        setSize(600, 500);
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
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCarregar = new JButton("Carregar");

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnListar);
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
                txtMarca.setText(""); txtModelo.setText(""); txtPreco.setText(""); txtExtra.setText("");
                atualizarListagem();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados: " + ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> atualizarListagem());

        btnSalvar.addActionListener(e -> {
            try { controller.salvarDados(); JOptionPane.showMessageDialog(this, "Salvo!"); } 
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao salvar."); }
        });

        btnCarregar.addActionListener(e -> {
            try { controller.carregarDados(); atualizarListagem(); JOptionPane.showMessageDialog(this, "Carregado!"); } 
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao carregar."); }
        });
    }

    private void atualizarListagem() {
        areaListagem.setText("");
        areaListagem.append("--- ESTOQUE ATUAL ---\n");
        for (Veiculo v : controller.getEstoque()) {
            areaListagem.append(v.getDetalhes() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaConcessionaria().setVisible(true));
    }
}
