package herancaPolimorfismoInterfaces.bibilioteca;

public class Ebook extends Midia{
    private String formato;

    public Ebook(String titulo, int anoDeLacamento, String formato) {
        super(titulo, anoDeLacamento);
        this.formato = formato;
    }

    public void exibirInfo() {
        System.out.println("Código: " + gerarCodigo() + " | Ebook: \"" + getTitulo() + "\" - Formato: " + formato);
    }
}
