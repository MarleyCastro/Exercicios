package encapsulamento.cadastroProdutos;

public class Produtos {
    private String nome;
    private double preco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double valor) {
        if (valor >= 0) {
            this.preco = valor;
        } else {
            System.out.println("Valor Iválido");
        }
    }

    public static void main(String[] args) {
        Produtos p = new Produtos();
        p.setNome("Mouse");
        p.setPreco(-59.99);

        System.out.printf("Produto: %s\nPreço: %.2f\n", p.getNome(), p.getPreco());
    }
}
