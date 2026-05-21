package encapsulamento.gestaoDeFuncionarios;

public class Funcionario {
    private String nome;
    private String cargo;
    private double salario;
    private int controleReajuste = 0;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void exibirInformacoes() {
        System.out.printf("\nFuncionario %s - Cargo: %s - Salário %.2f",
                nome, cargo, salario);
    }

    public void reajustarSalario(double percentual) {
        if (!(controleReajuste >= 1)) {
            salario += salario * (percentual / 100);
            System.out.printf("\nNovo salario de %s é %.2f ", nome, salario);
            controleReajuste++;
        } else {
            System.out.println("Já houve a alteração no reajuste");
        }

    }

}