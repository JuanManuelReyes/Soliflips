/*
Autores:
Juan Manuel Reyes | Nro. Estudiante 316445
Facundo Layes | Nro. Estudiante 248464

Repositorio: https://github.com/JuanManuelReyes/Soliflips
 */
package oblogatorio.soliflips;

import java.io.*;
import java.util.*;


/**
 * La clase Juego representa la logica principal del juego Soliflips.
 * Gestiona el flujo del juego, las interacciones del usuario y el estado del juego.
 */
public class Juego {
    private Tablero tablero;
    private long tiempoInicio;

    /**
     * Constructor por defecto para la clase Juego.
     * Inicializa un nuevo juego con un tablero de tamaño 0x0.
     */
    public Juego() {
        tablero = new Tablero(0, 0);
    }

    /**
     * Inicia un nuevo juego basado en el tipo de juego seleccionado en main meni.
     *
     * @param tipoJuego Caracter que indica el tipo de juego: 'a' para cargar desde archivo, 'b' para tablero predefinido, 'c' para tablero aleatorio.
     * @return Verdadero si el juego fue resuelto, falso en caso contrario.
     */
    public boolean iniciar(char tipoJuego) {
        boolean juegoResuelto = false;

        switch (tipoJuego) {
            case 'a':
                tablero.cargarDesdeArchivo("src/main/java/test/datos.txt");
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
                System.out.println("Opcion no valida.");
                break;
        }

        return juegoResuelto;
    }

    /**
     * Solicita un dato numérico al usuario dentro de un rango específico.
     *
     * @param mensaje Mensaje mostrado al usuario para indicar qué dato debe ingresar.
     * @param desde Valor mínimo que el usuario puede ingresar.
     * @param hasta Valor maximo que el usuario puede ingresar.
     * @return El valor numérico ingresado por el usuario.
     */
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

    /**
     * Permite al usuario jugar interactuando con el tablero.
     *
     * @return Verdadero si el usuario desea volver a jugar después de resolver el tablero, falso en caso contrario.
     */
    public boolean jugar() {
        tiempoInicio = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);
        boolean continuarJugando = true;
        boolean volverJugar = false;

        tablero.mostrar();
        while (continuarJugando) {
            System.out.println("Ingresa tu movimiento (fila) o 'X' para salir, 'S' para ver solucion, 'H' para ver historial:");
            String entradaFila = scanner.nextLine().toUpperCase();

            if (entradaFila.equals("X") || entradaFila.equals("S") || entradaFila.equals("H") || entradaFila.equals("-1") || entradaFila.equals("-1 -1")) {
                if (entradaFila.equals("X")) {
                    System.out.println("Has decidido salir del juego.");
                    continuarJugando = false;
                } else if (entradaFila.equals("S")) {
                    tablero.mostrarSolucion();
                } else if (entradaFila.equals("H")) {
                    tablero.mostrarHistorial();
                } else if (entradaFila.equals("-1") || entradaFila.equals("-1 -1")) {
                    tablero.deshacer();
                }
            } else {
                try {
                    // Obtén las dimensiones del tablero
                    int filas = tablero.obtenerFilas();
                    int columnas = tablero.obtenerColumnas();

                    int fila = Integer.parseInt(entradaFila);
                    System.out.println("Ingresa la columna:");
                    String entradaColumna = scanner.nextLine().toUpperCase();
                    int columna = Integer.parseInt(entradaColumna);

                    if (fila < 1 || fila > filas || columna < 1 || columna > columnas) {
                        System.out.println("Coordenadas fuera de rango, intenta de nuevo.");
                        continue;
                    }

                    tablero.agregarSolucion(fila + " " + columna);

                    fila -= 1;
                    columna -= 1;

                    tablero.hacerMovimiento(fila, columna, true);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no valida. Por favor, ingresa coordenadas validas o 'X' para salir.");
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("Valor fuera de rango. Por favor, ingresa coordenadas validas o 'X' para salir.");
                }
            }

            long tiempoFinal = System.currentTimeMillis();
            long tiempoTranscurrido = tiempoFinal - tiempoInicio;
            long segundosTranscurridos = tiempoTranscurrido / 1000;
            long minutosTranscurridos = segundosTranscurridos / 60;
            segundosTranscurridos %= 60;
            String segundosFormat = String.format("%02d", segundosTranscurridos);

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

    /**
     * Carga la solucion del juego basado en la opcion seleccionada.
     *
     * @param opcion String que indica la opcion de juego: 'a' para cargar desde archivo, 'b' para tablero predefinido, 'c' para tablero aleatorio.
     */
    public void cargarSolucion(String opcion) {
        switch (opcion) {
            case "a":
                String rutaArchivo = "src/main/java/test/datos.txt";
                try {
                    Scanner scanner = new Scanner(new File(rutaArchivo));
                    boolean agregarASolucion = false;

                    while (scanner.hasNextLine()) {
                        String linea = scanner.nextLine().trim();

                        if (agregarASolucion) {
                            tablero.agregarSolucion(linea);
                            continue;
                        }

                        try {
                            int numero = Integer.parseInt(linea);

                            if (numero >= 1 && numero <= 8) {
                                agregarASolucion = true;
                            }
                        } catch (NumberFormatException e) {
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
