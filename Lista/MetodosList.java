package Lista;

import java.util.ArrayList;

public class MetodosList {
    public static void main(String[] args) {

        ArrayList<String> nomeFuncionarios = new ArrayList<>();

        nomeFuncionarios.add("Maria");
        nomeFuncionarios.add("Marley");
        nomeFuncionarios.add("Claudionor");
        nomeFuncionarios.add("Jamile");
        nomeFuncionarios.add("Larissa");

        System.out.println(nomeFuncionarios.get(1));
        System.out.println(nomeFuncionarios.size());
    }
}
