package herancaPolimorfismoInterfaces.sistemaNotificacao;

public class Push extends Notificacao{
    private String titulo;

    public Push(String destinario, String mensagem, String titulo) {
        super(destinario, mensagem);
        this.titulo = titulo;
    }

    @Override
    void enviar() {
        System.out.printf("Enviando Push para: %s \nTítulo: %s \nConteúdo: %s \n\n", getDestinario(), titulo, getMensagem());
    }
}
