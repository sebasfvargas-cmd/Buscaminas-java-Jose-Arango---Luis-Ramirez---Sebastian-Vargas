
import java.util.Random;

public class Tablero {
    private int filas;
    private int columnas;
    private int minasTotal;
    private Celda[][] mapa;

    public Celda[][] getMapa(){
        return this.mapa;
    }

    public void calculadoraDeMinasCerca(){
        for (int i = 0; i<filas; i++){
            for (int j = 0; j<columnas; j++){
                if(mapa [i][j] instanceof CeldaConMina){
                    continue;
                }
                int contador = 0;
                if(i-1 >= 0 && mapa[i-1][j]instanceof CeldaConMina){
                    contador++;
                }
                if(i+1<filas && mapa[i+1][j]instanceof CeldaConMina){
                    contador++;
                }
                if(j-1>=0 && mapa[i][j-1]instanceof CeldaConMina){
                    contador++;
                }
                if(j+1<columnas && mapa[i][j+1]instanceof CeldaConMina){
                    contador++;
                }
                if(i-1>=0 && j-1>=0 && mapa[i-1][j-1]instanceof CeldaConMina){
                    contador++;
                }
                if(i-1>=0 && j+1<columnas && mapa[i-1][j+1]instanceof CeldaConMina){
                    contador++;
                }
                if(i+1<filas && j-1>=0 && mapa[i+1][j-1]instanceof CeldaConMina){
                    contador++;
                }
                if(i+1<filas && j+1<columnas && mapa[i+1][j+1]instanceof CeldaConMina){
                    contador++;
                }
                
                if(contador>0){
                    mapa[i][j] = new CeldaMinaCerca(contador);
                }
            }
        }

    }

    public boolean hayMina(int i, int j){
        return mapa[i][j] instanceof CeldaConMina;
    }

    public void ponerMinas(){
        Random azar = new Random();
        int minasPuestas = 0;
        while(minasPuestas<minasTotal){
            int filaAz = azar.nextInt(filas);
            int ColAz = azar.nextInt(columnas);
            if(!(mapa[filaAz][ColAz] instanceof CeldaConMina)){
                mapa[filaAz][ColAz] = new CeldaConMina();
                minasPuestas++;
            }
        }
    }

    public Tablero(int filas, int columnas, int minasTotal){
        this.filas = filas;
        this.columnas = columnas;
        this.minasTotal = minasTotal;
        this.mapa = new Celda[filas][columnas];

        for(int i = 0; i<filas; i++){
            for(int j = 0; j<columnas; j++){
                this.mapa[i][j] = new CeldaVacia();
            }
        }
    }

    public void imprimirTablero(){
        System.out.print("    ");
        for(int j = 0; j<columnas; j++){
            System.out.print(j + " ");
        }
        System.out.println();
        System.out.print("    ");
        for(int j = 0; j<columnas; j++){
            System.out.print("__");
        }
        System.out.println();
        for(int i = 0; i<filas; i++){
            System.out.print(i+" | ");
        
        for(int j = 0; j<columnas; j++){
            String ind = mapa[i][j].indicador();
            System.out.print(ind + " ");
        }
        System.out.println();
    }
    }
    public void revelarMinas(){

    for(int i = 0; i < filas; i++){

        for(int j = 0; j < columnas; j++){

            if(mapa[i][j] instanceof CeldaConMina){

                mapa[i][j].descubrir();

            }

        }

    }

}

    public boolean descubrirCelda(int i, int j){
        if(i<0||j<0||i>=filas||j>=columnas){
            throw new IllegalArgumentException("Esas coordenadas estan fuera de los límites");
        }
        if(mapa[i][j].estaDescubierta()){
            throw new IllegalStateException("Esta casilla ya ha sido descubierta");
        }

        return descubrirCercanos(i, j);

    }
    private boolean descubrirCercanos(int i, int j){
         if(i<0||j<0||i>=filas||j>=columnas){
            return false;
         }
         if(mapa[i][j].estaDescubierta()||mapa[i][j].estaMarcada()){
            return false;
         }
        mapa[i][j].descubrir();

        if(mapa[i][j] instanceof CeldaConMina){
            return true;
        }
        if(mapa[i][j] instanceof CeldaVacia){
            descubrirCercanos(i-1, j);
            descubrirCercanos(i+1, j);
            descubrirCercanos(i, j-1);
            descubrirCercanos(i, j+1);
            descubrirCercanos(i-1, j-1);
            descubrirCercanos(i-1, j+1);
            descubrirCercanos(i+1, j-1);
            descubrirCercanos(i+1, j+1);

        }
        return false;
    }
    public void marcarCelda(int i, int j){
        if(i<0||j<0||i>=filas||j>=columnas){
            throw new IllegalArgumentException("Esas coordenadas estan fuera de los límites");
        }
        if(mapa[i][j].estaDescubierta()){
            throw new IllegalStateException("No puedes marcar la casilla pues ya ha sido descubierta");
        }
        mapa[i][j].cambiarMarca();
    }
    public boolean comprobarVictoria(){
        int casillasSinDescubrir = 0;
        for(int i = 0; i<filas; i++){
            for(int j = 0; j<columnas; j++){
                if(!mapa[i][j].estaDescubierta()){
                    casillasSinDescubrir++;
                }
            }
        }
        return casillasSinDescubrir == minasTotal;
    }
}
