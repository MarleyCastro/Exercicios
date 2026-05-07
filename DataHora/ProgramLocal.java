package DataHora;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProgramLocal {
    public static void main(String[] args) {

        LocalDate d01 = LocalDate.now();

        LocalDate psat = d01.minusDays(7);
        LocalDate next = d01.plusDays(7);
        LocalDate nextYear = d01.plusYears(1);

        System.out.println("Semana passada = " + psat);
        System.out.println("Semana que vem = " + next);
        System.out.println("Ano que vem = " + nextYear);

        LocalDateTime tempoAtual = LocalDateTime.now();

        LocalDateTime semanaPassada = tempoAtual.minusDays(7);
        LocalDateTime semanaVem = tempoAtual.plusDays(7);

        System.out.println("Semana passada = " + semanaPassada);
        System.out.println("Semana que vem Horas = " + semanaVem);

        Instant d06 = Instant.now();
        Instant pastWeekInstant =  d06.minus(7, ChronoUnit.DAYS);
        Instant plusWeekInstant = d06.plus(7, ChronoUnit.DAYS);

        System.out.println("-".repeat(40));
        System.out.println("pastWeekInstant = " + pastWeekInstant);
        System.out.println("plusWeekInstant = " + plusWeekInstant);

        System.out.println("-".repeat(40));
        Duration t1 = Duration.between(semanaPassada, semanaVem);
        System.out.println("t1 dias = " + t1.toDays());

    }
}
