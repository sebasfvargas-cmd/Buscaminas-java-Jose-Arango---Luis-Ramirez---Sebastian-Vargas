public class CeldaVacia extends Celda {
@Override
public String indicador(){
    if(marcada){
        return "|>";
    }
    if(!descubierta){
        return "-";
    }
    return " ";
}
}