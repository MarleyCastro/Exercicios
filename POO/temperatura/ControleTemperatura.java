package POO.temperatura;

public class ControleTemperatura {

    public String local; // setor de monitoramento
    public double temperaturaAtual;

    public void temperaturaMedia() {
        if (temperaturaAtual > 37.5) {
            System.out.println("Alerta: Temperatura acima do limite!");
        } else {
            System.out.println("Temperatura equivalente");
        }
    }

    public void exibir() {
        System.out.println("Sensor no local: " + local );
        System.out.println("Temperatura: " + temperaturaAtual + " °C");
    }

}
