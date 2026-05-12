package POO.zerandoSaldo;

public class Program {
    public static void main(String[] args) {
        Conta conta = new Conta();
        conta.saldo = 1500;
        conta.exibirSaldo();
        conta.zerarSaldo();
    }
}
