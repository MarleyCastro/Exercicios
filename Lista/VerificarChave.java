package Lista;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VerificarChave {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<Integer, String> clientes = new HashMap<>();

        clientes.put(1, "Maria");
        clientes.put(2, "Marcos");
        clientes.put(3, "Ana");
        clientes.put(4, "Joana");
        clientes.put(5, "Karen");

        int buscaId = sc.nextInt();

        if (clientes.containsKey(buscaId)) {
            System.out.printf("o ID %d e o %s", buscaId, clientes.get(buscaId));
        } else {
            System.out.printf(" Cliente com ID %d não encontrado.", buscaId);
        }
    }
}
