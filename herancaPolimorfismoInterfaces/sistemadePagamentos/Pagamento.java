package herancaPolimorfismoInterfaces.sistemadePagamentos;

public class Pagamento {
    protected double valor;

    public Pagamento(double valor) {
        this.valor = valor;
    }

    public void confirmarPagamento() {
        System.out.printf("Pagamento de R$ %.2f confirmando\n", valor, calcularTaxa());
    }

    public double calcularTaxa() {
        return valor * 0.0;
    }
}
