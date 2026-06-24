package sistemagerenciamento;

public class Evento {
    private int    id;
    private String titulo;
    private String descricao;
    private String data;        // dd/MM/yyyy
    private String horario;     // HH:mm
    private String categoria;   // Aviso | Festa | Reunião | Prazo
    private String responsavel;

    public Evento() {}

    public Evento(int id, String titulo, String descricao,
                  String data, String horario, String categoria, String responsavel) {
        this.id          = id;
        this.titulo      = titulo;
        this.descricao   = descricao;
        this.data        = data;
        this.horario     = horario;
        this.categoria   = categoria;
        this.responsavel = responsavel;
    }

    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }
    public String getTitulo()                   { return titulo; }
    public void setTitulo(String titulo)        { this.titulo = titulo; }
    public String getDescricao()                { return descricao; }
    public void setDescricao(String d)          { this.descricao = d; }
    public String getData()                     { return data; }
    public void setData(String data)            { this.data = data; }
    public String getHorario()                  { return horario; }
    public void setHorario(String horario)      { this.horario = horario; }
    public String getCategoria()                { return categoria; }
    public void setCategoria(String cat)        { this.categoria = cat; }
    public String getResponsavel()              { return responsavel; }
    public void setResponsavel(String r)        { this.responsavel = r; }

    @Override
    public String toString() {
        return "[" + categoria + "] " + titulo + " - " + data + " " + horario;
    }
}
