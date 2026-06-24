package herancaPolimorfismoInterfaces.bibilioteca;

public class Livro extends Midia{
    private String autor;

    public Livro(String titulo, int anoDeLacamento, String autor) {
        super(titulo, anoDeLacamento);
        this.autor = autor;
    }

    public void exibirInfo() {
        System.out.println("Código: " + gerarCodigo() + " | Livro: \"" + getTitulo() + "\" - Autor: " + autor);
    }
}
