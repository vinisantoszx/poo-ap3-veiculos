package br.com.equipe7.concessionaria.view;

import br.com.equipe7.concessionaria.controller.ConcessionariaController;
import br.com.equipe7.concessionaria.model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaVenda extends JDialog {
    private ConcessionariaController controller;
    private JComboBox<String> comboVeiculos;
    private JCheckBox chkFinanciar;
    private JTextField txtEntrada, txtParcelas, txtJuros;
    private JLabel lblResumo;
    private JButton btnConfirmar, btnSimular;

    public TelaVenda(Frame owner, ConcessionariaController controller) {
        super(owner, "Realizar Venda", true);
        this.controller = controller;
        setSize(550, 500);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(owner);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainPanel, BorderLayout.CENTER);

        JPanel panelVeiculo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelVeiculo.setBorder(BorderFactory.createTitledBorder(" 1. Selecione o Veículo "));
        panelVeiculo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        comboVeiculos = new JComboBox<>();
        carregarVeiculos();
        panelVeiculo.add(comboVeiculos);
        mainPanel.add(panelVeiculo);
        
        mainPanel.add(Box.createVerticalStrut(15));

        JPanel panelPagamento = new JPanel(new GridLayout(5, 2, 10, 10));
        panelPagamento.setBorder(BorderFactory.createTitledBorder(" 2. Condições de Pagamento "));
        chkFinanciar = new JCheckBox("Deseja Financiar?");
        panelPagamento.add(chkFinanciar);
        panelPagamento.add(new JLabel("")); 

        panelPagamento.add(new JLabel("Valor Entrada (R$):"));
        txtEntrada = new JTextField("0.0");
        txtEntrada.setEnabled(false);
        panelPagamento.add(txtEntrada);

        panelPagamento.add(new JLabel("Nº Parcelas:"));
        txtParcelas = new JTextField("12");
        txtParcelas.setEnabled(false);
        panelPagamento.add(txtParcelas);

        panelPagamento.add(new JLabel("Juros Mensais (0.02 = 2%):"));
        txtJuros = new JTextField("0.02");
        txtJuros.setEnabled(false);
        panelPagamento.add(txtJuros);
        
        mainPanel.add(panelPagamento);
        mainPanel.add(Box.createVerticalStrut(15));

        JPanel panelResumo = new JPanel(new BorderLayout());
        panelResumo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)));
        lblResumo = new JLabel("Clique em simular para ver os valores...", SwingConstants.CENTER);
        lblResumo.setForeground(Color.BLUE);
        panelResumo.add(lblResumo, BorderLayout.CENTER);
        mainPanel.add(panelResumo);

        JPanel panelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotoes.setBorder(new EmptyBorder(10, 15, 15, 15));
        btnSimular = new JButton("Simular Valores");
        btnConfirmar = new JButton("FINALIZAR VENDA");
        btnConfirmar.setBackground(new Color(200, 255, 200));
        panelBotoes.add(btnSimular);
        panelBotoes.add(btnConfirmar);
        add(panelBotoes, BorderLayout.SOUTH);

        chkFinanciar.addActionListener(e -> {
            boolean sel = chkFinanciar.isSelected();
            txtEntrada.setEnabled(sel);
            txtParcelas.setEnabled(sel);
            txtJuros.setEnabled(sel);
            lblResumo.setText("Modo de pagamento alterado. Simule novamente.");
        });

        btnSimular.addActionListener(e -> simularValores());
        btnConfirmar.addActionListener(e -> realizarVenda());
    }

    private void carregarVeiculos() {
        comboVeiculos.removeAllItems();
        for (Veiculo v : controller.getEstoque()) {
            comboVeiculos.addItem(v.getModelo() + " - " + v.getMarca() + " (R$ " + v.getPreco() + ")");
        }
    }

    private void simularValores() {
        int index = comboVeiculos.getSelectedIndex();
        if (index < 0) return;
        Veiculo v = controller.getEstoque().get(index);

        if (chkFinanciar.isSelected()) {
            try {
                double entrada = Double.parseDouble(txtEntrada.getText());
                int parc = Integer.parseInt(txtParcelas.getText());
                double jur = Double.parseDouble(txtJuros.getText());

                if (entrada >= v.getPreco()) {
                    lblResumo.setText("Erro: Entrada maior que valor do veículo.");
                    return;
                }
                Financiamento fin = new Financiamento(v.getPreco(), entrada, parc, jur);
                double total = fin.calcularTotal();
                double parcela = (total - entrada) / parc;
                lblResumo.setText(String.format("<html>Total: R$ %.2f<br>Entrada: %.2f + %dx R$ %.2f</html>", total, entrada, parc, parcela));
            } catch (Exception ex) { lblResumo.setText("Erro nos números."); }
        } else {
            if (v instanceof IPromocional) {
                double desc = ((IPromocional) v).calcularDesconto(5.0);
                lblResumo.setText(String.format("À VISTA (Promo -5%%): R$ %.2f", desc));
            }
        }
    }

    private void realizarVenda() {
        int index = comboVeiculos.getSelectedIndex();
        if (index < 0) return;
        Veiculo v = controller.getEstoque().get(index);
        
        try {
            if (chkFinanciar.isSelected()) {
                double entrada = Double.parseDouble(txtEntrada.getText());
                int parc = Integer.parseInt(txtParcelas.getText());
                double jur = Double.parseDouble(txtJuros.getText());
                if (entrada >= v.getPreco()) {
                    JOptionPane.showMessageDialog(this, "Erro: Entrada inválida.");
                    return;
                }
                controller.venderVeiculo(v, true, entrada, parc, jur);
            } else {
                if (v instanceof IPromocional) {
                    double novoPreco = ((IPromocional) v).calcularDesconto(10.0);
                    v.setPreco(novoPreco);
                    JOptionPane.showMessageDialog(this, "Desconto aplicado!");
                }
                controller.venderVeiculo(v, false, 0, 0, 0);
            }
            JOptionPane.showMessageDialog(this, "Venda Sucesso!");
            dispose();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro nos dados."); }
    }
}