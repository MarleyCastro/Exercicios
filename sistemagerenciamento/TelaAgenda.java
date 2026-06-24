package sistemagerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class TelaAgenda extends JFrame {

    private final Usuario usuario;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private PainelCalendario painelCal;

    private JTextField   campoTitulo;
    private JTextField   campoData;
    private JTextField   campoHorario;
    private JTextField   campoResp;
    private JTextArea    areaDesc;
    private JComboBox<String> comboCat;

    private static final DateTimeFormatter FMT_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaAgenda(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Agenda — Eventos e Avisos da Empresa");
        setSize(960, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        carregarTabela();
    }

    private void initComponents() {
        Color bg    = new Color(20, 23, 40);
        Color bg2   = new Color(30, 34, 54);
        Color pink  = new Color(236, 72, 153);

        JPanel principal = new JPanel(new BorderLayout(0, 10));
        principal.setBackground(bg);
        principal.setBorder(new EmptyBorder(16, 16, 16, 16));

        // --- TÍTULO ---
        JLabel titulo = new JLabel("📅  Agenda — Eventos, Festas e Avisos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(pink);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        principal.add(titulo, BorderLayout.NORTH);

        // --- FORMULÁRIO ---
        JPanel painelForm = new JPanel(new BorderLayout(0, 8));
        painelForm.setBackground(bg2);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(12, 14, 12, 14)));

        JPanel linha1 = new JPanel(new GridBagLayout());
        linha1.setBackground(bg2);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 5, 3, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        campoTitulo  = new JTextField(18);
        campoData    = new JTextField("dd/MM/yyyy", 10);
        campoHorario = new JTextField("09:00", 6);
        comboCat     = new JComboBox<>(new String[]{"Aviso", "Festa/Evento", "Reunião", "Prazo"});
        campoResp    = new JTextField(12);

        String[]     labels = {"Título do Evento",  "Data (dd/MM/yyyy)", "Horário", "Categoria",  "Responsável"};
        JComponent[] campos = {campoTitulo,          campoData,           campoHorario, comboCat,  campoResp};

        for (int i = 0; i < labels.length; i++) {
            g.gridx = i; g.gridy = 0; linha1.add(rotulo(labels[i]), g);
            g.gridx = i; g.gridy = 1; estilizar(campos[i]); linha1.add(campos[i], g);
        }

        // Botão agendar
        JButton btnAgendar = new JButton("📅  Agendar Evento");
        estilizarBotao(btnAgendar, pink);
        btnAgendar.addActionListener(e -> agendarEvento());
        g.gridx = 5; g.gridy = 1; linha1.add(btnAgendar, g);

        // Linha 2: descrição
        JPanel linha2 = new JPanel(new BorderLayout(8, 3));
        linha2.setBackground(bg2);
        linha2.add(rotulo("Descrição / Detalhes do evento"), BorderLayout.NORTH);

        areaDesc = new JTextArea(2, 60);
        areaDesc.setBackground(new Color(20, 23, 40));
        areaDesc.setForeground(new Color(220, 225, 255));
        areaDesc.setCaretColor(Color.WHITE);
        areaDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        areaDesc.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        areaDesc.setLineWrap(true);
        areaDesc.setWrapStyleWord(true);
        linha2.add(new JScrollPane(areaDesc), BorderLayout.CENTER);

        painelForm.add(linha1, BorderLayout.NORTH);
        painelForm.add(linha2, BorderLayout.CENTER);

        // --- CONTEÚDO CENTRAL: Calendário | Tabela ---
        // Calendário
        painelCal = new PainelCalendario();
        painelCal.setPreferredSize(new Dimension(260, 0));
        painelCal.setBackground(bg2);
        painelCal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(10, 10, 10, 10)));

        // Tabela de eventos
        String[] colunas = {"#", "Data", "Horário", "Categoria", "Título", "Responsável", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        estilizarTabela(tabela);

        // Colora linhas por categoria
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                c.setBackground(new Color(20, 23, 40));
                c.setForeground(new Color(200, 210, 255));
                if (!sel) {
                    String cat = modeloTabela.getValueAt(row, 3).toString();
                    c.setBackground(switch (cat) {
                        case "Aviso"       -> new Color(30, 40, 70);
                        case "Festa/Evento"-> new Color(50, 20, 40);
                        case "Reunião"     -> new Color(20, 40, 40);
                        case "Prazo"       -> new Color(50, 35, 10);
                        default            -> new Color(20, 23, 40);
                    });
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(new Color(20, 23, 40));
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(46, 53, 85)),
                        "  Próximos Eventos  ", 0, 0,
                        new Font("Segoe UI", Font.BOLD, 11),
                        new Color(130, 140, 180)),
                new EmptyBorder(4, 4, 4, 4)));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelCal, scroll);
        split.setDividerLocation(270);
        split.setBackground(bg);
        split.setBorder(null);

        // --- RODAPÉ ---
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rodape.setBackground(bg);
        rodape.setBorder(new EmptyBorder(6, 0, 0, 0));

        JButton btnRemover = new JButton("🗑  Remover Evento Selecionado");
        estilizarBotaoNeutro(btnRemover);
        btnRemover.setForeground(new Color(239, 68, 68));
        btnRemover.addActionListener(e -> removerEvento());

        JButton btnHoje = new JButton("📆  Ver Eventos de Hoje");
        estilizarBotaoNeutro(btnHoje);
        btnHoje.addActionListener(e -> filtrarHoje());

        JButton btnTodos = new JButton("↺  Mostrar Todos");
        estilizarBotaoNeutro(btnTodos);
        btnTodos.addActionListener(e -> carregarTabela());

        rodape.add(btnRemover);
        rodape.add(btnHoje);
        rodape.add(btnTodos);

        // --- MONTAGEM ---
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(bg);
        centro.add(painelForm, BorderLayout.NORTH);
        centro.add(split,      BorderLayout.CENTER);
        centro.add(rodape,     BorderLayout.SOUTH);

        principal.add(centro, BorderLayout.CENTER);
        add(principal);
    }

    private void agendarEvento() {
        String titulo  = campoTitulo.getText().trim();
        String data    = campoData.getText().trim();
        String horario = campoHorario.getText().trim();
        String cat     = (String) comboCat.getSelectedItem();
        String resp    = campoResp.getText().trim();
        String desc    = areaDesc.getText().trim();

        if (titulo.isEmpty() || data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título e data são obrigatórios.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida formato de data
        try {
            LocalDate.parse(data, FMT_BR);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Evento ev = new Evento(SistemaData.proximoIdEvento++, titulo, desc, data, horario, cat,
                resp.isEmpty() ? usuario.getNome() : resp);
        SistemaData.eventos.add(ev);

        campoTitulo.setText(""); campoData.setText("dd/MM/yyyy");
        campoHorario.setText("09:00"); campoResp.setText(""); areaDesc.setText("");

        carregarTabela();
        painelCal.repaint();
        JOptionPane.showMessageDialog(this, "Evento agendado com sucesso!\n" + ev, "Agendado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removerEvento() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString().replace("#", ""));
        String nomeEv = modeloTabela.getValueAt(linha, 4).toString();
        int conf = JOptionPane.showConfirmDialog(this,
                "Remover o evento \"" + nomeEv + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            SistemaData.eventos.removeIf(e -> e.getId() == id);
            carregarTabela();
            painelCal.repaint();
        }
    }

    private void filtrarHoje() {
        String hoje = LocalDate.now().format(FMT_BR);
        modeloTabela.setRowCount(0);
        for (Evento ev : SistemaData.eventos) {
            if (ev.getData().equals(hoje)) {
                modeloTabela.addRow(linhaEvento(ev));
            }
        }
        if (modeloTabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum evento para hoje (" + hoje + ").",
                    "Hoje", JOptionPane.INFORMATION_MESSAGE);
            carregarTabela();
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        SistemaData.eventos.stream()
                .sorted((a, b) -> {
                    try {
                        LocalDate da = LocalDate.parse(a.getData(), FMT_BR);
                        LocalDate db = LocalDate.parse(b.getData(), FMT_BR);
                        return da.compareTo(db);
                    } catch (Exception e) { return 0; }
                })
                .forEach(ev -> modeloTabela.addRow(linhaEvento(ev)));
    }

    private Object[] linhaEvento(Evento ev) {
        String icone = switch (ev.getCategoria()) {
            case "Aviso"        -> "📢 Aviso";
            case "Festa/Evento" -> "🎉 Festa/Evento";
            case "Reunião"      -> "📋 Reunião";
            case "Prazo"        -> "⏰ Prazo";
            default             -> ev.getCategoria();
        };
        return new Object[]{
            "#" + ev.getId(), ev.getData(), ev.getHorario(),
            icone, ev.getTitulo(),
            ev.getResponsavel(), ev.getDescricao()
        };
    }

    // === HELPERS VISUAIS ===
    private JLabel rotulo(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(new Color(130, 140, 180));
        return l;
    }

    private void estilizar(JComponent c) {
        c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (c instanceof JTextField tf) {
            tf.setBackground(new Color(20, 23, 40));
            tf.setForeground(new Color(220, 225, 255));
            tf.setCaretColor(Color.WHITE);
            tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(46, 53, 85)),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        }
        if (c instanceof JComboBox<?> cb) {
            cb.setBackground(new Color(20, 23, 40));
            cb.setForeground(new Color(220, 225, 255));
        }
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor.darker());
        btn.setForeground(cor.brighter());
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 34));
    }

    private void estilizarBotaoNeutro(JButton btn) {
        btn.setBackground(new Color(40, 46, 70));
        btn.setForeground(new Color(160, 170, 210));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createLineBorder(new Color(46, 53, 85)));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estilizarTabela(JTable t) {
        t.setBackground(new Color(20, 23, 40));
        t.setForeground(new Color(200, 210, 255));
        t.setGridColor(new Color(46, 53, 85));
        t.setSelectionBackground(new Color(236, 72, 153, 60));
        t.setSelectionForeground(Color.WHITE);
        t.setRowHeight(26);
        t.getTableHeader().setBackground(new Color(30, 34, 54));
        t.getTableHeader().setForeground(new Color(130, 140, 180));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }

    // === PAINEL CALENDÁRIO CUSTOMIZADO ===
    class PainelCalendario extends JPanel {

        private static final String[] DIAS_SEM = {"Dom","Seg","Ter","Qua","Qui","Sex","Sáb"};

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            g2.setColor(new Color(30, 34, 54));
            g2.fillRect(0, 0, w, h);

            LocalDate hoje = LocalDate.now();
            YearMonth ym   = YearMonth.of(hoje.getYear(), hoje.getMonth());
            int diasNoMes  = ym.lengthOfMonth();
            int primeiroDia = ym.atDay(1).getDayOfWeek().getValue() % 7; // Dom=0

            // Título do mês
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g2.setColor(new Color(236, 72, 153));
            String mesTitulo = hoje.getMonth().getDisplayName(
                    java.time.format.TextStyle.FULL, new java.util.Locale("pt", "BR"))
                    + " " + hoje.getYear();
            g2.drawString(mesTitulo.substring(0, 1).toUpperCase() + mesTitulo.substring(1), 8, 22);

            // Nomes dos dias da semana
            int colW = (w - 10) / 7;
            g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
            g2.setColor(new Color(130, 140, 180));
            for (int i = 0; i < 7; i++) {
                g2.drawString(DIAS_SEM[i], 6 + i * colW, 40);
            }

            // Coleta dias com eventos no mês atual
            java.util.Set<Integer> diasComEvento = new java.util.HashSet<>();
            for (Evento ev : SistemaData.eventos) {
                try {
                    LocalDate d = LocalDate.parse(ev.getData(), FMT_BR);
                    if (d.getYear() == hoje.getYear() && d.getMonth() == hoje.getMonth()) {
                        diasComEvento.add(d.getDayOfMonth());
                    }
                } catch (Exception ignored) {}
            }

            // Células dos dias
            int linha = 0, col = primeiroDia;
            for (int dia = 1; dia <= diasNoMes; dia++) {
                int x = 6 + col * colW;
                int y = 52 + linha * 30;
                int cx = x + colW / 2 - 10;

                boolean ehHoje     = dia == hoje.getDayOfMonth();
                boolean temEvento  = diasComEvento.contains(dia);

                if (ehHoje) {
                    g2.setColor(new Color(236, 72, 153));
                    g2.fillRoundRect(cx - 2, y - 14, 22, 20, 6, 6);
                    g2.setColor(Color.WHITE);
                } else if (temEvento) {
                    g2.setColor(new Color(79, 110, 247, 60));
                    g2.fillRoundRect(cx - 2, y - 14, 22, 20, 6, 6);
                    g2.setColor(new Color(79, 110, 247));
                } else {
                    g2.setColor(new Color(160, 170, 210));
                }

                g2.setFont(new Font("Segoe UI", ehHoje ? Font.BOLD : Font.PLAIN, 11));
                g2.drawString(String.format("%2d", dia), cx, y);

                // Ponto indicador de evento
                if (temEvento && !ehHoje) {
                    g2.setColor(new Color(236, 72, 153));
                    g2.fillOval(cx + 6, y + 3, 4, 4);
                }

                col++;
                if (col == 7) { col = 0; linha++; }
            }

            // Legenda
            int ly = 52 + 6 * 30 + 10;
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(new Color(236, 72, 153));
            g2.fillRoundRect(6, ly, 10, 10, 4, 4);
            g2.setColor(new Color(130, 140, 180));
            g2.drawString("Hoje", 20, ly + 9);

            g2.setColor(new Color(79, 110, 247));
            g2.fillRoundRect(55, ly, 10, 10, 4, 4);
            g2.setColor(new Color(130, 140, 180));
            g2.drawString("Tem evento", 69, ly + 9);
        }
    }
}
