package sistemagerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private final Usuario usuario;

    public TelaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Sistema de Gerenciamento — Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        Color bgEscuro    = new Color(20, 23, 40);
        Color bgPainel    = new Color(30, 34, 54);
        Color corTexto    = new Color(200, 210, 255);
        Color corSubtexto = new Color(130, 140, 180);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(bgEscuro);

        // === CABEÇALHO ===
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(bgEscuro);
        cabecalho.setBorder(new EmptyBorder(20, 30, 16, 30));

        JPanel infoUsuario = new JPanel(new GridLayout(2, 1));
        infoUsuario.setBackground(bgEscuro);

        JLabel lblBemVindo = new JLabel("⚙ Sistema de Gerenciamento Empresarial");
        lblBemVindo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBemVindo.setForeground(corTexto);

        JLabel lblUsuario = new JLabel("Logado como: " + usuario.getNome()
                + " <" + usuario.getEmail() + ">");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblUsuario.setForeground(corSubtexto);

        infoUsuario.add(lblBemVindo);
        infoUsuario.add(lblUsuario);

        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnSair.setBackground(new Color(69, 10, 10));
        btnSair.setForeground(new Color(239, 68, 68));
        btnSair.setBorderPainted(false);
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> confirmarSaida());

        cabecalho.add(infoUsuario, BorderLayout.CENTER);
        cabecalho.add(btnSair, BorderLayout.EAST);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(46, 53, 85));
        sep.setBackground(bgEscuro);

        // === PAINEL DE BOTÕES ===
        JPanel painelBotoes = new JPanel(new GridLayout(3, 2, 16, 16));
        painelBotoes.setBackground(bgPainel);
        painelBotoes.setBorder(new EmptyBorder(24, 30, 24, 30));

        // Definições dos módulos: [titulo, subtitulo, cor, emoji]
        Object[][] modulos = {
            {"Vendas e Pedidos",   "Registrar e acompanhar pedidos",  new Color(79, 110, 247), "🛒"},
            {"Estoque",            "Entrada e saída de produtos",      new Color(20, 184, 166), "📦"},
            {"Financeiro",         "Faturamento por período",          new Color(34, 197, 94),  "💰"},
            {"Projetos e Tarefas", "Projetos e tarefas diárias",       new Color(245, 158, 11), "📋"},
            {"Agenda",             "Eventos, festas e avisos",         new Color(236, 72, 153), "📅"},
            {"Dashboard",          "Visão geral do sistema",           new Color(139, 92, 246), "📊"},
        };

        for (Object[] mod : modulos) {
            painelBotoes.add(criarBotaoModulo(
                    (String) mod[0], (String) mod[1], (Color) mod[2], (String) mod[3]));
        }

        // === RODAPÉ ===
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setBackground(bgEscuro);
        rodape.setBorder(new EmptyBorder(8, 30, 10, 30));

        JLabel lblRodape = new JLabel("Sistema Empresarial v1.0 — " + usuario.getNome());
        lblRodape.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblRodape.setForeground(corSubtexto);
        rodape.add(lblRodape);

        principal.add(cabecalho,    BorderLayout.NORTH);
        principal.add(sep,          BorderLayout.CENTER);
        principal.add(painelBotoes, BorderLayout.CENTER);
        principal.add(rodape,       BorderLayout.SOUTH);

        add(principal);
    }

    private JButton criarBotaoModulo(String titulo, String subtitulo, Color cor, String icone) {
        JButton btn = new JButton();
        btn.setLayout(new GridLayout(3, 1, 0, 2));
        btn.setBackground(new Color(34, 40, 64));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(14, 16, 14, 16)));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIcone = new JLabel(icone + "  " + titulo);
        lblIcone.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblIcone.setForeground(cor);
        lblIcone.setOpaque(false);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(130, 140, 180));
        lblSub.setOpaque(false);

        JSeparator sep = new JSeparator();
        sep.setForeground(cor.darker());

        btn.add(lblIcone);
        btn.add(sep);
        btn.add(lblSub);

        // Hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(42, 50, 80));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(34, 40, 64));
            }
        });

        // Ação de cada módulo
        btn.addActionListener(e -> abrirModulo(titulo));

        return btn;
    }

    private void abrirModulo(String titulo) {
        switch (titulo) {
            case "Vendas e Pedidos"   -> new TelaVendas(usuario).setVisible(true);
            case "Estoque"            -> new TelaEstoque(usuario).setVisible(true);
            case "Financeiro"         -> new TelaFinanceiro(usuario).setVisible(true);
            case "Projetos e Tarefas" -> new TelaProjetos(usuario).setVisible(true);
            case "Agenda"             -> new TelaAgenda(usuario).setVisible(true);
            case "Dashboard"          -> new TelaDashboard(usuario).setVisible(true);
        }
    }

    private void confirmarSaida() {
        int r = JOptionPane.showConfirmDialog(this,
                "Deseja realmente sair do sistema?",
                "Confirmar saída", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            SistemaData.usuarioLogado = null;
            new TelaLogin().setVisible(true);
            dispose();
        }
    }
}
