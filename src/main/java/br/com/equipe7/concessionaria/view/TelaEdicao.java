package br.com.equipe7.concessionaria.view;

import br.com.equipe7.concessionaria.model.Veiculo;
import javax.swing.*;
import java.awt.*;

public class TelaEdicao extends JDialog {
    private JTextField txtMarca, txtModelo, txtAno, txtPreco;
    private JButton btnSalvar;
    private Veiculo veiculoAlvo;

    public TelaEdicao(Frame owner, Veiculo veiculo) {
        super(owner, "Editar Veículo", true);
        this.veiculoAlvo = veiculo;
        setSize(300, 300);
        setLayout(new GridLayout(5, 2, 10, 10));
        setLocationRelativeTo(owner);

        add(new JLabel("Marca:"));
        txtMarca = new JTextField(veiculo.getMarca());
        add(txtMarca);

        add(new JLabel("Modelo:"));
        txtModelo = new JTextField(veiculo.getModelo());
        add(txtModelo);

        add(new JLabel("Ano:"));
        txtAno = new JTextField(String.valueOf(veiculo.getAno()));
        add(txtAno);

        add(new JLabel("Preço (R$):"));
        txtPreco = new JTextField(String.valueOf(veiculo.getPreco()));
        add(txtPreco);

        btnSalvar = new JButton("Salvar");
        add(new JLabel(""));
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                veiculoAlvo.setMarca(txtMarca.getText());
                veiculoAlvo.setModelo(txtModelo.getText());
                veiculoAlvo.setAno(Integer.parseInt(txtAno.getText()));
                veiculoAlvo.setPreco(Double.parseDouble(txtPreco.getText()));
                JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados.");
            }
        });
    }
}