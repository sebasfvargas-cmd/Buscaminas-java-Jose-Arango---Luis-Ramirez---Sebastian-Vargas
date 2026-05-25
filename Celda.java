public abstract class Celda {
    protected boolean marcada;
    protected boolean descubierta;

    public Celda(){
        this.descubierta = false;
        this.marcada = false;
    }

    public void cambiarMarca(){
        this.marcada = !this.marcada;
    }
    public void descubrir(){
        if(!descubierta){
            this.descubierta = true;
        }
    }
    public boolean estaMarcada(){
        return marcada;
    }
    public boolean estaDescubierta(){
        return descubierta;
    }

    public abstract String indicador();
}
