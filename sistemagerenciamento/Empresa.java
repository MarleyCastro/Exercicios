package sistemagerenciamento;

public class Empresa {
    private int    codigo;
    private String nome;
    private String endereco;
    private String cidade;
    private String uf;
    private String telefone;
    private String cnpj;
    private String contato;
    private String email;

    public Empresa() {}

    public Empresa(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public int getCodigo()                  { return codigo; }
    public void setCodigo(int codigo)       { this.codigo = codigo; }
    public String getNome()                 { return nome; }
    public void setNome(String nome)        { this.nome = nome; }
    public String getEndereco()             { return endereco; }
    public void setEndereco(String e)       { this.endereco = e; }
    public String getCidade()               { return cidade; }
    public void setCidade(String cidade)    { this.cidade = cidade; }
    public String getUf()                   { return uf; }
    public void setUf(String uf)            { this.uf = uf; }
    public String getTelefone()             { return telefone; }
    public void setTelefone(String t)       { this.telefone = t; }
    public String getCnpj()                 { return cnpj; }
    public void setCnpj(String cnpj)        { this.cnpj = cnpj; }
    public String getContato()              { return contato; }
    public void setContato(String contato)  { this.contato = contato; }
    public String getEmail()                { return email; }
    public void setEmail(String email)      { this.email = email; }
}
