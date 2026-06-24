package herancaPolimorfismoInterfaces.controleReservas;

public class Reservas {

    public void reservar() {
        System.out.println("Reservar realizada");
    }

    public void reservar(String dataReserva) {
        System.out.println("Reserva feita para o dia " + dataReserva);
    }

    public void reservar(String dataReserva, int quantPessoas) {
        System.out.println("Reserva feita para o dia " + dataReserva + " para " + quantPessoas + " pessoas");
    }

}
