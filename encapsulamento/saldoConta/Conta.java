package encapsulamento.saldoConta;

public class Conta {
    private String titular;
    private double saldo;

    public Conta(String titular) {
        this.titular = titular;
    }

    public double depositar(double saldo) {
        this.saldo = saldo;
        return saldo;
    }

    public void sacar(double valor) {
        if (saldo < valor) {
            System.out.println("Saldo insuficiente para saque.");
        } else {
            saldo -= valor;
            System.out.println("Transação Efetuada com sucesso!!");
        }
    }

    public void exibirSaldo() {
        System.out.printf("Saldo atual de %s : %.2f", titular, saldo);
    }

    public static void main(String [] args) {
        Conta conta = new Conta("Marley");

        conta.depositar(1500); // Transação Efetuada com sucesso!!
        conta.sacar(15);
        conta.sacar(1500);
        conta.exibirSaldo();
    }
}
