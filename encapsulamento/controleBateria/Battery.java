package encapsulamento.controleBateria;

public class Battery {
    private double nivelBateria;

    public void setNivelBateria(double nivelBateria) {
        if (nivelBateria >= 0 && nivelBateria <= 100) {
            this.nivelBateria = nivelBateria;
        }
    }

    public String exibirStatus() {
        if (nivelBateria <= 20) {
            return "Bateria fraca";
        } else if (nivelBateria <= 80) {
            return "Bateria ok";
        } else {
            return "Bateria cheia";
        }
    }

    public static void main(String[] args) {
        Battery b = new Battery();

//        b.setNivelBateria(10); // Bateria fraca
//        b.setNivelBateria(50); // Bateria ok
//        b.setNivelBateria(100); // Bateria cheia

        b.setNivelBateria(40);

        System.out.println(b.exibirStatus());
    }
}
