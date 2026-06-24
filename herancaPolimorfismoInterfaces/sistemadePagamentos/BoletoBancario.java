package herancaPolimorfismoInterfaces.sistemadePagamentos;

public class BoletoBancario extends Pagamento{

    public BoletoBancario(double valor) {
        super(valor);
    }

    @Override
    public void confirmarPagamento() {
        System.out.printf("Boleto de R$%.2f gerado com sucesso (Taxa: R$%.2f)\n",
                valor, calcularTaxa());
    }

    @Override
    public double calcularTaxa() {
        return 5.0;
    }
}
