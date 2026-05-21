package encapsulamento.gestaoDeFuncionarios;

public class Principal {
    public static void main(String[] args) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João");
        funcionario.setCargo("Desenvolvedor");
        funcionario.setSalario(8500);

        System.out.println("Salario: " + funcionario.getSalario());
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Cargo: " + funcionario.getCargo());

        // funcionario.exibirInformacoes();
        // funcionario.exibirInformacoes();

        funcionario.reajustarSalario(5);
        funcionario.reajustarSalario(5);
        funcionario.reajustarSalario(5);
        funcionario.reajustarSalario(5);
    }
}
