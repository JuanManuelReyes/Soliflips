import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Juego juego = new Juego();
        boolean juegoResuelto = false;
        System.out.println("\n" +
                "┏┓  ┓•┏┓•   \n" +
                "┗┓┏┓┃┓╋┃┓┏┓┏\n" +
                "┗┛┗┛┗┗┛┗┗┣┛┛\n" +
                "         ┛  \n");
        System.out.println("Ingrese \'S\' para empezar a jugar.");
        String respuesta = scanner.nextLine().toUpperCase();

        if (respuesta.equals("S")) {
            boolean opcionValida = false;
            while (!opcionValida){
                System.out.println("Elige una opcion (a, b, c): ");
                System.out.println("a) Tomar datos del archivo \"datos.txt\"");
                System.out.println("b) Usar el tablero predefinido");
                System.out.println("c) Usar un tablero al azar");

                char opcion = scanner.nextLine().charAt(0);

                switch (opcion) {
                    case 'a':
                        juegoResuelto = juego.iniciar('a');
                        opcionValida=true;
                        break;
                    case 'b':
                        juegoResuelto = juego.iniciar('b');
                        opcionValida=true;
                        break;
                    case 'c':
                        juegoResuelto = juego.iniciar('c');
                        opcionValida=true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
                if (juegoResuelto) {
                    System.out.println("Ingrese \'S\' para volver a jugar.");
                    respuesta = scanner.nextLine().toUpperCase();
                    if (respuesta.equals("S")) {
                        opcionValida=false;
                    }else{
                        System.out.println("¡Hasta luego!");
                        opcionValida=true;
                    }
                }
            }
        }else{
            System.out.println("¡Hasta luego!");
        }
    }
}