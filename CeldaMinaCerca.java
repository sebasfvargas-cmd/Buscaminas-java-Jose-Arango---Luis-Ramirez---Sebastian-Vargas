public class CeldaMinaCerca extends Celda {
    private int minasCerca;

    public CeldaMinaCerca(int minasCerca){
        this.minasCerca = minasCerca;
    }
    public int getMinasCerca(){
        return minasCerca;
    }
    @Override
    public String indicador(){
        if(marcada){
            return "|>";
        }
        if(!descubierta){
            return "-";
        }
        return String.valueOf(minasCerca);
    }
}


