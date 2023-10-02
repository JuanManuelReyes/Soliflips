import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Juego juego = new Juego();
        boolean juegoResuelto = false;

        System.out.println("¿Deseas jugar Soliflips? (S/N): ");
        String respuesta = scanner.nextLine();

        do {
            System.out.println("\n" +
                    "┏┓  ┓•┏┓•   \n" +
                    "┗┓┏┓┃┓╋┃┓┏┓┏\n" +
                    "┗┛┗┛┗┗┛┗┗┣┛┛\n" +
                    "         ┛  \n");

            System.out.println("Elige una opcion (a, b, c): ");
            System.out.println("a) Tomar datos del archivo \"datos.txt\"");
            System.out.println("b) Usar el tablero predefinido");
            System.out.println("c) Usar un tablero al azar");

            char opcion = scanner.nextLine().charAt(0);

            switch (opcion) {
                case 'a':
                    juegoResuelto = juego.iniciar('a');
                    break;
                case 'b':
                    juegoResuelto = juego.iniciar('b');
                    break;
                case 'c':
                    juegoResuelto = juego.iniciar('c');
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }

            if (juegoResuelto) {
                System.out.println("¿Deseas volver a jugar? (S/N):");
                respuesta = scanner.nextLine();
            } else {
                break;
            }

        } while (respuesta.equalsIgnoreCase("S"));

        System.out.println("Hasta luego!");
    }
}