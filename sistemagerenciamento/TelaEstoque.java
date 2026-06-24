package sistemagerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TelaEstoque extends JFrame {

    private final Usuario usuario;
    private DefaultTableModel modeloHistorico;
    private DefaultTableModel modeloSaldo;

    private JTextField campoNome;
    private JTextField campoQtde;
    private JTextField campoPreco;
    private JComboBox<String> comboTipo;

    public TelaEstoque(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Estoque — Entrada e Saída de Produtos");
        setSize(860, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        carregarDados();
    }

    private void initComponents() {
        Color bg  = new Color(20, 23, 40);
        Color bg2 = new Color(30, 34, 54);

        JPanel principal = new JPanel(new BorderLayout(0, 10));
        principal.setBackground(bg);
        principal.setBorder(new EmptyBorder(16, 16, 16, 16));

        // --- TÍTULO ---
        JLabel titulo = new JLabel("📦  Estoque — Controle de Produtos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(20, 184, 166));
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        principal.add(titulo, BorderLayout.NORTH);

        // --- CADASTRO DE MOVIMENTAÇÃO ---
        JPanel painelCad = new JPanel(new GridBagLayout());
        painelCad.setBackground(bg2);
        painelCad.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(12, 14, 12, 14)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoNome  = new JTextField(16);
        comboTipo  = new JComboBox<>(new String[]{"Entrada", "Saída"});
        campoQtde  = new JTextField("1", 5);
        campoPreco = new JTextField(8);

        String[] labels = {"Nome do Produto", "Tipo", "Quantidade", "Preço Unit. (R$)"};
        JComponent[] campos = {campoNome, comboTipo, campoQtde, campoPreco};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = 0; gbc.gridx = i;
            painelCad.add(rotulo(labels[i]), gbc);
            gbc.gridy = 1; gbc.gridx = i;
            estilizar(campos[i]);
            painelCad.add(campos[i], gbc);
        }

        JButton btnReg = new JButton("+ Registrar Movimentação");
        estilizarBotao(btnReg, new Color(20, 184, 166));
        btnReg.addActionListener(e -> registrarMovimento());
        gbc.gridy = 1; gbc.gridx = 4;
        painelCad.add(btnReg, gbc);

        // --- SPLIT PANE: Saldo | Histórico ---
        // Tabela de Saldo por Produto
        String[] colsSaldo = {"Produto", "Saldo (Qtde)", "Preço Unit.", "Valor Total em Estoque"};
        modeloSaldo = new DefaultTableModel(colsSaldo, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabelaSaldo = new JTable(modeloSaldo);
        estilizarTabela(tabelaSaldo);
        JScrollPane scrollSaldo = new JScrollPane(tabelaSaldo);
        scrollSaldo.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                "  Posição do Estoque  "));
        scrollSaldo.getViewport().setBackground(bg);
        scrollSaldo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(20, 184, 166, 80)),
                        "  Saldo por Produto  ",
                        0, 0,
                        new Font("Segoe UI", Font.BOLD, 11),
                        new Color(20, 184, 166)),
                new EmptyBorder(4, 4, 4, 4)));

        // Tabela de Histórico
        String[] colsHist = {"#", "Data", "Produto", "Tipo", "Qtde", "Preço Unit.", "Usuário"};
        modeloHistorico = new DefaultTableModel(colsHist, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabelaHist = new JTable(modeloHistorico);
        estilizarTabela(tabelaHist);
        JScrollPane scrollHist = new JScrollPane(tabelaHist);
        scrollHist.getViewport().setBackground(bg);
        scrollHist.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(46, 53, 85)),
                        "  Histórico de Movimentações  ",
                        0, 0,
                        new Font("Segoe UI", Font.BOLD, 11),
                        new Color(130, 140, 180)),
                new EmptyBorder(4, 4, 4, 4)));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollSaldo, scrollHist);
        split.setDividerLocation(320);
        split.setBackground(bg);
        split.setBorder(null);

        // --- MONTAGEM ---
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(bg);
        centro.add(painelCad, BorderLayout.NORTH);
        centro.add(split,     BorderLayout.CENTER);

        principal.add(centro, BorderLayout.CENTER);
        add(principal);
    }

    private void registrarMovimento() {
        String nome = campoNome.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do produto.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qtde;
        double preco;
        try {
            qtde  = Integer.parseInt(campoQtde.getText().trim());
            preco = Double.parseDouble(campoPreco.getText().replace(",", ".").trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade e preço devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Valida saída
        if ("Saída".equals(tipo)) {
            int saldoAtual = SistemaData.getSaldoProduto(nome);
            if (saldoAtual < qtde) {
                JOptionPane.showMessageDialog(this,
                        "Estoque insuficiente!\nProduto: " + nome
                        + "\nSaldo atual: " + saldoAtual
                        + "\nSaída solicitada: " + qtde,
                        "Estoque Insuficiente", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Estoque mov = new Estoque(SistemaData.proximoIdEstoque++, nome, tipo, qtde, preco, usuario.getNome());
        SistemaData.estoque.add(mov);

        campoNome.setText(""); campoQtde.setText("1"); campoPreco.setText("");
        carregarDados();
        JOptionPane.showMessageDialog(this,
                tipo + " registrada!\nProduto: " + nome
                + "\nQuantidade: " + qtde
                + "\nSaldo atual: " + SistemaData.getSaldoProduto(nome),
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void carregarDados() {
        // Saldo por produto
        modeloSaldo.setRowCount(0);
        Map<String, double[]> mapa = new HashMap<>(); // nome -> [saldo, preco]
        for (Estoque m : SistemaData.estoque) {
            mapa.putIfAbsent(m.getNomeProduto(), new double[]{0, m.getPrecoUnitario()});
            mapa.get(m.getNomeProduto())[0] += m.getTipo().equals("Entrada") ? m.getQuantidade() : -m.getQuantidade();
            mapa.get(m.getNomeProduto())[1] = m.getPrecoUnitario();
        }
        for (Map.Entry<String, double[]> e : mapa.entrySet()) {
            int saldo   = (int) e.getValue()[0];
            double prec = e.getValue()[1];
            modeloSaldo.addRow(new Object[]{
                e.getKey(),
                "Qtde " + saldo + (saldo < 3 ? "  ⚠" : ""),
                String.format("R$ %.2f", prec),
                String.format("R$ %.2f", saldo * prec)
            });
        }

        // Histórico
        modeloHistorico.setRowCount(0);
        for (int i = SistemaData.estoque.size() - 1; i >= 0; i--) {
            Estoque m = SistemaData.estoque.get(i);
            modeloHistorico.addRow(new Object[]{
                "#" + m.getId(), m.getData(), m.getNomeProduto(),
                m.getTipo(), m.getQuantidade(),
                String.format("R$ %.2f", m.getPrecoUnitario()),
                m.getUsuario()
            });
        }
    }

    private JLabel rotulo(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(new Color(130, 140, 180));
        return l;
    }

    private void estilizar(JComponent c) {
        c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (c instanceof JTextField t) {
            t.setBackground(new Color(20, 23, 40));
            t.setForeground(new Color(220, 225, 255));
            t.setCaretColor(Color.WHITE);
            t.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(46, 53, 85)),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        }
        if (c instanceof JComboBox<?> cb) {
            cb.setBackground(new Color(20, 23, 40));
            cb.setForeground(new Color(220, 225, 255));
        }
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estilizarTabela(JTable t) {
        t.setBackground(new Color(20, 23, 40));
        t.setForeground(new Color(200, 210, 255));
        t.setGridColor(new Color(46, 53, 85));
        t.setSelectionBackground(new Color(20, 184, 166, 80));
        t.setSelectionForeground(Color.WHITE);
        t.setRowHeight(26);
        t.getTableHeader().setBackground(new Color(30, 34, 54));
        t.getTableHeader().setForeground(new Color(130, 140, 180));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
}
