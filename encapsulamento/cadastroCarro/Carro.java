package encapsulamento.cadastroCarro;

public class Carro {

    private String modelo;
    private String placa;
    private int ano;

    public Carro(String modelo, String placa, int ano) {
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public int getAno() {
        return ano;
    }

    public static void main(String[] args) {
        Carro polo = new Carro("Polo", "ACB-2055", 2025);
        System.out.println("-".repeat(40));
        System.out.println("Veículo cadastrado: ");
        System.out.println("Modelo: " + polo.getModelo());
        System.out.println("Placa: " + polo.getPlaca());
        System.out.println("Ano: " + polo.getAno());
        System.out.println("-".repeat(40));
    }
}
