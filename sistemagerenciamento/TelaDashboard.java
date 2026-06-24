package sistemagerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TelaDashboard extends JFrame {

    private final Usuario usuario;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaDashboard(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Dashboard — Visão Geral do Sistema");
        setSize(920, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        Color bg  = new Color(20, 23, 40);
        Color bg2 = new Color(30, 34, 54);

        JPanel principal = new JPanel(new BorderLayout(0, 12));
        principal.setBackground(bg);
        principal.setBorder(new EmptyBorder(16, 16, 16, 16));

        // --- CABEÇALHO ---
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(bg);

        JLabel titulo = new JLabel("📊  Dashboard — Visão Geral da Empresa");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(139, 92, 246));

        String dataHoje = LocalDate.now().format(FMT);
        JLabel lblData = new JLabel("Atualizado em: " + dataHoje + "  |  Usuário: " + usuario.getNome());
        lblData.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblData.setForeground(new Color(130, 140, 180));

        JButton btnAtualizar = new JButton("↻ Atualizar");
        btnAtualizar.setBackground(new Color(50, 35, 90));
        btnAtualizar.setForeground(new Color(139, 92, 246));
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnAtualizar.setBorderPainted(false);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.addActionListener(e -> { getContentPane().removeAll(); initComponents(); revalidate(); repaint(); });

        JPanel topoDir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topoDir.setBackground(bg);
        topoDir.add(lblData);
        topoDir.add(btnAtualizar);

        cabecalho.add(titulo,  BorderLayout.WEST);
        cabecalho.add(topoDir, BorderLayout.EAST);
        principal.add(cabecalho, BorderLayout.NORTH);

        // === MÉTRICAS KPI ===
        double faturamento = SistemaData.getFaturamentoTotal();
        long vendasPend    = SistemaData.vendas.stream().filter(v -> v.getStatus().equals("Pendente")).count();
        long vendasConf    = SistemaData.vendas.stream().filter(v -> v.getStatus().equals("Confirmado") || v.getStatus().equals("Entregue")).count();

        int totalItensEst  = calcTotalEstoque();
        int produtosBaixos = calcProdutosBaixos();

        long projAtivos  = SistemaData.projetos.stream().filter(p -> p.getStatus().equals("Em andamento")).count();
        long projConc    = SistemaData.projetos.stream().filter(p -> p.getStatus().equals("Concluído")).count();

        String hoje = LocalDate.now().format(FMT);
        long eventosHoje = SistemaData.eventos.stream().filter(e -> e.getData().equals(hoje)).count();

        JPanel metricas = new JPanel(new GridLayout(2, 4, 12, 12));
        metricas.setBackground(bg);

        metricas.add(criarCard("💰 Faturamento Total",
                String.format("R$ %.2f", faturamento), new Color(34, 197, 94),
                vendasConf + " vendas faturadas"));
        metricas.add(criarCard("🛒 Pedidos Pendentes",
                String.valueOf(vendasPend), new Color(245, 158, 11),
                SistemaData.vendas.size() + " pedidos no total"));
        metricas.add(criarCard("📦 Itens em Estoque",
                String.valueOf(totalItensEst), new Color(20, 184, 166),
                produtosBaixos > 0 ? "⚠ " + produtosBaixos + " produtos baixos" : "Estoque OK"));
        metricas.add(criarCard("📋 Projetos Ativos",
                String.valueOf(projAtivos), new Color(79, 110, 247),
                projConc + " concluídos"));
        metricas.add(criarCard("📅 Eventos Hoje",
                String.valueOf(eventosHoje), new Color(236, 72, 153),
                SistemaData.eventos.size() + " eventos cadastrados"));
        metricas.add(criarCard("👤 Usuários Cadastrados",
                String.valueOf(SistemaData.usuarios.size()), new Color(139, 92, 246),
                "Sistema ativo"));
        metricas.add(criarCard("🛒 Total de Vendas",
                String.valueOf(SistemaData.vendas.size()), new Color(200, 150, 50),
                "Desde o início"));
        metricas.add(criarCard("📊 Progresso Médio",
                calcProgressoMedio() + "%", new Color(100, 200, 150),
                "Dos projetos em andamento"));

        // === PAINEL INFERIOR: Gráfico Estoque | Atividades | Agenda ===
        JPanel painelInferior = new JPanel(new GridLayout(1, 3, 12, 0));
        painelInferior.setBackground(bg);

        painelInferior.add(criarPainelEstoque(bg2));
        painelInferior.add(criarPainelAtividades(bg2));
        painelInferior.add(criarPainelAgenda(bg2));

        // === MONTAGEM ===
        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setBackground(bg);
        centro.add(metricas,       BorderLayout.NORTH);
        centro.add(painelInferior, BorderLayout.CENTER);

        principal.add(centro, BorderLayout.CENTER);
        add(principal);
    }

    // --- Painel Estoque por Produto ---
    private JPanel criarPainelEstoque(Color bg2) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(bg2);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(12, 12, 12, 12)));

        JLabel lbl = new JLabel("📦  Saldo em Estoque");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(20, 184, 166));
        p.add(lbl, BorderLayout.NORTH);

        // Calcula saldo
        Map<String, Integer> saldo = new HashMap<>();
        for (Estoque m : SistemaData.estoque) {
            saldo.merge(m.getNomeProduto(),
                    m.getTipo().equals("Entrada") ? m.getQuantidade() : -m.getQuantidade(),
                    Integer::sum);
        }

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(bg2);

        for (Map.Entry<String, Integer> e : saldo.entrySet()) {
            int qtde = e.getValue();
            boolean baixo = qtde < 3;

            JPanel item = new JPanel(new BorderLayout(8, 0));
            item.setBackground(bg2);
            item.setBorder(new EmptyBorder(5, 0, 5, 0));
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

            JLabel nomeLbl = new JLabel(e.getKey());
            nomeLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            nomeLbl.setForeground(new Color(200, 210, 255));

            JLabel qtdeLbl = new JLabel("Qtde " + qtde + (baixo ? " ⚠" : ""));
            qtdeLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            qtdeLbl.setForeground(baixo ? new Color(239, 68, 68) : new Color(34, 197, 94));

            // Barra de progresso visual
            int maxQtde = 15;
            JPanel barra = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(40, 46, 70));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    int fill = (int) ((Math.min(qtde, maxQtde) / (double) maxQtde) * getWidth());
                    g.setColor(baixo ? new Color(239, 68, 68) : new Color(20, 184, 166));
                    g.fillRoundRect(0, 0, fill, getHeight(), 4, 4);
                }
            };
            barra.setOpaque(false);
            barra.setPreferredSize(new Dimension(0, 6));
            barra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));

            JPanel right = new JPanel(new GridLayout(2, 1, 0, 2));
            right.setBackground(bg2);
            right.add(qtdeLbl);
            right.add(barra);

            item.add(nomeLbl, BorderLayout.WEST);
            item.add(right,   BorderLayout.EAST);

            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(46, 53, 85));

            lista.add(item);
            lista.add(sep);
        }

        if (saldo.isEmpty()) {
            JLabel vazio = new JLabel("Sem produtos no estoque.");
            vazio.setForeground(new Color(130, 140, 180));
            vazio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lista.add(vazio);
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(bg2);
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    // --- Painel Últimas Atividades (Vendas) ---
    private JPanel criarPainelAtividades(Color bg2) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(bg2);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(12, 12, 12, 12)));

        JLabel lbl = new JLabel("🛒  Últimas Vendas");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(79, 110, 247));
        p.add(lbl, BorderLayout.NORTH);

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(bg2);

        List<Venda> recentes = SistemaData.vendas.stream()
                .sorted((a, b) -> b.getId() - a.getId())
                .limit(8)
                .collect(Collectors.toList());

        for (Venda v : recentes) {
            JPanel item = new JPanel(new BorderLayout(8, 0));
            item.setBackground(bg2);
            item.setBorder(new EmptyBorder(6, 0, 6, 0));
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

            JPanel esq = new JPanel(new GridLayout(2, 1, 0, 2));
            esq.setBackground(bg2);

            JLabel prod = new JLabel(v.getProduto());
            prod.setFont(new Font("Segoe UI", Font.BOLD, 12));
            prod.setForeground(new Color(200, 210, 255));

            JLabel cli = new JLabel(v.getCliente() + " · " + v.getData());
            cli.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            cli.setForeground(new Color(130, 140, 180));

            esq.add(prod);
            esq.add(cli);

            Color corStatus = switch (v.getStatus()) {
                case "Entregue"   -> new Color(34, 197, 94);
                case "Confirmado" -> new Color(79, 110, 247);
                case "Cancelado"  -> new Color(239, 68, 68);
                default           -> new Color(245, 158, 11);
            };

            JPanel dir = new JPanel(new GridLayout(2, 1, 0, 2));
            dir.setBackground(bg2);

            JLabel valor = new JLabel(String.format("R$ %.0f", v.getValorTotal()));
            valor.setFont(new Font("Segoe UI", Font.BOLD, 12));
            valor.setForeground(new Color(34, 197, 94));
            valor.setHorizontalAlignment(SwingConstants.RIGHT);

            JLabel status = new JLabel(v.getStatus());
            status.setFont(new Font("Segoe UI", Font.BOLD, 10));
            status.setForeground(corStatus);
            status.setHorizontalAlignment(SwingConstants.RIGHT);

            dir.add(valor);
            dir.add(status);

            item.add(esq, BorderLayout.CENTER);
            item.add(dir, BorderLayout.EAST);

            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(46, 53, 85));
            lista.add(item);
            lista.add(sep);
        }

        if (recentes.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma venda registrada.");
            vazio.setForeground(new Color(130, 140, 180));
            lista.add(vazio);
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(bg2);
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    // --- Painel Próximos Eventos da Agenda ---
    private JPanel criarPainelAgenda(Color bg2) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(bg2);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(12, 12, 12, 12)));

        JLabel lbl = new JLabel("📅  Próximos Eventos");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(236, 72, 153));
        p.add(lbl, BorderLayout.NORTH);

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(bg2);

        List<Evento> proximos = SistemaData.eventos.stream()
                .sorted((a, b) -> {
                    try {
                        return LocalDate.parse(a.getData(), FMT).compareTo(LocalDate.parse(b.getData(), FMT));
                    } catch (Exception e) { return 0; }
                })
                .limit(8)
                .collect(Collectors.toList());

        for (Evento ev : proximos) {
            JPanel item = new JPanel(new BorderLayout(8, 0));
            item.setBackground(bg2);
            item.setBorder(new EmptyBorder(6, 0, 6, 0));
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

            String icone = switch (ev.getCategoria()) {
                case "Festa/Evento" -> "🎉";
                case "Reunião"      -> "📋";
                case "Prazo"        -> "⏰";
                default             -> "📢";
            };

            Color corCat = switch (ev.getCategoria()) {
                case "Festa/Evento" -> new Color(236, 72, 153);
                case "Reunião"      -> new Color(20, 184, 166);
                case "Prazo"        -> new Color(245, 158, 11);
                default             -> new Color(79, 110, 247);
            };

            JPanel esq = new JPanel(new GridLayout(2, 1, 0, 2));
            esq.setBackground(bg2);

            JLabel tit = new JLabel(icone + " " + ev.getTitulo());
            tit.setFont(new Font("Segoe UI", Font.BOLD, 12));
            tit.setForeground(new Color(200, 210, 255));

            JLabel dt = new JLabel(ev.getData() + " às " + ev.getHorario());
            dt.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            dt.setForeground(new Color(130, 140, 180));

            esq.add(tit);
            esq.add(dt);

            JLabel cat = new JLabel(ev.getCategoria());
            cat.setFont(new Font("Segoe UI", Font.BOLD, 10));
            cat.setForeground(corCat);
            cat.setVerticalAlignment(SwingConstants.CENTER);

            item.add(esq,  BorderLayout.CENTER);
            item.add(cat,  BorderLayout.EAST);

            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(46, 53, 85));
            lista.add(item);
            lista.add(sep);
        }

        if (proximos.isEmpty()) {
            JLabel vazio = new JLabel("Nenhum evento cadastrado.");
            vazio.setForeground(new Color(130, 140, 180));
            lista.add(vazio);
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(bg2);
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    // --- Card de métrica ---
    private JPanel criarCard(String label, String valor, Color cor, String sub) {
        JPanel card = new JPanel(new BorderLayout(0, 4));
        card.setBackground(new Color(30, 34, 54));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(12, 14, 12, 14)));

        JLabel lblTitulo = new JLabel(label);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTitulo.setForeground(new Color(130, 140, 180));

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValor.setForeground(cor);

        JLabel lblSub = new JLabel(sub);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblSub.setForeground(new Color(100, 110, 150));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor,  BorderLayout.CENTER);
        card.add(lblSub,    BorderLayout.SOUTH);

        return card;
    }

    // --- Helpers de cálculo ---
    private int calcTotalEstoque() {
        Map<String, Integer> saldo = new HashMap<>();
        for (Estoque m : SistemaData.estoque) {
            saldo.merge(m.getNomeProduto(),
                    m.getTipo().equals("Entrada") ? m.getQuantidade() : -m.getQuantidade(),
                    Integer::sum);
        }
        return saldo.values().stream().mapToInt(Integer::intValue).sum();
    }

    private int calcProdutosBaixos() {
        Map<String, Integer> saldo = new HashMap<>();
        for (Estoque m : SistemaData.estoque) {
            saldo.merge(m.getNomeProduto(),
                    m.getTipo().equals("Entrada") ? m.getQuantidade() : -m.getQuantidade(),
                    Integer::sum);
        }
        return (int) saldo.values().stream().filter(v -> v < 3).count();
    }

    private int calcProgressoMedio() {
        List<Projeto> ativos = SistemaData.projetos.stream()
                .filter(p -> p.getStatus().equals("Em andamento"))
                .collect(Collectors.toList());
        if (ativos.isEmpty()) return 0;
        return (int) ativos.stream().mapToInt(Projeto::getProgresso).average().orElse(0);
    }
}
