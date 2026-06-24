package hashMap;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Inicialização
        Map<Integer, Person> map = new HashMap<>();

        // Inserção de dados
        Person p1 = new Person(1, "João");
        Person p2 = new Person(2, "Pedro");
        map.put(p1.getId(), p1);
        map.put(p2.getId(), p2);

        // Exemplo de acesso (get)
        System.out.println(map.get(1));

        // Exemplo de verificação de chave inexistente
        System.out.println(map.get(-1)); // Retorna null

        // Percorrendo todos os elementos com foreach
        for (Map.Entry<Integer, Person> entry : map.entrySet()) {
            System.out.println("Chave: " + entry.getKey() + " | Valor: " + entry.getValue());
        }
    }
}
