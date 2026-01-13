package br.com.equipe7.concessionaria.view;

import br.com.equipe7.concessionaria.controller.ConcessionariaController;
import br.com.equipe7.concessionaria.model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class SistemaConcessionaria extends JFrame {
    private ConcessionariaController controller = new ConcessionariaController();
    private JComboBox<String> comboTipo;
    private JTextField txtMarca, txtModelo, txtAno, txtCor, txtPreco, txtExtra; // txtCor adicionado
    private JLabel lblExtra;
    private JTextArea areaListagem;

    public SistemaConcessionaria() {
        setTitle("QuixasVeículos - Equipe 7");
        setSize(900, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        // --- TOPO ---
        JPanel panelTopo = new JPanel(new BorderLayout());
        panelTopo.setBorder(BorderFactory.createTitledBorder(" Novo Veículo "));
        
        // Grid 4x4
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10)); 
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Linha 1
        formPanel.add(new JLabel("Tipo de Veículo:"));
        comboTipo = new JComboBox<>(new String[]{"Carro", "Moto"});
        formPanel.add(comboTipo);
        
        formPanel.add(new JLabel("Marca:"));
        txtMarca = new JTextField();
        formPanel.add(txtMarca);

        // Linha 2
        formPanel.add(new JLabel("Modelo:"));
        txtModelo = new JTextField();
        formPanel.add(txtModelo);

        formPanel.add(new JLabel("Cor:"));
        txtCor = new JTextField();
        formPanel.add(txtCor);

        // Linha 3
        formPanel.add(new JLabel("Ano Fab.:"));
        txtAno = new JTextField();
        formPanel.add(txtAno);

        formPanel.add(new JLabel("Preço (R$):"));
        txtPreco = new JTextField();
        formPanel.add(txtPreco);

        // Linha 4
        lblExtra = new JLabel("Nº Portas:");
        formPanel.add(lblExtra);
        txtExtra = new JTextField();
        formPanel.add(txtExtra);
        
        formPanel.add(new JLabel(""));
        JButton btnAdicionar = new JButton("Adicionar ao Estoque");
        btnAdicionar.setBackground(new Color(220, 230, 255));
        formPanel.add(btnAdicionar);

        panelTopo.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(panelTopo, BorderLayout.NORTH);

        // --- CENTRO ---
        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaListagem);
        scroll.setBorder(BorderFactory.createTitledBorder(" Estoque Atual "));
        mainPanel.add(scroll, BorderLayout.CENTER);

        // --- RODAPÉ ---
        JPanel panelBotoes = new JPanel(new GridLayout(1, 3, 10, 0));
        panelBotoes.setPreferredSize(new Dimension(0, 80));

        JPanel gpGestao = new JPanel(new GridLayout(2, 1, 5, 5));
        gpGestao.setBorder(BorderFactory.createTitledBorder("Gerenciar"));
        JButton btnEditar = new JButton("Editar Veículo");
        JButton btnRemover = new JButton("Remover Veículo");
        gpGestao.add(btnEditar);
        gpGestao.add(btnRemover);

        JPanel gpOperacoes = new JPanel(new GridLayout(2, 1, 5, 5));
        gpOperacoes.setBorder(BorderFactory.createTitledBorder("Operações"));
        JButton btnVenda = new JButton("Realizar Venda");
        JButton btnRevisao = new JButton("Agendar Revisão");
        btnVenda.setFont(new Font("SansSerif", Font.BOLD, 12));
        gpOperacoes.add(btnVenda);
        gpOperacoes.add(btnRevisao);

        JPanel gpSistema = new JPanel(new GridLayout(2, 2, 5, 5));
        gpSistema.setBorder(BorderFactory.createTitledBorder("Sistema"));
        JButton btnListar = new JButton("Atualizar");
        JButton btnRelatorio = new JButton("Relatório");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCarregar = new JButton("Carregar");
        gpSistema.add(btnListar);
        gpSistema.add(btnRelatorio);
        gpSistema.add(btnSalvar);
        gpSistema.add(btnCarregar);

        panelBotoes.add(gpGestao);
        panelBotoes.add(gpOperacoes);
        panelBotoes.add(gpSistema);
        mainPanel.add(panelBotoes, BorderLayout.SOUTH);

        // --- LISTENERS ---
        comboTipo.addActionListener(e -> {
            if (comboTipo.getSelectedItem().equals("Carro")) lblExtra.setText("Nº Portas:");
            else lblExtra.setText("Cilindradas (cc):");
        });

        btnAdicionar.addActionListener(e -> {
            try {
                String marca = txtMarca.getText();
                String modelo = txtModelo.getText();
                String cor = txtCor.getText(); // Lendo a cor
                int ano = Integer.parseInt(txtAno.getText());
                double preco = Double.parseDouble(txtPreco.getText());
                int extra = Integer.parseInt(txtExtra.getText());

                Veiculo v;
                if (comboTipo.getSelectedItem().equals("Carro")) v = new Carro(marca, modelo, ano, cor, preco, extra);
                else v = new Moto(marca, modelo, ano, cor, preco, extra);
                
                controller.adicionarVeiculo(v);
                JOptionPane.showMessageDialog(this, "Veículo adicionado!");
                
                txtMarca.setText(""); txtModelo.setText(""); txtCor.setText("");
                txtAno.setText(""); txtPreco.setText(""); txtExtra.setText("");
                
                atualizarListagem();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro nos dados! Verifique números."); }
        });

        btnRemover.addActionListener(e -> {
            String in = JOptionPane.showInputDialog("Nº para REMOVER:");
            if (in != null) {
                try {
                    if (controller.removerVeiculo(Integer.parseInt(in) - 1)) {
                        JOptionPane.showMessageDialog(this, "Removido!");
                        atualizarListagem();
                    } else JOptionPane.showMessageDialog(this, "Não encontrado.");
                } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Inválido."); }
            }
        });

        btnEditar.addActionListener(e -> {
            String in = JOptionPane.showInputDialog("Nº para EDITAR:");
            if (in != null) {
                try {
                    int idx = Integer.parseInt(in) - 1;
                    if (idx >= 0 && idx < controller.getEstoque().size()) {
                        new TelaEdicao(this, controller.getEstoque().get(idx)).setVisible(true);
                        atualizarListagem();
                    } else JOptionPane.showMessageDialog(this, "Não encontrado.");
                } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Inválido."); }
            }
        });

        // Botões restantes
        btnVenda.addActionListener(e -> {
             if (!controller.getEstoque().isEmpty()) {
                 new TelaVenda(this, controller).setVisible(true);
                 atualizarListagem();
             } else JOptionPane.showMessageDialog(this, "Estoque vazio.");
        });

        btnRevisao.addActionListener(e -> {
             if (!controller.getEstoque().isEmpty()) new TelaRevisao(this, controller).setVisible(true);
             else JOptionPane.showMessageDialog(this, "Estoque vazio.");
        });

        btnListar.addActionListener(e -> atualizarListagem());
        
        btnRelatorio.addActionListener(e -> {
            JTextArea ta = new JTextArea(controller.gerarRelatorio());
            ta.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Relatório", JOptionPane.INFORMATION_MESSAGE);
        });

        btnSalvar.addActionListener(e -> {
            try { controller.salvarDados(); JOptionPane.showMessageDialog(this, "Salvo!"); } 
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao salvar."); }
        });

        btnCarregar.addActionListener(e -> {
            try { controller.carregarDados(); atualizarListagem(); JOptionPane.showMessageDialog(this, "Carregado!"); } 
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao carregar (formato pode ser incompatível)."); }
        });
    }

    private void atualizarListagem() {
        areaListagem.setText("");
        List<Veiculo> est = controller.getEstoque();
        if(est.isEmpty()) {
            areaListagem.append("   O estoque está vazio.");
            return;
        }
        for (int i = 0; i < est.size(); i++) {
            Veiculo v = est.get(i);
            String status = v.isRevisado() ? "[REVISADO] " : "";
            areaListagem.append(String.format(" %02d. %s%s\n", (i+1), status, v.getDetalhes()));
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new SistemaConcessionaria().setVisible(true));
    }
}