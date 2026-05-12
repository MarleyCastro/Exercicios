package POO.calculandoNota;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Aluno a = new Aluno();
        System.out.print("Nome = ");
        a.nome = sc.nextLine();

        System.out.print("Nota 1 = ");
        a.nota1 = sc.nextDouble();

        System.out.print("Nota 2 = ");
        a.nota2 = sc.nextDouble();

        double media =  (a.nota1 + a.nota2) / 2;


        a.exibir();
    }
}
