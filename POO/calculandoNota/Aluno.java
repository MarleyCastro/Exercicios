package POO.calculandoNota;

public class Aluno {
    public String nome;
    public double nota1;
    public double nota2;
    public double media;

    public void exibir() {
        System.out.println("-".repeat(40));
        System.out.println("Aluno: " + nome);
        System.out.println("Nota 1: " + nota1);
        System.out.println("Nota 2: " + nota2);
        media = (nota1 + nota2) / 2;
        System.out.println("Média: " + media);

        if (media >= 7) {
            System.out.println("- ".repeat(40) + " Aprovado " + " -".repeat(40));
        } else {
            System.out.println("Reprovado");
        }
    }

}
