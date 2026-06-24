package herancaPolimorfismoInterfaces.rh;

public class Desenvolvedor extends Funcionario {
    private String stack;

    public Desenvolvedor(String nome, double salario, String stack) {
        super(nome, salario);
        this.stack = stack;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    @Override
    public void exibirInformacoes() {
        System.out.printf("Desenvolvedor: %s - salário %.2f - bônus: %.2f", nome, salario, stack);
    }

    public void calculaPLR() {
        System.out.println("PLR do Desenvolvedor");
    }
}
