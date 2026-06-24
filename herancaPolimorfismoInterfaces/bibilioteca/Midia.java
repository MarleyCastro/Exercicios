package herancaPolimorfismoInterfaces.bibilioteca;

public class Midia {
    private String titulo;
    private int anoDeLacamento;

    public Midia(String titulo, int anoDeLacamento) {
        this.titulo = titulo;
        this.anoDeLacamento = anoDeLacamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String gerarCodigo() {
        return "LIB-" + titulo.substring(0, 3) + anoDeLacamento;
    }
}
