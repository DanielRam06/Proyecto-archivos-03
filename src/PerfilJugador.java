import java.io.Serializable;

public class PerfilJugador implements Serializable {
	 private static final long serialVersionUID = 1L;
    private String nombre;
    private int juegosJugados;
    private int juegosGanados;
    private int juegosPerdidos;
    private int sumaPuntos;

    public PerfilJugador(String nombre) {
        this.nombre = nombre;
        this.juegosJugados = 0;
        this.juegosGanados = 0;
        this.juegosPerdidos = 0;
        this.sumaPuntos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getJuegosJugados() {
        return juegosJugados;
    }

    public int getJuegosGanados() {
        return juegosGanados;
    }
    public int getJuegosPerdidos() {
        return juegosPerdidos;
    }

    public int getSumaPuntos() {
        return sumaPuntos;
    }

    public void incrementarJuegosJugados() {
        juegosJugados++;
    }

    public void incrementarJuegosGanados() {
        juegosGanados++;
        sumaPuntos += 100;
    }
    public void incrementarJuegosPerdidos() {
        juegosPerdidos++;
    }

    public String toString() {
        return "Nombre: " + nombre + "\nJuegos jugados: " + juegosJugados + "\nJuegos ganados: " + juegosGanados+ "\nJuegos Perdidos: " + juegosPerdidos + "\nSuma de puntos: " + sumaPuntos;
    }

	
	}
