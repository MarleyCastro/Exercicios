package Lista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaComID {
    public static void main(String[] args) {
        Map<Integer, String> listaProdutos = new HashMap<>();

        listaProdutos.put(1, "Teclado");
        listaProdutos.put(2, "Setup");
        listaProdutos.put(3, "Cadeira");
        listaProdutos.put(4, "Mouse");

        System.out.println(listaProdutos);
    }
}
