package Lista;

import java.util.List;
import java.util.stream.Collectors;

public class Funcionarios {
    public static void main(String[] args) {
        List<String> funcionarios = List.of("Ana", "Bruno", "Carlos", "Amanda", "Alice", "Daniel", "Caroline");

        int tamanhoDesejado = 5;

        List<String> resultado = funcionarios.stream()
                .filter(nomeCinco -> nomeCinco.length() <= tamanhoDesejado)
                .collect(Collectors.toList());

        System.out.println(resultado);
    }
}
