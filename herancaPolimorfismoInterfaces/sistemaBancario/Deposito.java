package herancaPolimorfismoInterfaces.sistemaBancario;

public class Deposito extends OperacaoBancaria implements AcaoBancaria{
    public Deposito(double valor) {
        super(valor);
    }

    @Override
    public void executar() {
        System.out.printf("Depósito de R$ %.2f realizado", getValor());
    }
}
