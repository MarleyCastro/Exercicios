package DataHora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VerificandoData {
    public static void main(String[] args) {

        LocalDate dataCadastrada = LocalDate.of(2026, 03, 11);
        LocalDate dataAtual = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String dataCadastradaFormatada = dataCadastrada.format(formatter);
        String dataAtualFormatada = dataAtual.format(formatter);

        if (dataCadastrada.isBefore(dataAtual)) {
            System.out.println("Data do evento: " + dataAtualFormatada);
            System.out.println("Data atual: " + dataCadastradaFormatada);
            System.out.println("O evento já ocorreu.");
        } else {
            System.out.println("O evento já ocorreu!!");
        }
    }
}
