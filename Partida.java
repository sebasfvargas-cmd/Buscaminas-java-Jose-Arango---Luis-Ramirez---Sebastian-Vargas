public class Partida {

    private String player;
    private boolean ganar;
    private int diff;
    private int mov;

    public Partida(String jugador, boolean victoria, int dificultad, int movimientos){

        this.player = jugador;
        this.ganar = victoria;
        this.diff = dificultad;
        this.mov = movimientos;

    }

    public String getJugador(){
        return player;
    }

    public boolean getVictoria(){
        return ganar;
    }

    public int getDificultad(){
        return diff;
    }

    public int getMovimientos(){
        return mov;
    }

    @Override
    public String toString(){

        return "Jugador: " + player +
               " | Victoria: " + ganar +
               " | Dificultad: " + diff +
               " | Movimientos: " + mov;
    }
}

