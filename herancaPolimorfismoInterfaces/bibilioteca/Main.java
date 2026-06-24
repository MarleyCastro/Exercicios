package herancaPolimorfismoInterfaces.bibilioteca;

public class Main {
    public static void main(String[] args) {
        Livro livro = new Livro("O Guia do Mochileiro das Galáxias", 1979, "Douglas Adams");
        Revista revista = new Revista("National Geographic", 2023, 245);
        Ebook ebook = new Ebook("Clean Code", 2008, "PDF");

        livro.exibirInfo();
        revista.exibirInfo();
        ebook.exibirInfo();
    }
}
