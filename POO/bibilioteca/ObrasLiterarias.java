package POO.bibilioteca;

public class ObrasLiterarias {

    public String titulo;
    public String autor;
    public int paginas;

    public void exibir() {
        System.out.printf("%s de %s com %d páginas", titulo, autor, paginas);
    }

}
