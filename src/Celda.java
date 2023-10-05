/*
Autores:
Juan Manuel Reyes | Nro. Estudiante 316445
Facundo Layes | Nro. Estudiante 248464

Repositorio: https://github.com/JuanManuelReyes/Soliflips
 */

/**
 * La clase Celda representa una celda individual en el tablero del juego.
 * Cada celda tiene un símbolo y un color asociado.
 */
public class Celda {
    private String simbolo;
    private char color;

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    /**
     * Constructor para la clase Celda.
     * Crea una nueva instancia de Celda con un símbolo y color específicos.
     *
     * @param simbolo Representa el símbolo de la celda.
     * @param color Representa el color de la celda, puede ser 'R' o 'A', representando rojo y azul.
     */
    public Celda(String simbolo, char color) {
        this.simbolo = simbolo;
        this.color = color;
    }

    /**
     * Cambia el color de la celda.
     * Si el color actual es 'R', lo cambia a 'A' y viceversa.
     */
    public void cambiarColor() {
        if (color == 'R') {
            color = 'A';
        } else {
            color = 'R';
        }
    }

    /**
     * Representacion en cadena de la celda.
     * Devuelve una cadena que combina el símbolo y el color de la celda.
     *
     * @return Una cadena que representa la celda, compuesta por el símbolo seguido del color. Ejemplo: /R.
     */
    @Override
    public String toString() {
        return simbolo + color;
    }
}
