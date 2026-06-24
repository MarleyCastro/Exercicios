package herancaPolimorfismoInterfaces.sistemadePagamentos;

public class Principal {
    public static void main(String[] args) {
        Pagamento cartao = new CartaoCredito(250);
        Pagamento boleto = new BoletoBancario(500);
        Pagamento pix = new Pix(300);

        cartao.confirmarPagamento();
        boleto.confirmarPagamento();
        pix.confirmarPagamento();
    }
}
