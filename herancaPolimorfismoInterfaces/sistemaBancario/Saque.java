package herancaPolimorfismoInterfaces.sistemaBancario;

public class Saque extends  OperacaoBancaria implements AcaoBancaria{

    public Saque(double valor) {
        super(valor);
    }

    @Override
    public void executar() {
        System.out.printf("\nSaque de R$ %.2f realizado.", getValor());
    }
}
