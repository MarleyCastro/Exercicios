package POO.zerandoSaldo;

public class Conta {

    public double saldo;

    public void exibirSaldo() {
        System.out.println("Seu saldo atual = R$ " + saldo);
    }
    public void zerarSaldo() {
        saldo = 0;
        System.out.println("Saldo atual = R$ " + saldo);
    }

}
