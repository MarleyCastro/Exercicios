package herancaPolimorfismoInterfaces.controleDispositivos;

public class ArCondicionado implements Controlavel{
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
            System.out.println("Ar-condicionado ligado");
            setLigado(true);
        } else {
            System.out.println("Ar-condicionado já está ligado");
        }
    }

    @Override
    public void desligar() {
        if (!getLigado()) {
            System.out.println("Ar-condicionado desligado");
            setLigado(true);
        } else {
            System.out.println("Ar-condicionado já está desligado");
        }
    }
}
