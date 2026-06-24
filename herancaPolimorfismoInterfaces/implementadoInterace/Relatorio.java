package herancaPolimorfismoInterfaces.implementadoInterace;

public class Relatorio implements Imprimivel {
    private String titulo;
    private String conteudo;

    public Relatorio (String titulo, String conteudo) {
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public void imprimir() {
        System.out.println("Título: " + getTitulo());
        System.out.println("Conteúdo: " + getConteudo());
    }
}