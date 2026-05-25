 import java.util.ArrayList;

public class Historial {

    private ArrayList<Partida> historial;

    public Historial(){

        historial = new ArrayList<>();

    }
    public void agregarPartida(Partida partida){

        historial.add(partida);

    }

    public void mostrarHistorial(){

        for(Partida p : historial){

            System.out.println(p);

        }

    }
    public void ordenarPorMovimientos(){

        for(int i = 0; i < historial.size()-1; i++){

            for(int j = 0; j < historial.size()-1-i; j++){

                if(historial.get(j).getMovimientos() >
                   historial.get(j+1).getMovimientos()){

                    Partida temp = historial.get(j);

                    historial.set(j, historial.get(j+1));

                    historial.set(j+1, temp);

                }

            }

        }

    }
    public Partida buscarPartida(int movimientos){

        int izquierda = 0;
        int derecha = historial.size()-1;

        while(izquierda <= derecha){

            int medio = (izquierda + derecha)/2;

            int movMedio = historial.get(medio).getMovimientos();
            
            if(movMedio == movimientos){

            return historial.get(medio);

        }

        if(movMedio < movimientos){

            izquierda = medio + 1;

        }else{

            derecha = medio - 1;

        }

    }

    return null;

}

}

