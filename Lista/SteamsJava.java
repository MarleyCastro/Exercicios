package Lista;
// inportar da bibilioteca util o
import java.util.List;
import java.util.stream.Collectors;

public class SteamsJava {
    public static void main(String[] args) {
        try {
            List<String> funcionarios = List.of("Ana", "  Bruno", "   Carlos", "  Amanda", "Armando", "Amora  ", "  Arco"); // List.of() É não pode adicionar nem alterar os valores dentro desta lista

            List<String> funcionariosLetraA = funcionarios.stream() // .stream() =  Como se fosse a uma esteira que está transportando os dados para outras listas Com a manipulação dos dados
                    .map(f -> f.trim()) // Retirar os espaços Do cadastro Dos nomes
                    .filter(f -> f.trim().startsWith("A") && f.length() >= 4) // Filter igual a pesquisar, String que é o nome da variável f, Então f vai procurar pelas palavras que começam com a letra "a", && E que é maior do que 4 ou mais letras
                    .collect(Collectors.toList());

            System.out.println("Nomes Filtrados com o nome A = " + funcionariosLetraA);

        } catch (UnsupportedOperationException e) {
            System.out.println("Não adcione na lista ");
        }
        
    }
}
