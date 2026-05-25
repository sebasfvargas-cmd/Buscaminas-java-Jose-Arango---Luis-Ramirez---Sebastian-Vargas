public class CeldaConMina extends Celda{
    @Override
    public String indicador(){
        if(estaMarcada()){
            return "|>";
        }
        if(!estaDescubierta()){
            return "-";
        }
        return "*";
    }
}
