package herancaPolimorfismoInterfaces.escola;

public class Docente extends Pessoa{
    private String diciplina;

    public Docente(String nome, int idade, String diciplina) {
        super(nome, idade);
        this.diciplina = diciplina;
    }

    public void exibirDados() {
        System.out.printf("Docente: %s - Idade: %d - Disciplina: %s\n", getNome(), getIdade(), diciplina);
    }
}
