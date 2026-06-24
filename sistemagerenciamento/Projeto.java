package sistemagerenciamento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Projeto {
    private int    id;
    private String titulo;
    private String descricao;
    private String responsavel;
    private String prazo;
    private String prioridade;  // Alta | Média | Baixa
    private String tipo;        // Projeto | Tarefa Diária
    private String status;      // Em andamento | Concluído | Pendente
    private int    progresso;   // 0-100
    private String dataCriacao;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Projeto() {
        this.dataCriacao = LocalDate.now().format(FMT);
        this.status      = "Pendente";
        this.progresso   = 0;
    }

    public Projeto(int id, String titulo, String descricao, String responsavel,
                   String prazo, String prioridade, String tipo) {
        this();
        this.id          = id;
        this.titulo      = titulo;
        this.descricao   = descricao;
        this.responsavel = responsavel;
        this.prazo       = prazo;
        this.prioridade  = prioridade;
        this.tipo        = tipo;
        this.status      = "Em andamento";
    }

    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }
    public String getTitulo()                   { return titulo; }
    public void setTitulo(String titulo)        { this.titulo = titulo; }
    public String getDescricao()                { return descricao; }
    public void setDescricao(String d)          { this.descricao = d; }
    public String getResponsavel()              { return responsavel; }
    public void setResponsavel(String r)        { this.responsavel = r; }
    public String getPrazo()                    { return prazo; }
    public void setPrazo(String prazo)          { this.prazo = prazo; }
    public String getPrioridade()               { return prioridade; }
    public void setPrioridade(String p)         { this.prioridade = p; }
    public String getTipo()                     { return tipo; }
    public void setTipo(String tipo)            { this.tipo = tipo; }
    public String getStatus()                   { return status; }
    public void setStatus(String status)        { this.status = status; }
    public int getProgresso()                   { return progresso; }
    public void setProgresso(int progresso)     { this.progresso = progresso; }
    public String getDataCriacao()              { return dataCriacao; }
    public void setDataCriacao(String d)        { this.dataCriacao = d; }
}
