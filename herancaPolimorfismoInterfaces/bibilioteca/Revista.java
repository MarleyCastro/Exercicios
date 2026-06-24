package herancaPolimorfismoInterfaces.bibilioteca;

public class Revista extends Midia{
    private int edicao;

    public Revista(String titulo, int anoDeLacamento, int edicao) {
        super(titulo, anoDeLacamento);
        this.edicao = edicao;
    }

    public void exibirInfo() {
        System.out.println("Código: " + gerarCodigo() + " | Revista: \"" + getTitulo() + "\" - Edição: " + edicao);
    }
}
