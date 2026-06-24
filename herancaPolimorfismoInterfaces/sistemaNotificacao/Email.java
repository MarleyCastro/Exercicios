package herancaPolimorfismoInterfaces.sistemaNotificacao;

public class Email extends Notificacao{
    private String assunto;

    public Email(String destinario, String mensagem, String assunto) {
        super(destinario, mensagem);
        this.assunto = assunto;
    }

    @Override
    void enviar() {
        System.out.printf("Email enviado para %s \nAssunto: %s \nCorpo: %s \n\n",    getDestinario(), assunto, getMensagem());
    }
}
