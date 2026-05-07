package Lista;
import java.util.List;

public class CalcNotasMaior {
    public static void main(String[] args) {
        List<Double> notas = List.of(7.5, 8.0, 6.5, 9.0, 10.0);

        // reduce() vai percorrer a lista somando os indices com o método sum
        double somaTotal = notas.stream()
                .reduce(0.0, Double::sum);

        // Retorna a media das notas descritas
        double mediaNotas = somaTotal / 5;

        // O método max(), min() percorre toda a sua lista comparando as notas
        double maiorNota = notas.stream().max(Double::compare).get();
        double menorNota = notas.stream().min(Double::compare).get();

        System.out.println("A média das notas é: " + mediaNotas);
        System.out.println("A menor nota foi: " + menorNota);
        System.out.println("A maior nota foi: " + maiorNota);
    }
}

