package POO.corrigindoCadastro;

public class Funcionario {

    public String nome;
    public String cargo;
    public String nivelDeAcesso;

    public Funcionario(){};

    public Funcionario(String nome, String cargo, String nivelDeAcesso) {
        this.nome = nome;
        this.cargo = cargo;
        this.nivelDeAcesso = nivelDeAcesso;

        System.out.println("--- Antes da atualização ---");
        System.out.println("Nome: " + nome);
        System.out.println("Cargo: " + cargo);
        System.out.println("Nível de acesso: " + nivelDeAcesso);
    }
}
