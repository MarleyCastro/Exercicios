package DataHora;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

public class DiferencaHoras {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        LocalTime horaPonto = LocalTime.of(13,45,33);
        LocalTime horaAtual = LocalTime.now();

        Duration diferenca = Duration.between(horaPonto, horaAtual);
        Duration horas = Duration.ofDays(diferenca.toHours());
        Duration minutos = Duration.ofDays(diferenca.toMinutesPart());

        System.out.printf("Diferença de tempo: %s horas e %s minutos", horas, minutos);
    }
}
