package POO.bibilioteca;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ObrasLiterarias livro1 = new ObrasLiterarias();
        livro1.titulo = sc.nextLine();
        livro1.autor = sc.nextLine();
        livro1.paginas = sc.nextInt();

        livro1.exibir();
    }
}
