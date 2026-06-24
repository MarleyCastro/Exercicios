package herancaPolimorfismoInterfaces.enviandoMensagens;

public class Notificador {
    public void enviarMensagem (String mensagem) {
        System.out.println("Mensagem enviada para todos: " + mensagem);
    }

    public void enviarMensagem (String usuario, String mensagem) {
        System.out.println("Mensagem para " + usuario + ": " + mensagem);
    }

    public void enviarMensagem (String usuario, String mensagem, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            System.out.println("Mensagem para " + usuario + ":" + mensagem);
        }
    }
}
