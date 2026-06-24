package herancaPolimorfismoInterfaces.sistemaNotificacao;

public class Notificacao {
    private String destinario;
    private String mensagem;

    public Notificacao(String destinario, String mensagem) {
        this.destinario = destinario;
        this.mensagem = mensagem;
    }

    public String getDestinario() {
        return destinario;
    }

    public void setDestinario(String destinario) {
        this.destinario = destinario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    void enviar(){}
}
