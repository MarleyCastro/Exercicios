package herancaPolimorfismoInterfaces.controleReservas;

public class Main {
    public static void main(String [] args){
        Reservas r = new Reservas();
        r.reservar();
        r.reservar("10/05");
        r.reservar("15/08", 7);

        Reservas vip = new ReservaVip();
        vip.reservar();
    }
}
