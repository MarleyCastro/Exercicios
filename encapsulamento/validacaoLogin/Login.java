package encapsulamento.validacaoLogin;

import java.util.Scanner;

public class Login {
    public class Usuario {
        private String login;
        private String senha;

        public Usuario(String login, String senha) {
            this.login = login;
            this.senha = senha;
        }

        public boolean validarSenha(String login, String senha) {
            return this.login.equals(login) && this.senha.equals(senha);
        }

        public String getLogin() {
            return this.login;
        }
    }


    public void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Usuario aluno = new Usuario("aluno2025", "escola@123");
            int tentativasRestantes = 3;

            while (tentativasRestantes > 0) {
                System.out.print("Digite o seu usuário: ");
                String usuario = sc.nextLine();

                System.out.print("Digite a sua senha: ");
                String senha = sc.nextLine();

                if (aluno.validarSenha(usuario, senha)) {
                    System.out.println("Login bem-sucedido!");
                    break;
                } else {
                    tentativasRestantes--;
                    if (tentativasRestantes == 0) {
                        System.out.println("Acesso bloqueado. Contate o administrador.");
                    } else {
                        System.out.println("Senha incorreta. Tentativas restantes: " + tentativasRestantes);
                    }
                }
            }
            sc.close();
        }
    }


