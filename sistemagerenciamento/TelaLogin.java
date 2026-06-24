package sistemagerenciamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JFrame {

    private JTextField  campoEmail;
    private JPasswordField campoSenha;
    private JButton     btnEntrar;
    private JLabel      lblTentativas;
    private JLabel      lblErro;
    private int         tentativas = 0;
    private static final int MAX_TENTATIVAS = 3;

    public TelaLogin() {
        setTitle("Sistema de Gerenciamento — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 380);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(30, 34, 54));

        // === CABEÇALHO ===
        JPanel cabecalho = new JPanel(new GridLayout(2, 1, 0, 4));
        cabecalho.setBackground(new Color(20, 23, 40));
        cabecalho.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("⚙ Sistema de Gerenciamento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(200, 210, 255));

        JLabel lblSub = new JLabel("Faça login para continuar", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(130, 140, 180));

        cabecalho.add(lblTitulo);
        cabecalho.add(lblSub);

        // === FORMULÁRIO ===
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(new Color(30, 34, 54));
        formulario.setBorder(new EmptyBorder(24, 30, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.gridx = 0; gbc.weightx = 1.0;

        // Email
        JLabel lblEmail = criarLabel("Email");
        gbc.gridy = 0;
        formulario.add(lblEmail, gbc);

        campoEmail = new JTextField();
        estilizarCampo(campoEmail);
        gbc.gridy = 1;
        formulario.add(campoEmail, gbc);

        // Senha
        JLabel lblSenha = criarLabel("Senha");
        gbc.gridy = 2;
        formulario.add(lblSenha, gbc);

        campoSenha = new JPasswordField();
        estilizarCampo(campoSenha);
        gbc.gridy = 3;
        formulario.add(campoSenha, gbc);

        // Tentativas
        lblTentativas = new JLabel(" ");
        lblTentativas.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTentativas.setForeground(new Color(245, 158, 11));
        gbc.gridy = 4;
        formulario.add(lblTentativas, gbc);

        // Erro
        lblErro = new JLabel(" ");
        lblErro.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblErro.setForeground(new Color(239, 68, 68));
        gbc.gridy = 5;
        formulario.add(lblErro, gbc);

        // Botão
        btnEntrar = new JButton("Entrar no Sistema");
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEntrar.setBackground(new Color(79, 110, 247));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 0, 0);
        formulario.add(btnEntrar, gbc);

        // === EVENTOS ===
        btnEntrar.addActionListener(e -> autenticar());
        campoSenha.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) autenticar();
            }
        });

        painelPrincipal.add(cabecalho, BorderLayout.NORTH);
        painelPrincipal.add(formulario, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private void autenticar() {
        if (tentativas >= MAX_TENTATIVAS) return;

        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            lblErro.setText("Preencha email e senha.");
            return;
        }

        // Busca usuário na lista
        Usuario encontrado = null;
        for (Usuario u : SistemaData.usuarios) {
            if (u.autenticar(email, senha)) {
                encontrado = u;
                break;
            }
        }

        if (encontrado != null) {
            SistemaData.usuarioLogado = encontrado;
            JOptionPane.showMessageDialog(this,
                    "Bem-vindo, " + encontrado.getNome() + "!",
                    "Login realizado", JOptionPane.INFORMATION_MESSAGE);
            new TelaPrincipal(encontrado).setVisible(true);
            dispose();
        } else {
            tentativas++;
            campoSenha.setText("");

            if (tentativas >= MAX_TENTATIVAS) {
                lblErro.setText("Sistema bloqueado! Número máximo de tentativas atingido.");
                btnEntrar.setEnabled(false);
                JOptionPane.showMessageDialog(this,
                        "ACESSO BLOQUEADO!\nNúmero máximo de tentativas atingido.\nFeche e reabra o sistema.",
                        "Bloqueado", JOptionPane.ERROR_MESSAGE);
            } else {
                lblTentativas.setText("Tentativa " + tentativas + "/" + MAX_TENTATIVAS);
                lblErro.setText("Email ou senha incorretos. Tente novamente.");
            }
        }
    }

    // Helpers visuais
    private JLabel criarLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(new Color(130, 140, 180));
        return l;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setBackground(new Color(20, 23, 40));
        campo.setForeground(new Color(220, 225, 255));
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 53, 85)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        campo.setPreferredSize(new Dimension(0, 36));
    }

    public static void main(String[] args) {
        // Email: admin@empresa.com | Senha: admin123
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
