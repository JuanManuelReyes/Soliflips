/*
Autores:
Juan Manuel Reyes | Nro. Estudiante 316445
Facundo Layes | Nro. Estudiante 248464

Repositorio: https://github.com/JuanManuelReyes/Soliflips
 */
package soliflips;

import java.io.*;
import java.util.*;

/**
 * La clase Tablero representa el tablero del juego Soliflips.
 * Gestiona el estado del tablero, los movimientos realizados y la solucion.
 */
public class Tablero {
    private List<String> historialMovimientos = new ArrayList<>();
    private List<String> movimientosAleatorios = new ArrayList<>();
    private List<String> solucion = new ArrayList<>();

    private Celda[][] matriz;
    private static final String[][] TABLERO_PREDEFINIDO = {
            {"|A", "|A", "-R", "/A", "|R", "-R"},
            {"-R", "/A", "/A", "|A", "-R", "-R"},
            {"-R", "-R", "|A", "-R", "/R", "-R"},
            {"\\R", "-R", "|R", "\\R", "|A", "|R"},
            {"\\R", "/R", "/R", "|A", "/A", "\\A"},
    };

    /**
     * Obtiene una celda especifica del tablero.
     *
     * @param fila indice de la fila de la celda.
     * @param columna indice de la columna de la celda.
     * @return La celda en la posicion especificada.
     */
    public Celda getCelda(int fila, int columna) {
        return matriz[fila][columna];
    }

    /**
     * Constructor del tablero con dimensiones especificas.
     *
     * @param filas Numero de filas del tablero.
     * @param columnas Numero de columnas del tablero.
     */
    public Tablero(int filas, int columnas) {
        matriz = new Celda[filas][columnas];
    }

    /**
     * Obtiene la lista de movimientos aleatorios generados.
     *
     * @return Lista de movimientos aleatorios.
     */
    public List<String> getMovimientosAleatorios() {
        return movimientosAleatorios;
    }

    /**
     * Agrega o elimina un movimiento de la solucion.
     *
     * @param mov Movimiento a agregar o eliminar.
     */
    public void agregarSolucion(String mov){
        if (solucion.contains(mov)) {
            solucion.remove(mov);
        }else{
            solucion.add(mov);
        }
    }

    /**
     * Agrega todos los movimientos aleatorios a la solucion.
     */
    public void agregarSolucionAleatoria(){
        solucion.addAll(movimientosAleatorios);
    }

    /**
     * Muestra la solucion actual del tablero.
     */
    public void mostrarSolucion() {
        System.out.println("Pasos para llegar a la solucion: ");
        for (String movimiento : solucion) {
            System.out.println(movimiento);
        }
    }

    /**
     * Borra la solucion actual del tablero.
     */
    public void borrarSolucion(){
        solucion.clear();
    }

    /**
     * Carga el tablero predefinido.
     */
    public void cargarPredefinido() {
        int filas = TABLERO_PREDEFINIDO.length;
        int columnas = TABLERO_PREDEFINIDO[0].length;
        matriz = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                char simbolo = TABLERO_PREDEFINIDO[i][j].charAt(0);
                char color = TABLERO_PREDEFINIDO[i][j].charAt(1);
                matriz[i][j] = new Celda(String.valueOf(simbolo), color);
            }
        }
    }

    /**
     * Carga un tablero desde un archivo especifico.
     *
     * @param nombreArchivo Ruta del archivo desde donde se cargara el tablero.
     */
    public void cargarDesdeArchivo(String nombreArchivo) {
        try {
            Scanner scanner = new Scanner(new File(nombreArchivo));
            int filas = scanner.nextInt();
            int columnas = scanner.nextInt();
            scanner.nextLine();

            matriz = new Celda[filas][columnas];
            for (int i = 0; i < filas; i++) {
                String[] fila = scanner.nextLine().split(" ");
                for (int j = 0; j < columnas; j++) {
                    char simbolo = fila[j].charAt(0);
                    char color = fila[j].charAt(1);
                    matriz[i][j] = new Celda(String.valueOf(simbolo), color);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Genera un tablero aleatorio de dimensiones y dificultad especificos.
     *
     * @param filas Numero de filas del tablero.
     * @param columnas Numero de columnas del tablero.
     * @param nivel Nivel de dificultad del tablero (cantidad de movimientos para solucionarlo).
     */
    public void generarAleatorio(int filas, int columnas, int nivel) {
        movimientosAleatorios.clear();
        matriz = new Celda[filas][columnas];
        Random rand = new Random();
        char color;
        if (rand.nextInt(2)==0) {
            color='R';
        }else{
            color='A';
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                char simbolo = obtenerSimboloAleatorio();
                matriz[i][j] = new Celda(String.valueOf(simbolo), color);
            }
        }


        for (int i = 0; i < nivel; i++) {
            int filaRandom = rand.nextInt(filas);
            int columnaRandom = rand.nextInt(columnas);
            String movimiento = (filaRandom + 1) + " " + (columnaRandom + 1);

            if (!movimientosAleatorios.contains(movimiento)){
                movimientosAleatorios.add(movimiento);
                generarMovimiento(filaRandom, columnaRandom);
            } else {
                i--;
            }

        }

    }

    private char obtenerSimboloAleatorio() {
        Random rand = new Random();
        int random = rand.nextInt(4);
        char[] simbolos = {'/', '\\', '-', '|'};
        char simbolo = simbolos[random];

        return simbolo;
    }

    /**
     * Muestra el tablero actual en consola.
     */
    public void mostrar() {
        // Codigos ANSI para colores
        final String ROJO = "\u001B[31m";
        final String AZUL = "\u001B[34m";
        final String RESET = "\u001B[0m";

        int columnas = matriz[0].length;

        // Imprimir numeros de columnas
        System.out.print("   "); // Espacios para alinear con el numero de fila
        for (int i = 1; i <= columnas; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.println();

        for (int i = 0; i < columnas; i++) {
            if (i == 0) {
                System.out.print("  "); // Espacios solo la primera vez
            }
            System.out.print("+---");
        }
        System.out.println("+");

        for (int i = 0; i < matriz.length; i++) {
            // Imprimir numero de fila
            System.out.print(i + 1 + " ");
            for (int j = 0; j < matriz[i].length; j++) {
                String celda = matriz[i][j].toString();
                char colorChar = celda.charAt(celda.length() - 1);
                String color = (colorChar == 'R') ? ROJO : AZUL;
                System.out.print("| " + color + celda.charAt(0) + RESET + " ");
            }
            System.out.println("|");

            for (int k = 0; k < columnas; k++) {
                if (k == 0) {
                    System.out.print("  "); // Espacios solo la primera vez
                }
                System.out.print("+---");
            }
            System.out.println("+");
        }
    }

    /**
     * Muestra el tablero actual junto a otro tablero especificado.
     *
     * @param tableroAntes Tablero que se mostrara junto al tablero actual.
     */
    public void mostrarJunto(Tablero tableroAntes) {
        // Codigos ANSI para colores
        final String ROJO = "\u001B[31m";
        final String AZUL = "\u001B[34m";
        final String RESET = "\u001B[0m";

        int columnas = matriz[0].length;

        // Imprimir numeros de columnas
        System.out.print("   "); // Espacios para alinear con el numero de fila
        for (int i = 1; i <= columnas; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.print("          ");  // Espacio entre tableros
        for (int i = 1; i <= columnas; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.println();

        for (int i = 0; i < matriz.length; i++) {
            // Imprimir lineas separatorias
            System.out.print("  "); // Espacios para alinear con el numero de fila
            for (int k = 0; k < columnas; k++) {
                System.out.print("+---");
            }
            System.out.print("+  ==>  ");
            System.out.print("  "); // Espacios adicionales para la segunda matriz
            for (int k = 0; k < columnas; k++) {
                System.out.print("+---");
            }
            System.out.println("+");

            // Imprimir numero de fila
            System.out.print(i + 1 + " ");
            for (int j = 0; j < matriz[i].length; j++) {
                String celda = tableroAntes.matriz[i][j].toString();
                char colorChar = celda.charAt(celda.length() - 1);
                String color = (colorChar == 'R') ? ROJO : AZUL;
                System.out.print("| " + color + celda.charAt(0) + RESET + " ");
            }
            System.out.print("|  ==>  " + (i + 1) + " ");
            for (int j = 0; j < matriz[i].length; j++) {
                String celda = matriz[i][j].toString();
                char colorChar = celda.charAt(celda.length() - 1);
                String color = (colorChar == 'R') ? ROJO : AZUL;
                System.out.print("| " + color + celda.charAt(0) + RESET + " ");
            }
            System.out.println("|");
        }

        // Imprimir la ultima linea separatoria
        System.out.print("  "); // Espacios para alinear con el numero de fila
        for (int k = 0; k < columnas; k++) {
            System.out.print("+---");
        }
        System.out.print("+  ==>  ");
        System.out.print("  "); // Espacios adicionales para la segunda matriz
        for (int k = 0; k < columnas; k++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    /**
     * Crea y devuelve una copia del tablero actual.
     *
     * @return Una copia del tablero actual.
     */
    public Tablero clonar() {
        Tablero clon = new Tablero(matriz.length, matriz[0].length);
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                String celdaStr = matriz[i][j].toString();
                char simbolo = celdaStr.charAt(0);
                char color = celdaStr.charAt(1);
                clon.matriz[i][j] = new Celda(String.valueOf(simbolo), color);
            }
        }
        return clon;
    }

    /**
     * Cambia el color de una celda especifica.
     *
     * @param fila indice de la fila de la celda.
     * @param columna indice de la columna de la celda.
     */
    public void cambiarColor(int fila, int columna) {
        matriz[fila][columna].cambiarColor();
    }

    /**
     * Verifica si el tablero actual es una solucion.
     *
     * @return Verdadero si el tablero es una solucion, falso en caso contrario.
     */
    public boolean esSolucion() {
        boolean solucion = true;
        char primerColor = matriz[0][0].toString().charAt(1);
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].toString().charAt(1) != primerColor) {
                    solucion = false;
                }
            }
        }
        return solucion;
    }

    /**
     * Realiza un movimiento en el tablero.
     *
     * @param fila indice de la fila del movimiento.
     * @param columna indice de la columna del movimiento.
     * @param agregar Indica si el movimiento debe ser agregado al historial.
     */
    public void hacerMovimiento(int fila, int columna, boolean agregar) {
        Tablero tableroAntes = this.clonar();
        if (agregar){
            historialMovimientos.add((fila + 1) + " " + (columna + 1));
        }
        String celda = getCelda(fila, columna).toString();
        char simbolo = celda.charAt(0);

        switch (simbolo) {
            case '/':
                flipDiagonalSlash(fila, columna);
                break;
            case '\\':
                flipDiagonalBackslash(fila, columna);
                break;
            case '-':
                flipHorizontal(fila);
                break;
            case '|':
                flipVertical(columna);
                break;
        }
        if (agregar) {
            mostrarJunto(tableroAntes);
        } else {
            mostrar();
        }
    }

    /**
     * Genera un movimiento en el tablero basado en la posicion dada.
     *
     * @param fila indice de la fila del movimiento.
     * @param columna indice de la columna del movimiento.
     */
    public void generarMovimiento(int fila, int columna) {
        Tablero tableroAntes = this.clonar();

        String celda = getCelda(fila, columna).toString();
        char simbolo = celda.charAt(0);

        switch (simbolo) {
            case '/':
                flipDiagonalSlash(fila, columna);
                break;
            case '\\':
                flipDiagonalBackslash(fila, columna);
                break;
            case '-':
                flipHorizontal(fila);
                break;
            case '|':
                flipVertical(columna);
                break;
        }
    }

    /**
     * Cambia el color de las celdas en diagonal desde la posicion dada, en direccion "/".
     *
     * @param fila indice de la fila de inicio.
     * @param columna indice de la columna de inicio.
     */
    public void flipDiagonalSlash(int fila, int columna) {
        int i = fila, j = columna;
        while (i >= 0 && j < matriz[0].length) {
            matriz[i][j].cambiarColor();
            i--;
            j++;
        }
        i = fila + 1;
        j = columna - 1;
        while (i < matriz.length && j >= 0) {
            matriz[i][j].cambiarColor();
            i++;
            j--;
        }
    }

    /**
     * Cambia el color de las celdas en diagonal desde la posicion dada, en direccion "\".
     *
     * @param fila indice de la fila de inicio.
     * @param columna indice de la columna de inicio.
     */
    public void flipDiagonalBackslash(int fila, int columna) {
        int i = fila, j = columna;
        while (i >= 0 && j >= 0) {
            matriz[i][j].cambiarColor();
            i--;
            j--;
        }
        i = fila + 1;
        j = columna + 1;
        while (i < matriz.length && j < matriz[0].length) {
            matriz[i][j].cambiarColor();
            i++;
            j++;
        }
    }

    /**
     * Cambia el color de todas las celdas en la fila dada.
     *
     * @param fila indice de la fila a cambiar.
     */
    public void flipHorizontal(int fila) {
        for (int j = 0; j < matriz[0].length; j++) {
            matriz[fila][j].cambiarColor();
        }
    }

    /**
     * Cambia el color de todas las celdas en la columna dada.
     *
     * @param columna indice de la columna a cambiar.
     */
    public void flipVertical(int columna) {
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][columna].cambiarColor();
        }
    }

    /**
     * Muestra el historial de movimientos realizados en el tablero.
     */
    public void mostrarHistorial() {
       if (historialMovimientos.isEmpty()){
           System.out.println("No se han realizado movimientos.");
       } else {
           System.out.println("Historial de movimientos:");
           for (String movimiento : historialMovimientos) {
               System.out.println(movimiento);
           }
       }
    }

    /**
     * Deshace el ultimo movimiento realizado en el tablero.
     */
    public void deshacer() {
        if (!historialMovimientos.isEmpty()) {
            int fila = Character.getNumericValue(historialMovimientos.get(historialMovimientos.size() - 1).charAt(0) - 1);
            int columna = Character.getNumericValue(historialMovimientos.get(historialMovimientos.size() - 1).charAt(2) - 1);
            hacerMovimiento(fila, columna, false);
            agregarSolucion((fila+1) + " " + (columna+1));
            historialMovimientos.remove(historialMovimientos.size()-1);
        }else{
            System.out.println("No se han realizado movimientos.");
        }
    }

    /**
     * Borra el historial de movimientos del tablero.
     */
    public void borrarHistorial(){
        historialMovimientos.clear();
    }


    /**
     * Obtiene el número de filas del tablero.
     *
     * @return El número de filas del tablero.
     */
    public int obtenerFilas() {
        int filas = matriz.length;
        return filas;
    }

    /**
     * Obtiene el número de columnas del tablero.
     *
     * @return El número de columnas del tablero.
     */
    public int obtenerColumnas() {
        int columnas = 0;
        if (matriz.length > 0) {
            columnas =  matriz[0].length;
        }
        return columnas;
    }
}
