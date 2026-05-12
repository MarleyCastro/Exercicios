package POO.corrigindoCadastro;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Funcionario f1 = new Funcionario();
        System.out.print("Nome: ");
        f1.nome = sc.nextLine();

        System.out.print("Cargo atual: ");
        f1.cargo = sc.nextLine();

        System.out.println("Novo cargo: ");
        f1.cargo = sc.nextLine();

        System.out.println("Nível de acesso atual: ");
        f1.nivelDeAcesso = sc.nextLine();

        f1 = new Funcionario(f1.nome, f1.cargo, f1.nivelDeAcesso);

        Funcionario f2 = new Funcionario();
    }
}
