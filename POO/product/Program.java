package POO.product;

public class Program {
    public static void main(String[] args) {
        RelatorioProduto p1 = new RelatorioProduto();
        p1.nome = "Mouse Gamer";
        p1.preco = 159.6;
        p1.quantidade = 25;

        RelatorioProduto p2 = new RelatorioProduto();
        p2.nome = "Teclado";
        p2.preco = 120.0;
        p2.quantidade = 1;

        RelatorioProduto p3 = new RelatorioProduto();
        p3.nome = "Monitor";
        p3.preco = 950.0;
        p3.quantidade = 1;

        p1.exibir();
        p2.exibir();
        p3.exibir();

    }
}
