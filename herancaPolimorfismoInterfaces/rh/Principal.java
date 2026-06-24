package herancaPolimorfismoInterfaces.rh;

public class Principal {
    public static void main(String[] args) {
        Funcionario desenvolvedor = new Desenvolvedor("Marley", 10000, "Desenvolvedor FullStack");
        desenvolvedor.exibirInformacoes();
        desenvolvedor.reajustarSalario(15);
        desenvolvedor.aprovarProjeto("Uso de IA nos códigos Java");

    }
}
