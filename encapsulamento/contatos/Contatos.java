package encapsulamento.contatos;

import java.util.ArrayList;
import java.util.List;

public class Contatos {

    private String nome;
    private String contato;

    public Contatos(String nome, String contato) {
        this.nome = nome;
        this.contato = contato;
    }

    public String getNome() {
        return nome;
    }

    public String getContato() {
        return contato;
    }

    public static void main(String[] args) {
        List<Contatos> contatos = new ArrayList<>();

        contatos.add(new Contatos("João Silva", "(11) 99999-0000"));
        contatos.add(new Contatos("Luana Santos", "(21) 98888-0000"));
        contatos.add(new Contatos("Pedro Oliveira", "(31) 97777-0000"));
        contatos.add(new Contatos("Mariana Costa", "(41) 96666-0000"));
        contatos.add(new Contatos("Carlos Pereira", "(51) 95555-0000"));
        contatos.add(new Contatos("Fernanda Lima", "(61) 94444-0000"));
        contatos.add(new Contatos("Ricardo Souza", "(71) 93333-0000"));
        contatos.add(new Contatos("Ana Paula", "(81) 92222-0000"));
        contatos.add(new Contatos("Gabriel Martins", "(91) 91111-0000"));
        contatos.add(new Contatos("Beatriz Rocha", "(85) 90000-0000"));

        int i = 0;
        for (Contatos c : contatos) {
            System.out.println(i + ". " + c.getNome() + " - " + c.getContato());
            i++;
        }





    }
}
