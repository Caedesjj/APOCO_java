package model;

/**
 * Contiene las métricas de un algoritmo de ordenamiento:
 *  – iteraciones realizadas
 *  – tiempo de ejecución en milisegundos
 */
public class ResultadoOrdenamiento {

    private final String algoritmo;
    private final long iteraciones;
    private final long tiempoMs;

    public ResultadoOrdenamiento(String algoritmo, long iteraciones, long tiempoMs) {
        this.algoritmo  = algoritmo;
        this.iteraciones = iteraciones;
        this.tiempoMs   = tiempoMs;
    }

    public String getAlgoritmo()   { return algoritmo; }
    public long getIteraciones()   { return iteraciones; }
    public long getTiempoMs()      { return tiempoMs; }

    @Override
    public String toString() {
        return String.format(
            "Algoritmo : %s%n" +
            "Iteraciones: %,d%n" +
            "Tiempo     : %d ms",
            algoritmo, iteraciones, tiempoMs
        );
    }
}
