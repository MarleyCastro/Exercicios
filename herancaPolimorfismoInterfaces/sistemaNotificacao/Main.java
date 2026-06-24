package herancaPolimorfismoInterfaces.sistemaNotificacao;

public class Main {
    public static void main(String[] args) {
        Notificacao email = new Email("cliente@exemplo.com",
                "Aproveite nossos descontos esta semana.",
                "Promoção especial!");

        Notificacao sms = new SMS("(11) 98765-4321",
                "Sua fatura foi paga com sucesso.");

        Notificacao push = new Push("usuario_app",
                "Você tem uma nova mensagem não lida.",
                "Novidade!");

        email.enviar();
        sms.enviar();
        push.enviar();
    }
}
