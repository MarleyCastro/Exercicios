package herancaPolimorfismoInterfaces.controleDispositivos;

public class Main {
    public static void main(String[]args) {
        Luz luz = new Luz();
        ArCondicionado ar = new ArCondicionado();

        ar.ligar();
        ar.ligar();
        ar.desligar();
        ar.desligar();

        System.out.println("=".repeat(40));

        luz.ligar();
        luz.ligar();
        luz.desligar();
        luz.desligar();
    }
}
