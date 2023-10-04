import java.io.*;
import java.util.*;

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
    public Celda getCelda(int fila, int columna) {
        return matriz[fila][columna];
    }

    public Tablero(int filas, int columnas) {
        matriz = new Celda[filas][columnas];
    }

    public List<String> getMovimientosAleatorios() {
        return movimientosAleatorios;
    }

    public void agregarSolucion(String mov){
        if (solucion.contains(mov)) {
            solucion.remove(mov);
        }else{
            solucion.add(mov);
        }
    }

    public void agregarSolucionAleatoria(){
        solucion.addAll(movimientosAleatorios);
    }


    public void mostrarSolucion() {
        System.out.println("Pasos para llegar a la solucion: ");
        for (String movimiento : solucion) {
            System.out.println(movimiento);
        }
    }

    public void borrarSolucion(){
        solucion.clear();
    }

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

    public void mostrar() {
        // Códigos ANSI para colores
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

    public void mostrarJunto(Tablero tableroAntes) {
        // Códigos ANSI para colores
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

        // Imprimir la ultima línea separatoria
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

    public void cambiarColor(int fila, int columna) {
        matriz[fila][columna].cambiarColor();
    }

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

    public void flipHorizontal(int fila) {
        for (int j = 0; j < matriz[0].length; j++) {
            matriz[fila][j].cambiarColor();
        }
    }

    public void flipVertical(int columna) {
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][columna].cambiarColor();
        }
    }

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

//    public void deshacer() {
//        if (!historialMovimientos.isEmpty()) {
//            String ultimoMovimiento = historialMovimientos.remove(historialMovimientos.size() - 1);
//
//            String[] partes = ultimoMovimiento.split(",");
//            int fila = Integer.parseInt(partes[0].trim()) - 1;
//            int columna = Integer.parseInt(partes[1].trim()) - 1;
//
//            hacerMovimiento(fila, columna, false);
//        } else {
//            System.out.println("No hay movimientos para deshacer.");
//        }
//    }

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

    public void borrarHistorial(){
        historialMovimientos.clear();
    }
}
