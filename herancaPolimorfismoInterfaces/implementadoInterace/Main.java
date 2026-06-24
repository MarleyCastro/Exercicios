package herancaPolimorfismoInterfaces.implementadoInterace;

public class Main {
    public static void main(String[]args) {
        Relatorio rel = new Relatorio(
                "DEUS DA MINHA VIDA",
                "Ele faz muito mais do que pensamos ou imaginamos."
        );

        rel.imprimir();
    }
}
