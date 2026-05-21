package encapsulamento.senhaAlerada;

public class Usuario {
    private String senha;

    public Usuario(String senha) {
        this.senha = senha;
    }


    public void setSenha(String senha, String senhaAtualizada) {
        if (senha != senhaAtualizada) {
            this.senha = senha;
            System.out.println("Senha alterada com sucesso!");
        } else {
            System.out.println("Senha atual incorreta. A senha não foi alterada.");
        }
    }

    public static void main(String[]args) {
        Usuario u = new Usuario("123456");

        // u.setSenha("123456", "123456"); Senha atual incorreta. A senha não foi alterada.
        u.setSenha("123456", "abc123"); // Senha alterada com sucesso!
    }
}
