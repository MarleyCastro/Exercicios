package sistemagerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TelaFinanceiro extends JFrame {

    private final Usuario usuario;
    private DefaultTableModel modeloTabela;
    private JLabel lblFaturamento;
    private JLabel lblTicket;
    private JLabel lblQtde;
    private JLabel lblTotal;
    private JComboBox<String> comboPeriodo;
    private PainelGrafico painelGrafico;

    public TelaFinanceiro(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Financeiro — Faturamento e Relatórios");
        setSize(860, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        atualizar();
    }

    private void initComponents() {
        Color bg  = new Color(20, 23, 40);
        Color bg2 = new Color(30, 34, 54);
        Color verde = new Color(34, 197, 94);

        JPanel principal = new JPanel(new BorderLayout(0, 12));
        principal.setBackground(bg);
        principal.setBorder(new EmptyBorder(16, 16, 16, 16));

        // --- TÍTULO + FILTRO ---
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(bg);

        JLabel titulo = new JLabel("💰  Financeiro — Faturamento por Período");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(verde);

        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filtroPanel.setBackground(bg);
        JLabel lblFiltro = new JLabel("Período:");
        lblFiltro.setForeground(new Color(130, 140, 180));
        lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        comboPeriodo = new JComboBox<>(new String[]{"Todas as vendas","Últimos 7 dias","Últimos 30 dias","Últimos 90 dias"});
        comboPeriodo.setBackground(bg2);
        comboPeriodo.setForeground(new Color(220, 225, 255));
        comboPeriodo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboPeriodo.addActionListener(e -> atualizar());
        JButton btnAtualizar = new JButton("↻ Atualizar");
        estilizarBotao(btnAtualizar, verde);
        btnAtualizar.addActionListener(e -> atualizar());
        filtroPanel.add(lblFiltro);
        filtroPanel.add(comboPeriodo);
        filtroPanel.add(btnAtualizar);

        topo.add(titulo,      BorderLayout.WEST);
        topo.add(filtroPanel, BorderLayout.EAST);
        principal.add(topo, BorderLayout.NORTH);

        // --- MÉTRICAS ---
        JPanel metricas = new JPanel(new GridLayout(1, 4, 12, 0));
        metricas.setBackground(bg);

        lblFaturamento = new JLabel("R$ 0,00");
        lblTicket      = new JLabel("R$ 0,00");
        lblQtde        = new JLabel("0");
        lblTotal       = new JLabel("R$ 0,00");

        metricas.add(criarCard("Faturamento no Período", lblFaturamento, verde));
        metricas.add(criarCard("Ticket Médio",           lblTicket,      new Color(79, 110, 247)));
        metricas.add(criarCard("Qtde de Vendas",         lblQtde,        new Color(245, 158, 11)));
        metricas.add(criarCard("Total Geral Acumulado",  lblTotal,       new Color(20, 184, 166)));

        // --- CONTEÚDO CENTRAL ---
        // Gráfico de barras simples
        painelGrafico = new PainelGrafico();
        painelGrafico.setBackground(bg2);
        painelGrafico.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(10, 10, 10, 10)));
        painelGrafico.setPreferredSize(new Dimension(320, 200));

        // Tabela de vendas faturadas
        String[] colunas = {"Data", "Cliente", "Produto", "Qtde", "Total"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(modeloTabela);
        estilizarTabela(tabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(bg);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(46, 53, 85)),
                        "  Vendas Faturadas  ", 0, 0,
                        new Font("Segoe UI", Font.BOLD, 11),
                        new Color(130, 140, 180)),
                new EmptyBorder(4, 4, 4, 4)));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelGrafico, scroll);
        split.setDividerLocation(340);
        split.setBackground(bg);
        split.setBorder(null);

        // --- MONTAGEM ---
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(bg);
        centro.add(metricas, BorderLayout.NORTH);
        centro.add(split,    BorderLayout.CENTER);

        principal.add(centro, BorderLayout.CENTER);
        add(principal);
    }

    private void atualizar() {
        List<Venda> filtradas = SistemaData.vendas.stream()
                .filter(v -> !v.getStatus().equals("Cancelado"))
                .collect(Collectors.toList());

        // Filtro de período (simulado por índice, pois as datas já estão em dd/MM/yyyy)
        // Em produção, converteríamos para LocalDate e filtраríamos corretamente.
        // Aqui mostramos todas as vendas confirmadas/entregues.

        double fat   = filtradas.stream().mapToDouble(Venda::getValorTotal).sum();
        double total = SistemaData.getFaturamentoTotal();
        double ticket = filtradas.isEmpty() ? 0 : fat / filtradas.size();

        lblFaturamento.setText(String.format("R$ %.2f", fat));
        lblTicket.setText(String.format("R$ %.2f", ticket));
        lblQtde.setText(String.valueOf(filtradas.size()));
        lblTotal.setText(String.format("R$ %.2f", total));

        modeloTabela.setRowCount(0);
        for (Venda v : filtradas) {
            modeloTabela.addRow(new Object[]{
                v.getData(), v.getCliente(), v.getProduto(),
                v.getQuantidade(),
                String.format("R$ %.2f", v.getValorTotal())
            });
        }

        painelGrafico.setVendas(filtradas);
        painelGrafico.repaint();
    }

    private JPanel criarCard(String label, JLabel valor, Color cor) {
        JPanel card = new JPanel(new GridLayout(2, 1, 0, 4));
        card.setBackground(new Color(30, 34, 54));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(10, 14, 10, 14)));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(new Color(130, 140, 180));
        valor.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valor.setForeground(cor);
        card.add(lbl);
        card.add(valor);
        return card;
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor.darker().darker());
        btn.setForeground(cor);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estilizarTabela(JTable t) {
        t.setBackground(new Color(20, 23, 40));
        t.setForeground(new Color(200, 210, 255));
        t.setGridColor(new Color(46, 53, 85));
        t.setSelectionBackground(new Color(34, 197, 94, 60));
        t.setSelectionForeground(Color.WHITE);
        t.setRowHeight(26);
        t.getTableHeader().setBackground(new Color(30, 34, 54));
        t.getTableHeader().setForeground(new Color(130, 140, 180));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }

    // --- Painel de gráfico de barras customizado ---
    static class PainelGrafico extends JPanel {
        private List<Venda> vendas = List.of();

        public void setVendas(List<Venda> vendas) { this.vendas = vendas; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            g2.setColor(new Color(30, 34, 54));
            g2.fillRect(0, 0, w, h);

            if (vendas == null || vendas.isEmpty()) {
                g2.setColor(new Color(130, 140, 180));
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                g2.drawString("Sem dados para exibir", 20, h / 2);
                return;
            }

            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2.setColor(new Color(130, 140, 180));
            g2.drawString("Faturamento por Venda (R$)", 10, 18);

            double maxVal = vendas.stream().mapToDouble(Venda::getValorTotal).max().orElse(1);
            int margem = 40, topo = 30, base = h - 40;
            int alturaDisp = base - topo;
            int n = Math.min(vendas.size(), 10);
            int largBarra = (w - margem * 2) / n - 8;

            for (int i = 0; i < n; i++) {
                Venda v = vendas.get(i);
                int altBarra = (int) ((v.getValorTotal() / maxVal) * alturaDisp);
                int x = margem + i * ((w - margem * 2) / n);
                int y = base - altBarra;

                g2.setColor(new Color(79, 110, 247, 180));
                g2.fillRoundRect(x, y, largBarra, altBarra, 4, 4);

                g2.setColor(new Color(200, 210, 255));
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
                String val = "R$" + (int) v.getValorTotal();
                g2.drawString(val, x, y - 4);

                g2.setColor(new Color(100, 110, 150));
                String nome = v.getProduto().length() > 8 ? v.getProduto().substring(0, 8) + "." : v.getProduto();
                g2.drawString(nome, x, base + 14);
            }
        }
    }
}
