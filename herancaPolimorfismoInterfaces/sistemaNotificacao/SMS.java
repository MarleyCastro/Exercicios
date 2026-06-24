package herancaPolimorfismoInterfaces.sistemaNotificacao;

public class SMS extends Notificacao{
    private String mensagem;

    public SMS(String destinario, String mensagem) {
        super(destinario, mensagem);
        this.mensagem = mensagem;
    }

    @Override
    void enviar() {
        System.out.printf("Enviando SMS para: %s \nMensagem: %s. \n\n", getDestinario(), mensagem);
    }
}
