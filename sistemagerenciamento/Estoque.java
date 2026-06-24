package sistemagerenciamento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Estoque {
    private int    id;
    private String nomeProduto;
    private String tipo;        // "Entrada" ou "Saída"
    private int    quantidade;
    private double precoUnitario;
    private String data;
    private String usuario;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Estoque() {
        this.data = LocalDate.now().format(FMT);
    }

    public Estoque(int id, String nomeProduto, String tipo,
                   int quantidade, double precoUnitario, String usuario) {
        this();
        this.id            = id;
        this.nomeProduto   = nomeProduto;
        this.tipo          = tipo;
        this.quantidade    = quantidade;
        this.precoUnitario = precoUnitario;
        this.usuario       = usuario;
    }

    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }
    public String getNomeProduto()              { return nomeProduto; }
    public void setNomeProduto(String n)        { this.nomeProduto = n; }
    public String getTipo()                     { return tipo; }
    public void setTipo(String tipo)            { this.tipo = tipo; }
    public int getQuantidade()                  { return quantidade; }
    public void setQuantidade(int q)            { this.quantidade = q; }
    public double getPrecoUnitario()            { return precoUnitario; }
    public void setPrecoUnitario(double p)      { this.precoUnitario = p; }
    public String getData()                     { return data; }
    public void setData(String data)            { this.data = data; }
    public String getUsuario()                  { return usuario; }
    public void setUsuario(String usuario)      { this.usuario = usuario; }
}
