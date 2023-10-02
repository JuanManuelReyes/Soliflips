import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Juego {
    private Tablero tablero;
    private long tiempoInicio;
    private List<String> solucion = new ArrayList<>();

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
                boolean datosValidos = false;
                while (!datosValidos) {
                    try {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Ingresa la cantidad de filas:");
                        int filas = scanner.nextInt();
                        if (filas < 3 || filas > 9) {
                            throw new IllegalArgumentException("El número de filas debe estar entre 3 y 9.");
                        }
                        System.out.println("Ingresa la cantidad de columnas:");
                        int columnas = scanner.nextInt();
                        if (columnas < 3 || columnas > 9) {
                            throw new IllegalArgumentException("El número de columnas debe estar entre 3 y 9.");
                        }
                        System.out.println("Elige un nivel (1 a 8):");
                        int nivel = scanner.nextInt();
                        if (nivel < 1 || nivel > 8) {
                            throw new IllegalArgumentException("El nivel debe estar entre 1 y 8.");
                        }
                        tablero.generarAleatorio(filas, columnas, nivel);
                        datosValidos = true;
                        cargarSolucion("c");
                        juegoResuelto = jugar();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Por favor, ingresa los datos nuevamente.");
                    }
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

        return juegoResuelto;
    }

    public boolean  jugar() {
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

            if (entrada.equals("X")) {
                System.out.println("Has decidido salir del juego después de " + minutosTranscurridos + " minutos y " + segundosTranscurridos + " segundos.");
                continuarJugando = false;
            } else if (entrada.equals("S")) {
                mostrarSolucion();
            } else if (entrada.equals("H")) {
                tablero.mostrarHistorial();
            }
            if (entrada.equals("-1") || entrada.equals("-1 -1")) {
                tablero.deshacer();
            }

            String[] partes = entrada.split(" ");
            if (partes.length == 2 && !entrada.equals("-1 -1")) {
                try {
                    int fila = Integer.parseInt(partes[0]) - 1; // Se resta 1 para ajustar con el indice
                    int columna = Integer.parseInt(partes[1]) - 1;
                    if (solucion.contains(entrada)){
                        solucion.remove(entrada);
                    } else {
                        solucion.add(entrada);
                    }
                    tablero.hacerMovimiento(fila, columna, true);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no valida. Por favor, ingresa coordenadas validas o 'X' para salir.");
                }
            }

            if (tablero.esSolucion()) {
                System.out.println("Felicidades! Has resuelto el tablero en " + minutosTranscurridos + ":" + segundosTranscurridos + " minuto/s.");
                continuarJugando = false;
                volverJugar = true;
            }
        }

        return volverJugar;
    }

    public void cargarSolucion(String opcion) {
        switch (opcion) {
            case "a":
                String rutaArchivo = "src/datos.txt";
                try {
                    Scanner scanner = new Scanner(new File(rutaArchivo));
                    boolean agregarASolucion = false;

                    while (scanner.hasNextLine()) {
                        String linea = scanner.nextLine().trim();  // Lee linea del archivo y saca espacios al principio y al final

                        if (agregarASolucion) { // Si es true agrega a lista de solu
                            solucion.add(linea);
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
                solucion.add("4 4");
                solucion.add("5 6");
                solucion.add("5 4");
                break;
            case "c":
                solucion.addAll(tablero.getMovimientosAleatorios());
                break;
        }
    }

    public void mostrarSolucion() {
        System.out.println("Pasos para llegar a la solucion: ");
        for (String movimiento : solucion) {
            System.out.println(movimiento);
        }
    }


}
