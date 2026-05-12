package POO.temperatura;

import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        ControleTemperatura temp = new ControleTemperatura();
        System.out.print("Local = ");
        temp.local = sc.nextLine();

        System.out.print("Temperatura = ");
        String temperaturaAtual = sc.nextLine();
        temperaturaAtual = temperaturaAtual.replace(",",".");
        temp.temperaturaAtual = Double.parseDouble(temperaturaAtual);


        temp.exibir();
        temp.temperaturaMedia();
    }
}
