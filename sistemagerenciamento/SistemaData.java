package sistemagerenciamento;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositório central de dados compartilhado entre todos os JFrames.
 * Em um projeto real, seria substituído por um banco de dados.
 */
public class SistemaData {

    // Usuários cadastrados
    public static final List<Usuario> usuarios = new ArrayList<>();

    // Dados dos departamentos
    public static final List<Venda>   vendas   = new ArrayList<>();
    public static final List<Estoque> estoque  = new ArrayList<>();
    public static final List<Projeto> projetos = new ArrayList<>();
    public static final List<Evento>  eventos  = new ArrayList<>();

    // Contadores de ID
    public static int proximoIdVenda   = 1;
    public static int proximoIdEstoque = 1;
    public static int proximoIdProjeto = 1;
    public static int proximoIdEvento  = 1;

    // Usuário logado na sessão atual
    public static Usuario usuarioLogado = null;

    // Bloco estático: popula dados de exemplo ao iniciar
    static {
        // Usuário padrão
        usuarios.add(new Usuario("Administrador", "admin@empresa.com", "admin123"));

        // Vendas de exemplo
        vendas.add(new Venda(proximoIdVenda++, "João Silva",    "Notebook Dell",     2, 2250.00, "Entregue",   "admin"));
        vendas.add(new Venda(proximoIdVenda++, "Maria Oliveira","Monitor LG",        1, 1200.00, "Confirmado", "admin"));
        vendas.add(new Venda(proximoIdVenda++, "Carlos Souza",  "Teclado Mecânico",  3,  150.00, "Pendente",   "admin"));
        vendas.add(new Venda(proximoIdVenda++, "Ana Lima",      "Headset Logitech",  5,  178.00, "Confirmado", "admin"));

        // Estoque de exemplo
        estoque.add(new Estoque(proximoIdEstoque++, "Notebook Dell",    "Entrada", 10, 3200.00, "admin"));
        estoque.add(new Estoque(proximoIdEstoque++, "Monitor LG",       "Entrada",  8,  900.00, "admin"));
        estoque.add(new Estoque(proximoIdEstoque++, "Notebook Dell",    "Saída",    2, 3200.00, "admin"));
        estoque.add(new Estoque(proximoIdEstoque++, "Teclado Mecânico", "Entrada", 15,  300.00, "admin"));
        estoque.add(new Estoque(proximoIdEstoque++, "Teclado Mecânico", "Saída",    3,  300.00, "admin"));
        estoque.add(new Estoque(proximoIdEstoque++, "Headset Logitech", "Entrada", 12,  500.00, "admin"));

        // Projetos de exemplo
        projetos.add(new Projeto(proximoIdProjeto++, "Migração de Servidor",
                "Migrar todos os servidores para cloud AWS.",
                "Carlos Tech", "15/06/2026", "Alta", "Projeto"));
        projetos.add(new Projeto(proximoIdProjeto++, "Relatório Mensal de Vendas",
                "Compilar relatório de vendas de maio.",
                "Ana Lima", "31/05/2026", "Média", "Tarefa Diária"));
        projetos.get(1).setProgresso(70);

        // Eventos de exemplo
        eventos.add(new Evento(proximoIdEvento++, "Reunião de Diretoria",
                "Revisão dos resultados do trimestre.",
                "28/05/2026", "10:00", "Reunião", "admin"));
        eventos.add(new Evento(proximoIdEvento++, "Festa Junina da Empresa",
                "Festa junina anual — pátio da empresa.",
                "12/06/2026", "18:00", "Festa", "admin"));
        eventos.add(new Evento(proximoIdEvento++, "Prazo Imposto de Renda PJ",
                "Último dia para declaração de IR pessoa jurídica.",
                "31/05/2026", "23:59", "Prazo", "admin"));
    }

    // Busca saldo de um produto no estoque
    public static int getSaldoProduto(String nomeProduto) {
        int saldo = 0;
        for (Estoque mov : estoque) {
            if (mov.getNomeProduto().equalsIgnoreCase(nomeProduto)) {
                saldo += mov.getTipo().equals("Entrada") ? mov.getQuantidade() : -mov.getQuantidade();
            }
        }
        return saldo;
    }

    // Faturamento total de vendas confirmadas/entregues
    public static double getFaturamentoTotal() {
        double total = 0;
        for (Venda v : vendas) {
            if (!v.getStatus().equals("Cancelado")) total += v.getValorTotal();
        }
        return total;
    }
}
