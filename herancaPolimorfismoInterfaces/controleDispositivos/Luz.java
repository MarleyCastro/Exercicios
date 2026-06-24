package herancaPolimorfismoInterfaces.controleDispositivos;

public class Luz implements Controlavel{
    private boolean ligado;
    private boolean desligado;

    public boolean getLigado() {
        return ligado;
    }

    public boolean getDesligado() {
        return desligado;
    }

    public void setLigado(boolean ligado) {
        this.ligado = ligado;
    }

    public void setDesligado(boolean desligado) {
        this.desligado = desligado;
    }

    @Override
    public void ligar() {
        if (!getLigado()) {
            System.out.println("Luz ligado");
            setLigado(true);
        } else {
            System.out.println("Luz já está ligada");
        }
    }

    @Override
    public void desligar() {
        if (getLigado()) {
            System.out.println("Luz desligada");
            setLigado(true);
        } else {
            System.out.println("Luz já está desligada");
        }
    }
}
