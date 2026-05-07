package DataHora;

import java.time.LocalDate;
import java.time.LocalTime;

public class DataHoraAtual {
    public static void main(String[] args) {

        String tarefa = "Enviar relátório semanal";

        LocalDate dataCriada = LocalDate.now();
        LocalTime horaCriacao = LocalTime.now();

        System.out.println(tarefa + "\n");
        System.out.println(dataCriada);
        System.out.println(horaCriacao);

    }
}
