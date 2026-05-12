package POO.tarefa;

import java.util.ArrayList;
import java.util.List;

public class Tarefa {
    public String descricao;
    public  boolean concluida;

    public void exibir() {
        if (!concluida) {
            System.out.println("Tarefa: Estudar Java - Status: Pendente ");
        } else {
            System.out.println("Tarefa: Fazer exercícios - Status: Concluída  ");
        }
    }

    public static void main(String[] args) {

        Tarefa t1 = new Tarefa();
        t1.descricao = "Estudar Java";
        t1.concluida = false;

        Tarefa t2 = new Tarefa();
        t2.descricao = "Fazer exercícios";
        t2.concluida = true;

        List<Tarefa> lista = new ArrayList<>();
        lista.add(t1);
        lista.add(t2);

        for (Tarefa t : lista) {
            t.exibir();
        }
    }
}
