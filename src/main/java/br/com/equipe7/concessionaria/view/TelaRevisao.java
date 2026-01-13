package br.com.equipe7.concessionaria.view;

import br.com.equipe7.concessionaria.controller.ConcessionariaController;
import br.com.equipe7.concessionaria.model.Veiculo;
import javax.swing.*;
import java.awt.*;

public class TelaRevisao extends JDialog {
    private ConcessionariaController controller;
    private JComboBox<String> comboVeiculos;
    private JTextField txtCusto;
    private JButton btnConfirmar;

    public TelaRevisao(Frame owner, ConcessionariaController controller) {
        super(owner, "Agendar Revisão", true);
        this.controller = controller;
        setSize(350, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(owner);

        add(new JLabel("Veículo:"));
        comboVeiculos = new JComboBox<>();
        carregarVeiculos();
        add(comboVeiculos);

        add(new JLabel("Custo da Revisão (R$):"));
        txtCusto = new JTextField();
        add(txtCusto);

        btnConfirmar = new JButton("Confirmar Revisão");
        add(new JLabel("")); 
        add(btnConfirmar);

        btnConfirmar.addActionListener(e -> {
            int index = comboVeiculos.getSelectedIndex();
            if (index >= 0) {
                try {
                    double custo = Double.parseDouble(txtCusto.getText());
                    Veiculo v = controller.getEstoque().get(index);
                    v.realizarRevisao(custo);
                    JOptionPane.showMessageDialog(this, "Revisão registrada!");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Valor inválido.");
                }
            }
        });
    }

    private void carregarVeiculos() {
        for (Veiculo v : controller.getEstoque()) {
            String status = v.isRevisado() ? "[OK]" : "[Pend.]";
            comboVeiculos.addItem(status + " " + v.getModelo());
        }
    }
}