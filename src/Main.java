import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Juego juego = new Juego();

        System.out.println("\n" +
                "┏┓  ┓•┏┓•   \n" +
                "┗┓┏┓┃┓╋┃┓┏┓┏\n" +
                "┗┛┗┛┗┗┛┗┗┣┛┛\n" +
                "         ┛  \n");

        System.out.println("Ingrese \'S\' para jugar:");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            System.out.println("Elige una opcion (a, b, c): ");
            System.out.println("a) Tomar datos del archivo \"datos.txt\"");
            System.out.println("b) Usar el tablero predefinido");
            System.out.println("c) Usar un tablero al azar");

            char opcion = scanner.nextLine().charAt(0);

            switch (opcion) {
                case 'a':
                    juego.iniciar('a');
                    break;
                case 'b':
                    juego.iniciar('b');
                    break;
                case 'c':
                    juego.iniciar('c');
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } else {
            System.out.println("Hasta luego!");
        }
    }
}