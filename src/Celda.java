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

    public Celda(String simbolo, char color) {
        this.simbolo = simbolo;
        this.color = color;
    }

    public void cambiarColor() {
        if (color == 'R') {
            color = 'A';
        } else {
            color = 'R';
        }
    }

    @Override
    public String toString() {
        return simbolo + color;
    }
}
