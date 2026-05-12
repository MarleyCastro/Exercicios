package POO.multaAtraso;

public class Multa {

    public String titulo;
    public int diasDeAtraso;

    public double calculo() {
        return diasDeAtraso * 2.50;
    }

    void exibirDetalhes() {
        double multa = calculo();
        System.out.printf("Livro: %s | Multa por %d dias de atraso: R$ %.2f%n",
                titulo, diasDeAtraso, multa);
    }

    public static void main(String[] args) {
        Multa pd = new Multa();

        pd.titulo = "Dom Casmurro";

        pd.diasDeAtraso = 3;
        pd.calculo();
        pd.exibirDetalhes();
    }
}
