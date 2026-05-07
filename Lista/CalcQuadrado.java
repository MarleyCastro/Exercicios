package Lista;

import java.util.*;
import java.util.stream.Collectors;

public class CalcQuadrado {
    public static void main(String[] args) {
        List<Integer> numeros = List.of(2,3,5,7,11);

        List<Integer> quadradoNumero = numeros.stream()
                                      .map(i -> i * i)
                                        .collect(Collectors.toList());


        System.out.println(quadradoNumero);
    }
}
