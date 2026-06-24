package herancaPolimorfismoInterfaces.sistemadePagamentos;

public class CartaoCredito extends Pagamento{

    public CartaoCredito(double valor) {
        super(valor);
    }

    @Override
    public void confirmarPagamento() {
        System.out.printf("Pagamento de R$%.2f confirmado no Cartão de Crédito (Taxa: R$%.2f)\n",
                valor, calcularTaxa());
    }

    @Override
    public double calcularTaxa() {
        return 3.0;
    }
}
