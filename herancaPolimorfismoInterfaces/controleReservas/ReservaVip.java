package herancaPolimorfismoInterfaces.controleReservas;

public class ReservaVip extends Reservas{

    @Override
    public void reservar() {
        System.out.println("Reserva VIP confirmada com atendimento exclusivo");
    }
}
