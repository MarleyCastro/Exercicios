package sistemagerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaProjetos extends JFrame {

    private final Usuario usuario;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private JTextField   campoTitulo;
    private JTextField   campoResp;
    private JTextField   campoPrazo;
    private JTextArea    areaDesc;
    private JComboBox<String> comboPrio;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboStatus;

    public TelaProjetos(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Projetos e Tarefas");
        setSize(900, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        carregarTabela();
    }

    private void initComponents() {
        Color bg  = new Color(20, 23, 40);
        Color bg2 = new Color(30, 34, 54);
        Color amber = new Color(245, 158, 11);

        JPanel principal = new JPanel(new BorderLayout(0, 10));
        principal.setBackground(bg);
        principal.setBorder(new EmptyBorder(16, 16, 16, 16));

        // --- TÍTULO ---
        JLabel titulo = new JLabel("📋  Projetos e Tarefas Diárias");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(amber);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        principal.add(titulo, BorderLayout.NORTH);

        // --- FORMULÁRIO ---
        JPanel painelForm = new JPanel(new BorderLayout(0, 8));
        painelForm.setBackground(bg2);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                new EmptyBorder(12, 14, 12, 14)));

        // Linha 1: título, responsável, prazo, prioridade, tipo
        JPanel linha1 = new JPanel(new GridBagLayout());
        linha1.setBackground(bg2);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 5, 3, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        campoTitulo = new JTextField(18);
        campoResp   = new JTextField(12);
        campoPrazo  = new JTextField("dd/MM/yyyy", 10);
        comboPrio   = new JComboBox<>(new String[]{"Alta", "Média", "Baixa"});
        comboTipo   = new JComboBox<>(new String[]{"Projeto", "Tarefa Diária"});
        comboStatus = new JComboBox<>(new String[]{"Pendente", "Em andamento", "Concluído"});

        String[] labels1 = {"Título", "Responsável", "Prazo (dd/MM/yyyy)", "Prioridade", "Tipo", "Status"};
        JComponent[] campos1 = {campoTitulo, campoResp, campoPrazo, comboPrio, comboTipo, comboStatus};

        for (int i = 0; i < labels1.length; i++) {
            g.gridx = i; g.gridy = 0;
            linha1.add(rotulo(labels1[i]), g);
            g.gridx = i; g.gridy = 1;
            estilizar(campos1[i]);
            linha1.add(campos1[i], g);
        }

        // Linha 2: descrição + botão
        JPanel linha2 = new JPanel(new BorderLayout(8, 0));
        linha2.setBackground(bg2);

        areaDesc = new JTextArea(3, 40);
        areaDesc.setBackground(new Color(20, 23, 40));
        areaDesc.setForeground(new Color(220, 225, 255));
        areaDesc.setCaretColor(Color.WHITE);
        areaDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        areaDesc.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        areaDesc.setLineWrap(true);
        areaDesc.setWrapStyleWord(true);

        JPanel painelDesc = new JPanel(new BorderLayout(0, 3));
        painelDesc.setBackground(bg2);
        painelDesc.add(rotulo("Descrição do projeto / tarefa"), BorderLayout.NORTH);
        painelDesc.add(new JScrollPane(areaDesc), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 1, 0, 8));
        painelBotoes.setBackground(bg2);

        JButton btnSalvar = new JButton("💾  Salvar Projeto");
        estilizarBotao(btnSalvar, amber);
        btnSalvar.addActionListener(e -> salvarProjeto());

        JButton btnLimpar = new JButton("✕  Limpar Campos");
        estilizarBotaoNeutro(btnLimpar);
        btnLimpar.addActionListener(e -> limparCampos());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);

        linha2.add(painelDesc,   BorderLayout.CENTER);
        linha2.add(painelBotoes, BorderLayout.EAST);

        painelForm.add(linha1, BorderLayout.NORTH);
        painelForm.add(linha2, BorderLayout.CENTER);

        // --- TABELA ---
        String[] colunas = {"#", "Tipo", "Título", "Responsável", "Prazo", "Prioridade", "Status", "Progresso", "Criado em"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        estilizarTabela(tabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(new Color(20, 23, 40));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(46, 53, 85)));

        // --- RODAPÉ ---
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rodape.setBackground(bg);
        rodape.setBorder(new EmptyBorder(6, 0, 0, 0));

        JButton btnConcluir = new JButton("✔  Marcar como Concluído");
        estilizarBotao(btnConcluir, new Color(34, 197, 94));
        btnConcluir.addActionListener(e -> marcarConcluido());

        JButton btnProgresso = new JButton("📊  Atualizar Progresso");
        estilizarBotao(btnProgresso, new Color(79, 110, 247));
        btnProgresso.addActionListener(e -> atualizarProgresso());

        JButton btnRemover = new JButton("🗑  Remover");
        estilizarBotaoNeutro(btnRemover);
        btnRemover.setForeground(new Color(239, 68, 68));
        btnRemover.addActionListener(e -> removerProjeto());

        rodape.add(btnConcluir);
        rodape.add(btnProgresso);
        rodape.add(btnRemover);

        // --- MONTAGEM ---
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(bg);
        centro.add(painelForm, BorderLayout.NORTH);
        centro.add(scroll,     BorderLayout.CENTER);
        centro.add(rodape,     BorderLayout.SOUTH);

        principal.add(centro, BorderLayout.CENTER);
        add(principal);
    }

    private void salvarProjeto() {
        String titulo = campoTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o título do projeto.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Projeto p = new Projeto(
                SistemaData.proximoIdProjeto++,
                titulo,
                areaDesc.getText().trim(),
                campoResp.getText().trim(),
                campoPrazo.getText().trim(),
                (String) comboPrio.getSelectedItem(),
                (String) comboTipo.getSelectedItem()
        );
        p.setStatus((String) comboStatus.getSelectedItem());
        SistemaData.projetos.add(p);

        limparCampos();
        carregarTabela();
        JOptionPane.showMessageDialog(this, "Projeto/Tarefa salvo com sucesso!", "Salvo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void marcarConcluido() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { avisoSelecao(); return; }
        int id = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString().replace("#", ""));
        SistemaData.projetos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .ifPresent(p -> { p.setStatus("Concluído"); p.setProgresso(100); });
        carregarTabela();
        JOptionPane.showMessageDialog(this, "Projeto marcado como concluído!", "Concluído", JOptionPane.INFORMATION_MESSAGE);
    }

    private void atualizarProgresso() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { avisoSelecao(); return; }
        int id = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString().replace("#", ""));

        String input = JOptionPane.showInputDialog(this,
                "Digite o progresso (0 a 100):", "Atualizar Progresso", JOptionPane.QUESTION_MESSAGE);
        if (input == null) return;
        try {
            int prog = Integer.parseInt(input.trim());
            if (prog < 0 || prog > 100) throw new NumberFormatException();
            SistemaData.projetos.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .ifPresent(p -> {
                        p.setProgresso(prog);
                        if (prog == 100) p.setStatus("Concluído");
                        else if (prog > 0) p.setStatus("Em andamento");
                    });
            carregarTabela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número entre 0 e 100.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerProjeto() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { avisoSelecao(); return; }
        int id = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString().replace("#", ""));
        String nomeProjeto = modeloTabela.getValueAt(linha, 2).toString();
        int conf = JOptionPane.showConfirmDialog(this,
                "Remover \"" + nomeProjeto + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            SistemaData.projetos.removeIf(p -> p.getId() == id);
            carregarTabela();
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Projeto p : SistemaData.projetos) {
            modeloTabela.addRow(new Object[]{
                "#" + p.getId(),
                p.getTipo(),
                p.getTitulo(),
                p.getResponsavel().isEmpty() ? "—" : p.getResponsavel(),
                p.getPrazo().isEmpty() ? "—" : p.getPrazo(),
                p.getPrioridade(),
                p.getStatus(),
                p.getProgresso() + "%",
                p.getDataCriacao()
            });
        }
    }

    private void limparCampos() {
        campoTitulo.setText(""); campoResp.setText("");
        campoPrazo.setText("dd/MM/yyyy"); areaDesc.setText("");
        comboPrio.setSelectedIndex(0); comboTipo.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);
    }

    private void avisoSelecao() {
        JOptionPane.showMessageDialog(this, "Selecione um projeto na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
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
        btn.setBackground(cor.darker());
        btn.setForeground(cor.brighter());
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 34));
    }

    private void estilizarBotaoNeutro(JButton btn) {
        btn.setBackground(new Color(40, 46, 70));
        btn.setForeground(new Color(160, 170, 210));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createLineBorder(new Color(46, 53, 85)));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 34));
    }

    private void estilizarTabela(JTable t) {
        t.setBackground(new Color(20, 23, 40));
        t.setForeground(new Color(200, 210, 255));
        t.setGridColor(new Color(46, 53, 85));
        t.setSelectionBackground(new Color(245, 158, 11, 60));
        t.setSelectionForeground(Color.WHITE);
        t.setRowHeight(28);
        t.getTableHeader().setBackground(new Color(30, 34, 54));
        t.getTableHeader().setForeground(new Color(130, 140, 180));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
}
