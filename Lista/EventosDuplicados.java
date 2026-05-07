package Lista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventosDuplicados {
    public static void main(String[] args) {
        List<String> eventosDuplicados = new ArrayList<>();

        eventosDuplicados.add("IA Conference Brasil");
        eventosDuplicados.add("AI Summit");
        eventosDuplicados.add("DevFest");
        eventosDuplicados.add("Cloud Expo");
        eventosDuplicados.add("IA Conference Brasil");
        eventosDuplicados.add("DevFest");

        System.out.println("Eventos Duplicidade = " + eventosDuplicados);

        Set<String> eventoUnico = new HashSet<>(eventosDuplicados);

        System.out.println("Evento Unicos = " + eventoUnico);
    }
}
