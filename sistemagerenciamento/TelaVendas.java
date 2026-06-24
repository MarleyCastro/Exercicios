package sistemagerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaVendas extends JFrame {

    private final Usuario usuario;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JLabel lblTotal;
    private JLabel lblFaturado;

    // Campos de cadastro
    private JTextField campoCliente;
    private JTextField campoProduto;
    private JTextField campoQtde;
    private JTextField campoValor;
    private JComboBox<String> comboStatus;

    public TelaVendas(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Vendas e Pedidos");
        setSize(820, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        carregarTabela();
        atualizarTotais();
    }

    private void initComponents() {
        Color bg  = new Color(20, 23, 40);
        Color bg2 = new Color(30, 34, 54);

        JPanel principal = new JPanel(new BorderLayout(0, 10));
        principal.setBackground(bg);
        principal.setBorder(new EmptyBorder(16, 16, 16, 16));

        // --- TÍTULO ---
        JLabel titulo = new JLabel("🛒  Vendas e Pedidos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(200, 210, 255));
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        principal.add(titulo, BorderLayout.NORTH);

        // --- PAINEL DE CADASTRO ---
        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBackground(bg2);
        painelCadastro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(12, 14, 12, 14)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Cliente", "Produto", "Qtde", "Valor Unit. (R$)", "Status"};
        campoCliente = new JTextField(14);
        campoProduto = new JTextField(14);
        campoQtde    = new JTextField("1", 4);
        campoValor   = new JTextField(8);
        comboStatus  = new JComboBox<>(new String[]{"Pendente","Confirmado","Entregue","Cancelado"});

        JComponent[] campos = {campoCliente, campoProduto, campoQtde, campoValor, comboStatus};
        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = 0; gbc.gridx = i;
            painelCadastro.add(rotulo(labels[i]), gbc);
            gbc.gridy = 1; gbc.gridx = i;
            estilizar(campos[i]);
            painelCadastro.add(campos[i], gbc);
        }

        JButton btnAdicionar = new JButton("+ Registrar Venda");
        estilizarBotao(btnAdicionar, new Color(79, 110, 247));
        btnAdicionar.addActionListener(e -> registrarVenda());
        gbc.gridy = 1; gbc.gridx = 5;
        painelCadastro.add(btnAdicionar, gbc);

        // --- TABELA ---
        String[] colunas = {"#", "Data", "Cliente", "Produto", "Qtde", "Valor Unit.", "Total", "Status", "Usuário"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        estilizarTabela(tabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(46, 53, 85)));
        scroll.getViewport().setBackground(new Color(20, 23, 40));

        // --- TOTAIS e BOTÃO REMOVER ---
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(bg);
        rodape.setBorder(new EmptyBorder(8, 0, 0, 0));

        JPanel painelTotais = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        painelTotais.setBackground(bg);
        lblTotal    = new JLabel("Total geral: R$ 0,00");
        lblFaturado = new JLabel("Faturado: R$ 0,00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFaturado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotal.setForeground(new Color(200, 210, 255));
        lblFaturado.setForeground(new Color(34, 197, 94));
        painelTotais.add(lblTotal);
        painelTotais.add(lblFaturado);

        JButton btnRemover = new JButton("Remover Selecionado");
        estilizarBotao(btnRemover, new Color(100, 20, 20));
        btnRemover.setForeground(new Color(239, 68, 68));
        btnRemover.addActionListener(e -> removerVenda());

        rodape.add(painelTotais, BorderLayout.WEST);
        rodape.add(btnRemover,   BorderLayout.EAST);

        // --- MONTAGEM ---
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(bg);
        centro.add(painelCadastro, BorderLayout.NORTH);
        centro.add(scroll,         BorderLayout.CENTER);
        centro.add(rodape,         BorderLayout.SOUTH);

        principal.add(centro, BorderLayout.CENTER);
        add(principal);
    }

    private void registrarVenda() {
        String cliente = campoCliente.getText().trim();
        String produto = campoProduto.getText().trim();
        String status  = (String) comboStatus.getSelectedItem();

        if (cliente.isEmpty() || produto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha cliente e produto.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qtde;
        double valor;
        try {
            qtde  = Integer.parseInt(campoQtde.getText().trim());
            valor = Double.parseDouble(campoValor.getText().replace(",", ".").trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Qtde e valor devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Venda v = new Venda(SistemaData.proximoIdVenda++, cliente, produto, qtde, valor, status, usuario.getNome());
        SistemaData.vendas.add(v);

        campoCliente.setText(""); campoProduto.setText("");
        campoQtde.setText("1");   campoValor.setText("");

        carregarTabela();
        atualizarTotais();
        JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removerVenda() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString().replace("#", ""));
        int conf = JOptionPane.showConfirmDialog(this, "Remover a venda #" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            SistemaData.vendas.removeIf(v -> v.getId() == id);
            carregarTabela();
            atualizarTotais();
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Venda v : SistemaData.vendas) {
            modeloTabela.addRow(new Object[]{
                "#" + v.getId(), v.getData(), v.getCliente(), v.getProduto(),
                v.getQuantidade(),
                String.format("R$ %.2f", v.getValorUnitario()),
                String.format("R$ %.2f", v.getValorTotal()),
                v.getStatus(), v.getUsuario()
            });
        }
    }

    private void atualizarTotais() {
        double total    = SistemaData.vendas.stream().mapToDouble(Venda::getValorTotal).sum();
        double faturado = SistemaData.vendas.stream()
                .filter(v -> !v.getStatus().equals("Cancelado"))
                .mapToDouble(Venda::getValorTotal).sum();
        lblTotal.setText(String.format("Total geral: R$ %.2f", total));
        lblFaturado.setText(String.format("Faturado: R$ %.2f", faturado));
    }

    // === HELPERS VISUAIS ===
    private JLabel rotulo(String texto) {
        JLabel l = new JLabel(texto);
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
        btn.setPreferredSize(new Dimension(160, 32));
    }

    private void estilizarTabela(JTable t) {
        t.setBackground(new Color(20, 23, 40));
        t.setForeground(new Color(200, 210, 255));
        t.setGridColor(new Color(46, 53, 85));
        t.setSelectionBackground(new Color(79, 110, 247, 80));
        t.setSelectionForeground(Color.WHITE);
        t.setRowHeight(26);
        t.getTableHeader().setBackground(new Color(30, 34, 54));
        t.getTableHeader().setForeground(new Color(130, 140, 180));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
}
