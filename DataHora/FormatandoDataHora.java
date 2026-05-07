package DataHora;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatandoDataHora {
    public static void main(String[] args) {
        LocalDate dataAtual = LocalDate.now();
        LocalTime horaAtual = LocalTime.now();

        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter horaFormato = DateTimeFormatter.ofPattern("HH:mm");

        String dataFormada = dataAtual.format(dataFormato);
        String horaFormatada = horaAtual.format(horaFormato);

        System.out.println(dataAtual + " " + horaAtual);
        System.out.println(dataFormada + " " + horaFormatada);
    }
}
