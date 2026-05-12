package POO.product;

public class RelatorioProduto {

    public String nome;
    public double preco;
    public int quantidade;

    public RelatorioProduto(){}

    public void exibir() {
        System.out.println("=".repeat(40));
        System.out.println("nome = " + this.nome);
        System.out.println("preço = " + this.preco);
        System.out.println("Quantidade = " + this.quantidade);
        System.out.println("=".repeat(40));
    }


}
