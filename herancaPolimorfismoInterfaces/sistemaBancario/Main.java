package herancaPolimorfismoInterfaces.sistemaBancario;

public class Main {
    public static void main(String[] args) {
        OperacaoBancaria deposito = new Deposito(500);
        OperacaoBancaria saque = new Saque(500);

        deposito.executar();
        saque.executar();
    }
}
