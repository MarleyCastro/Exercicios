package DataHora;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FusoHorarioPaises {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String busca = sc.nextLine();

        ZonedDateTime horario = ZonedDateTime.now(ZoneId.of(busca.trim()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horarioFormatado = horario.format(formatter);

        System.out.println("Horário atual em Tóquio: " + horarioFormatado);
    }
}
