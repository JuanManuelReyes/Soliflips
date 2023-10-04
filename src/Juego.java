import java.io.*;
import java.util.*;

public class Juego {
    private Tablero tablero;
    private long tiempoInicio;

    public Juego() {
        tablero = new Tablero(0, 0);
    }

    public boolean iniciar(char tipoJuego) {
        boolean juegoResuelto = false;

        switch (tipoJuego) {
            case 'a':
                tablero.cargarDesdeArchivo("./test/datos.txt");
                cargarSolucion("a");
                juegoResuelto = jugar();
                break;
            case 'b':
                tablero.cargarPredefinido();
                cargarSolucion("b");
                juegoResuelto = jugar();
                break;
            case 'c':
                int filas = pedirDato("Ingresa la cantidad de filas (3 a 9): ", 3, 9);
                int columnas = pedirDato("Ingresa la cantidad de columnas (3 a 9): ", 3, 9);
                int nivel = pedirDato("Elige un nivel (1 a 8): ", 1, 8);
                System.out.println();
                tablero.generarAleatorio(filas, columnas, nivel);
                cargarSolucion("c");
                juegoResuelto = jugar();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

        return juegoResuelto;
    }

    public int pedirDato(String mensaje, int desde, int hasta){
        Scanner in = new Scanner(System.in);
        int dato = 0;
        boolean ok = false;
        while (!ok){
            try {
                System.out.print(mensaje);
                dato = in.nextInt();
                in.nextLine();
                if (dato < desde || dato > hasta){
                    System.out.println("Valor fuera de rango.");
                }
                else {
                    ok = true;
                }
            }
            catch(InputMismatchException e){
                System.out.println("Ingrese solo numeros.");
                in.nextLine();
            }
        }
        return dato;
    }
    public boolean jugar() {
        tiempoInicio = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);
        boolean continuarJugando = true;
        boolean volverJugar = false;

        tablero.mostrar(); // Muestra el estado actual del tablero
        while (continuarJugando) {
            System.out.println("Ingresa tu movimiento (fila columna) o 'X' para salir, 'S' para ver solucion, 'H' para ver historial:");
            String entrada = scanner.nextLine().toUpperCase();

            long tiempoFinal = System.currentTimeMillis();
            long tiempoTranscurrido = tiempoFinal - tiempoInicio; // en milisegundos
            long segundosTranscurridos = tiempoTranscurrido / 1000;
            long minutosTranscurridos = segundosTranscurridos / 60;
            segundosTranscurridos %= 60;
            String segundosFormat = String.format("%02d", segundosTranscurridos);

            String[] partes = entrada.split(" ");
            if (partes.length == 2 && !entrada.equals("-1 -1")) {
                try {
                    int fila = Integer.parseInt(partes[0]) - 1; // Se resta 1 para ajustar con el indice
                    int columna = Integer.parseInt(partes[1]) - 1;
                    tablero.agregarSolucion(entrada);
                    tablero.hacerMovimiento(fila, columna, true);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no valida. Por favor, ingresa coordenadas validas o 'X' para salir.");
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("Valor fuera de rango. Por favor, ingresa coordenadas validas o 'X' para salir.");
                }
            }

            if (entrada.equals("X")) {
                System.out.println("Has decidido salir del juego después de " + minutosTranscurridos + " minutos y " + segundosTranscurridos + " segundos.");
                continuarJugando = false;
            } else if (entrada.equals("S")) {
                tablero.mostrarSolucion();
            } else if (entrada.equals("H")) {
                tablero.mostrarHistorial();
            }
            if (entrada.equals("-1") || entrada.equals("-1 -1")) {
                tablero.deshacer();
            }

            if (tablero.esSolucion()) {
                System.out.println("Felicidades! Has resuelto el tablero en " + minutosTranscurridos + ":" + segundosFormat);
                tablero.borrarSolucion();
                tablero.borrarHistorial();
                continuarJugando = false;
                volverJugar = true;
            }
        }

        return volverJugar;
    }

    public void cargarSolucion(String opcion) {
        switch (opcion) {
            case "a":
                String rutaArchivo = "./test/datos.txt";
                try {
                    Scanner scanner = new Scanner(new File(rutaArchivo));
                    boolean agregarASolucion = false;

                    while (scanner.hasNextLine()) {
                        String linea = scanner.nextLine().trim();  // Lee linea del archivo y saca espacios al principio y al final

                        if (agregarASolucion) { // Si es true agrega a lista de solu
                            tablero.agregarSolucion(linea);
                            continue;
                        }

                        try {
                            int numero = Integer.parseInt(linea);

                            if (numero >= 1 && numero <= 8) {
                                agregarASolucion = true; // A partir de ahora vienen las soluciones
                            }
                        } catch (NumberFormatException e) {
                            // La linea no es un número vlido, simplemente continua
                            continue;
                        }
                    }

                    scanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Archivo no encontrado: " + rutaArchivo);
                }
                break;
            case "b":
                tablero.agregarSolucion("4 4");
                tablero.agregarSolucion("5 6");
                tablero.agregarSolucion("5 4");
                break;
            case "c":
                tablero.agregarSolucionAleatoria();
                break;
        }
    }

}
