package POO.controleEstoque;

import java.util.Scanner;

public class ControleEstoque {

    public String nome;
    public int quantidade;

    public void vender(int quantidade) {
        int sub = this.quantidade -= quantidade;
        if (quantidade >= this.quantidade) {
            System.out.println("Estoque insuficiente");
        } else {
            System.out.println("Venda realizada. Estoque restante de Camiseta: " + sub);
        }
    }


    public static void main(String[] args) {
        ControleEstoque c = new ControleEstoque();
        c.nome = "Camiseta";
        c.quantidade = 10;

        c.vender(11);
    }
}
