package herancaPolimorfismoInterfaces.sistemaBancario;

public abstract class OperacaoBancaria implements  AcaoBancaria {
    private double valor;

    public OperacaoBancaria (double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    class Saque extends OperacaoBancaria {
        public Saque(double valor) {
            super(valor);
        }

        @Override
        public void executar() {
            System.out.printf("Saque de R$%.2f realizado%n", valor);
        }
    }

}
