package sistemagerenciamento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {
    private int    id;
    private String data;
    private String cliente;
    private String produto;
    private int    quantidade;
    private double valorUnitario;
    private String status;  // Pendente | Confirmado | Entregue | Cancelado
    private String usuario;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Venda() {
        this.data = LocalDate.now().format(FMT);
    }

    public Venda(int id, String cliente, String produto, int quantidade,
                 double valorUnitario, String status, String usuario) {
        this();
        this.id           = id;
        this.cliente      = cliente;
        this.produto      = produto;
        this.quantidade   = quantidade;
        this.valorUnitario = valorUnitario;
        this.status       = status;
        this.usuario      = usuario;
    }

    public double getValorTotal() {
        return quantidade * valorUnitario;
    }

    public int getId()                      { return id; }
    public void setId(int id)               { this.id = id; }
    public String getData()                 { return data; }
    public void setData(String data)        { this.data = data; }
    public String getCliente()              { return cliente; }
    public void setCliente(String c)        { this.cliente = c; }
    public String getProduto()              { return produto; }
    public void setProduto(String p)        { this.produto = p; }
    public int getQuantidade()              { return quantidade; }
    public void setQuantidade(int q)        { this.quantidade = q; }
    public double getValorUnitario()        { return valorUnitario; }
    public void setValorUnitario(double v)  { this.valorUnitario = v; }
    public String getStatus()               { return status; }
    public void setStatus(String status)    { this.status = status; }
    public String getUsuario()              { return usuario; }
    public void setUsuario(String u)        { this.usuario = u; }
}
