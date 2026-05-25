
import java.util.InputMismatchException;
import java.util.Scanner;

public class Buscaminas{
    private static Historial historial = new Historial();
    private int dificultad;
    private Tablero tablero;
    private int movimientos;
    public Buscaminas(int dificultad, int filas, int columnas, int minasTotales){
        this.movimientos = 0;
        this.dificultad = dificultad;
        if(dificultad ==1){
            this.tablero = new Tablero(8, 8, 12);
        }
        else if(dificultad == 2){
            this.tablero = new Tablero(15, 15, 35);
        }
        else if(dificultad == 3){
            this.tablero = new Tablero(16, 30, 100);
        }else{
            this.tablero = new Tablero(filas, columnas, minasTotales);
        }
        this.tablero.ponerMinas();
        this.tablero.calculadoraDeMinasCerca();
    }
    public boolean turno(int fila, int columna){
        Scanner sc = new Scanner(System.in);
        int decision = 0;
        boolean decisionValida = false;
        if(this.tablero.getMapa()[fila][columna].estaMarcada()){
            while(!decisionValida){
                try {
                    System.out.println("La casilla esta marcada");
                    System.out.println("¿Que vas a hacer?");
                    System.out.println("Desmarcar celda: 2 ");
                    System.out.println("Salir del juego: 3");
                    decision = sc.nextInt();
                    if(decision==2||decision==3){
                        decisionValida = true;
                    }else{
                        System.out.println("Toma una decision válida");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Mientras más te demores en elegir correctamente, más peligro corre tu pueblo...");
                    sc.next();
                }
            }
        }else{
        System.out.println("¿Que vas a hacer?");
        System.out.println("Descubrir: 1");
        System.out.println("Marcar: 2");
        System.out.println("Salir del juego: 3");

        while(!decisionValida){
            try {
                decision = sc.nextInt();
                if(decision>0 && decision<=3){
                    decisionValida = true;
                }else{
                    System.out.println("Toma una decision válida...");
                }
            } catch (InputMismatchException e) {
                System.out.println("Mientras más te demores en elegir correctamente, más peligro corre tu pueblo...");
                sc.next();
            }
         }
        }
        if(decision == 1){
            movimientos++;
            return tablero.descubrirCelda(fila, columna);
        }
        else if(decision == 2){
            movimientos++;
            tablero.marcarCelda(fila, columna);
        }
        else if(decision == 3){
            System.out.println("Un verdadero soldado no abandona a los suyos...");
            System.exit(0);
        }
        return false;
    
    }
    public void iniciarGame(){
        Scanner sc = new Scanner(System.in);
        boolean sigueJugando = true;
        System.out.println("Aquí comienza tu aventura... ");
        System.out.println("Trata de sobrevivir");
        System.out.println("");
        while(sigueJugando){
            System.out.println("Este será tu terreno de juego:");
            this.tablero.imprimirTablero();

            int fila = 0;
            int col = 0;
            boolean movValido = false;
            while(!movValido){
                try {
                    System.out.println("Introduce la coordenada de la fila:");
                    fila = sc.nextInt();
                    System.out.println("Introduce la coordenada de la columna:");
                    col = sc.nextInt();
                    movValido = true;
                } catch (InputMismatchException e) {
                    System.out.println("Coordenada no válida, por favor ingrese un entero correcto");
                    sc.next();
                }
            }
            try {
                boolean exploto = this.turno(fila, col);
                if(exploto){
                    this.tablero.revelarMinas();
                    System.out.println("Misión fallida guerrero, has decepcionado a todos");
                    System.out.println("Escribe tu nombre:");
                    String player = sc.next();
                    Partida partida = new Partida(player, false, dificultad, movimientos);
                    historial.agregarPartida(partida);
                    System.out.println("Todo el territorio de tu pueblo ha explotado...");
                    this.tablero.imprimirTablero();
                    volverAJugar();
                    sigueJugando = false;
                }else if (this.tablero.comprobarVictoria()) {
                    System.out.println("FELICIDADES CAMPEÓN");
                    System.out.println("Escribe tu nombre:");
                    String player = sc.next();
                    Partida partida = new Partida(player, true , dificultad, movimientos);
                    historial.agregarPartida(partida);  
                    System.out.println("Eres el orgullo de tu gente, seras recordado como un heroe");
                    System.out.println("       .---.\r\n" + //
                                                "  ___ /_____\\\r\n" + //
                                                " /\\.-`( '.' )\r\n" + //
                                                "/ /    \\_-_/_\r\n" + //
                                                "\\ `-.-\"`'V'//-.\r\n" + //
                                                " `.__,   |// , \\\r\n" + //
                                                "     |Ll //Ll|\\ \\\r\n" + //
                                                "     |__//   | \\_\\\r\n" + //
                                                "    /---|[]==| / /\r\n" + //
                                                "    \\__/ |   \\/\\/\r\n" + //
                                                "    /_   | Ll_\\|\r\n" + //
                                                "     |`^\"\"\"^`|\r\n" + //
                                                "     |   |   |\r\n" + //
                                                "     |   |   |\r\n" + //
                                                "     |   |   |\r\n" + //
                                                "     |   |   |\r\n" + //
                                                "     L___l___J\r\n" + //
                                                " ggs  |_ | _|\r\n" + //
                                                "     (___|___)\r\n" + //
                                                "      ^^^ ^^^\r\n" + //
                                                "");
                    this.tablero.imprimirTablero();
                    volverAJugar();
                    sigueJugando = false;
                }
            } catch (IllegalStateException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }
    public static void volverAJugar(){

    Scanner sc = new Scanner(System.in);
    System.out.println("¿Quieres volver a jugar?");
    System.out.println("si / no");
    String respuesta = sc.next();
    if(respuesta.trim().equalsIgnoreCase("si")){
        Lobby();
    }else{
        System.out.println("Gracias por jugar");
        System.exit(0);
    }

    }
   public static void Lobby(){
    Scanner sc = new Scanner(System.in);
    System.out.println("████████╗███████╗██████╗░███╗░░░███╗██╗███╗░░██╗░█████╗░██╗░░░░░\n" + //
                "╚══██╔══╝██╔════╝██╔══██╗████╗░████║██║████╗░██║██╔══██╗██║░░░░░\n" + //
                "░░░██║░░░█████╗░░██████╔╝██╔████╔██║██║██╔██╗██║███████║██║░░░░░\n" + //
                "░░░██║░░░██╔══╝░░██╔══██╗██║╚██╔╝██║██║██║╚████║██╔══██║██║░░░░░\n" + //
                "░░░██║░░░███████╗██║░░██║██║░╚═╝░██║██║██║░╚███║██║░░██║███████╗\n" + //
                "░░░╚═╝░░░╚══════╝╚═╝░░╚═╝╚═╝░░░░░╚═╝╚═╝╚═╝░░╚══╝╚═╝░░╚═╝╚══════╝\n" + //
                "\n" + //
                "███╗░░░███╗██╗███╗░░██╗███████╗████████╗░█████╗░██████╗░  ░█████╗░███████╗\n" + //
                "████╗░████║██║████╗░██║██╔════╝╚══██╔══╝██╔══██╗██╔══██╗  ██╔═══╝░╚════██║\n" + //
                "██╔████╔██║██║██╔██╗██║█████╗░░░░░██║░░░██║░░██║██████╔╝  ██████╗░░░░░██╔╝\n" + //
                "██║╚██╔╝██║██║██║╚████║██╔══╝░░░░░██║░░░██║░░██║██╔══██╗  ██╔══██╗░░░██╔╝░\n" + //
                "██║░╚═╝░██║██║██║░╚███║███████╗░░░██║░░░╚█████╔╝██║░░██║  ╚█████╔╝░░██╔╝░░\n" + //
                "╚═╝░░░░░╚═╝╚═╝╚═╝░░╚══╝╚══════╝░░░╚═╝░░░░╚════╝░╚═╝░░╚═╝  ░╚════╝░░░╚═╝░░░");
                System.out.println(" ");
                System.out.println(" ");
    System.out.println("Hola, bienvenido al TerminalMinator 67");
    historial.ordenarPorMovimientos();
    historial.mostrarHistorial();
    System.out.println("Eres un exsoldado de la guerra de Vietnam y descubriste que hay minas ubicadas en el territorio de tu comunidad");
    System.out.println("Tu objetivo es hallar todas las minas sin caer en ellas, mucha suerte!!!");
    boolean InicioValido = false;
    String comienzo=null;
    while(!InicioValido){
    System.out.println("           ¿Que harás?           ");
    System.out.println("Salvar a tu pueblo(iniciar)     ó     Dejar morir a todos(cerrar)     ó Ver herores anteriores (buscar)");
    System.out.println("Indique su eleccion escribiendo alguna de las palabras entre parentesis:");
    comienzo = sc.next();
    if(comienzo.trim().equalsIgnoreCase("cerrar") || comienzo.trim().equalsIgnoreCase("iniciar")|| comienzo.trim().equalsIgnoreCase("buscar")){
        InicioValido = true;
    }else{
        System.out.println("opción no válida");
    }
    }
    if(comienzo.trim().equalsIgnoreCase("cerrar")){
        System.out.println("Esta vez le fallaste a tu gente...");
        System.exit(0);
    }
    if(comienzo.trim().equalsIgnoreCase("buscar")){
        System.out.println("Ingrese la cantidad de movimientos a buscar:");
        int movBuscados = sc.nextInt();
        historial.ordenarPorMovimientos();
        Partida encontrada = historial.buscarPartida(movBuscados);
        if(encontrada != null){
            System.out.println(encontrada);
        }else{
            System.out.println("No se encontró una partida con esa cantidad de movimientos");
        }
        Lobby();
    }
    if(comienzo.trim().equalsIgnoreCase("iniciar")){
        System.out.println("Elige tu dificultad:");
        System.out.println("Fácil: 1");
        System.out.println("Medio: 2");
        System.out.println("Difícil: 3");
        System.out.println("Personalizado: 4");
        boolean diffValida = false;
        int diff = 0;
        while(!diffValida){
            try{
        diff = sc.nextInt();
        if(diff>=1 && diff<=4){
            diffValida = true;
        }else{
            System.out.println("Opcion invalida, por favor seleccione un entero dentro del rango:");
        }
        }catch (InputMismatchException e){
            System.out.println("Por favor intruduce un entero válido, no letras ni decimales");
            sc.next();
        }
        }
        if(diff==4){
            System.out.println("Digite el ancho del mapa");
            System.out.println("El ancho debe tener un valor entero entre 1 a 20:");
            boolean anchValido = false;
            int ancho = 0;
            while(!anchValido){
                try {
                    ancho = sc.nextInt();
                    if(ancho>0&&ancho<=20){
                        anchValido = true;
                    }else{
                        System.out.println("Tamaño de ancho no válido, por favor digite un entero dentro del rango");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Por favor intruduce un entero válido, no letras ni decimales");
                    sc.next();
                }
            }
            System.out.println("Digite el largo del mapa");
            System.out.println("El largo debe tener un valor entero entre 1 a 20:");
            boolean largValido = false;
            int largo = 0;
            while(!largValido){
                try {
                    largo = sc.nextInt();
                    if(largo>0&&largo<=40){
                        largValido = true;
                    }else{
                        System.out.println("Tamaño de largo no válido, por favor digite un entero dentro del rango");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Por favor introduce un entero válido, no letras ni decimales");
                    sc.next();
                }
            }
            System.out.println("Digite la cantidad de minas");
            System.out.println("Las minas totales son la cantidad de celdas totales menos 1");
            boolean minasValidas = false;
            int minasTotales = 0;
            while(!minasValidas){
                try {
                    minasTotales = sc.nextInt();
                    if(minasTotales>0&&minasTotales<=largo*ancho-1){
                        minasValidas = true;
                    }else{
                        System.out.println("Cantidad de minas inválidas, por favor digite una cantidad de minas acordes");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Por favor introduce un entero válido, no letras ni decimales");
                    sc.next();
                }
            }
            Buscaminas game =  new Buscaminas(diff, ancho, largo, minasTotales);
            game.iniciarGame();
        }else{
            Buscaminas game = new Buscaminas(diff, 0, 0, 0);
            game.iniciarGame();
        }
    }
  }
}

