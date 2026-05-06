package controller;

import model.*;
import java.util.Comparator;

public class PoliticoController {

    private ListaPoliticos    lista;
    private MatrizAuditorio   auditorio;
    private ResultadoOrdenamiento ultimoResultado;

    // Comparadores reutilizables
    public static final Comparator<Politico> POR_DINERO_DESC =
            (a, b) -> Integer.compare(b.getDinero(), a.getDinero());

    public static final Comparator<Politico> POR_DINERO_ASC =
            (a, b) -> Integer.compare(a.getDinero(), b.getDinero());

    public static final Comparator<Politico> POR_EDAD_ASC =
            (a, b) -> Integer.compare(a.getEdad(), b.getEdad());

    public static final Comparator<Politico> POR_EDAD_DESC =
            (a, b) -> Integer.compare(b.getEdad(), a.getEdad());

    // ── Constructor ──────────────────────────────────────────────────────────

    public PoliticoController(ListaPoliticos lista) {
        this.lista = lista;
    }

    // ── Generación de datos ──────────────────────────────────────────────────

    public ListaPoliticos getLista() { return lista; }

    public void generarPoliticos(int n, long seed) {
        DataGenerator.generarPoliticosAleatorios(lista, n, seed);
    }

    public void generarPoliticos(int n) {
        DataGenerator.generarPoliticosAleatorios(lista, n);
    }

    /**
     * Descarta la lista actual y genera una nueva con n políticos.
     * Usa semilla fija (42) para reproducibilidad entre comparaciones.
     */
    public void regenerarPoliticos(int n) {
        lista = new ListaPoliticos();
        auditorio = null;
        ultimoResultado = null;
        DataGenerator.generarPoliticosAleatorios(lista, n, 42L);
    }

    // ── Ordenamiento de la lista principal ──────────────────────────────────

    /**
     * Ordena la lista con el algoritmo elegido y el comparador dado.
     * Actualiza la lista internamente y guarda las métricas.
     *
     * @param algoritmo  "bubble" | "insertion" | "quick" | "merge"
     * @param comparador Comparator<Politico> a usar
     * @return ResultadoOrdenamiento con iteraciones y tiempo
     */
    public ResultadoOrdenamiento ordenar(String algoritmo, Comparator<Politico> comparador) {
        Politico[] arr = lista.toArray();
        ResultadoOrdenamiento resultado;

        switch (algoritmo.toLowerCase()) {
            case "bubble":
                resultado = Ordenamiento.bubbleSort(arr, comparador);
                break;
            case "insertion":
                resultado = Ordenamiento.insertionSort(arr, comparador);
                break;
            case "quick":
                resultado = Ordenamiento.quickSort(arr, comparador);
                break;
            case "merge":
                resultado = Ordenamiento.mergeSort(arr, comparador);
                break;
            default:
                throw new IllegalArgumentException("Algoritmo desconocido: " + algoritmo);
        }

        lista.fromArray(arr);
        this.ultimoResultado = resultado;
        return resultado;
    }

    // ── Auditorio ────────────────────────────────────────────────────────────

    /**
     * Construye el auditorio (k filas, m columnas).
     * Primero ordena los políticos por dinero descendente,
     * luego llena el auditorio (cada fila se ordena internamente por edad asc).
     *
     * @param k         número de filas
     * @param m         número de columnas
     * @param algoritmo algoritmo a usar para el ordenamiento previo
     */
    public ResultadoOrdenamiento construirAuditorio(int k, int m, String algoritmo) {
        auditorio = new MatrizAuditorio(k, m);

        // Ordenar por dinero descendente
        ResultadoOrdenamiento res = ordenar(algoritmo, POR_DINERO_DESC);

        // Llenar el auditorio con el arreglo ya ordenado
        Politico[] ordenados = lista.toArray();
        auditorio.llenar(ordenados);

        return res;
    }

    public MatrizAuditorio getAuditorio() { return auditorio; }

    // ── Consultas ─────────────────────────────────────────────────────────────

    public String obtenerListaComoTexto()  { return lista.recorrerLista(); }
    public int obtenerCantidadPoliticos()  { return lista.contarPoliticos(); }
    public ResultadoOrdenamiento getUltimoResultado() { return ultimoResultado; }
}
