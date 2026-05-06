package model;

/**
 * Auditorio implementado como lista enlazada de listas enlazadas.
 *
 * Eje externo  → filas (k), avanzando por dinero decreciente
 *                (la fila 1 tiene a quienes más roban).
 * Eje interno  → columnas (m) dentro de cada fila, ordenadas por edad
 *                ascendente (el más joven va primero).
 *
 * Se llena con los primeros k*m políticos del arreglo ya ordenado
 * por dinero descendente.
 */
public class MatrizAuditorio {

    private NodoAuditorio cabeza;   // primera fila
    private NodoAuditorio cola;     // última fila
    private final int k;            // filas
    private final int m;            // columnas
    private int filasUsadas;

    public MatrizAuditorio(int k, int m) {
        this.k          = k;
        this.m          = m;
        this.cabeza     = null;
        this.cola       = null;
        this.filasUsadas = 0;
    }

    public int getK()           { return k; }
    public int getM()           { return m; }
    public NodoAuditorio getCabeza() { return cabeza; }

    /** Agrega una nueva fila vacía al final de la lista exterior. */
    private FilaAuditorio agregarFila() {
        FilaAuditorio fila = new FilaAuditorio(m);
        NodoAuditorio nodo = new NodoAuditorio(fila);
        if (cabeza == null) {
            cabeza = nodo;
            cola   = nodo;
        } else {
            cola.setSiguiente(nodo);
            nodo.setAnterior(cola);
            cola = nodo;
        }
        filasUsadas++;
        return fila;
    }

    /**
     * Llena el auditorio con los políticos del arreglo (ya ordenado
     * por dinero descendente).  Se usan los primeros k*m elementos.
     */
    public void llenar(Politico[] ordenadosPorDinero) {
        // Limpiar estado anterior
        cabeza      = null;
        cola        = null;
        filasUsadas = 0;

        int capacidad = k * m;
        int total     = Math.min(capacidad, ordenadosPorDinero.length);

        FilaAuditorio filaActual = agregarFila();
        for (int i = 0; i < total; i++) {
            if (filaActual.estaLlena()) {
                if (filasUsadas >= k) break;  // auditorio lleno
                filaActual = agregarFila();
            }
            filaActual.insertar(ordenadosPorDinero[i]);
        }
    }

    /**
     * Texto completo del auditorio para mostrar en pantalla.
     */
    public String toTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== AUDITORIO (%d filas × %d columnas) ===%n", k, m));
        sb.append("Ordenado: fila 1 = mayores robadores | dentro de cada fila: por edad ascendente\n\n");

        NodoAuditorio nodoFila = cabeza;
        int numFila = 1;
        while (nodoFila != null) {
            sb.append(nodoFila.getFila().toTexto(numFila)).append('\n');
            nodoFila = nodoFila.getSiguiente();
            numFila++;
        }
        return sb.toString();
    }

    // ── Consultas sobre los ejes ─────────────────────────────────────────────

    /** Político más joven en el auditorio (primer asiento de cada fila). */
    public Politico masJoven() {
        Politico mejor = null;
        NodoAuditorio nF = cabeza;
        while (nF != null) {
            NodoFila prim = nF.getFila().getCabeza();
            if (prim != null) {
                Politico p = prim.getPolitico();
                if (mejor == null || p.getEdad() < mejor.getEdad()) mejor = p;
            }
            nF = nF.getSiguiente();
        }
        return mejor;
    }

    /** Político más viejo en el auditorio (último asiento de cada fila). */
    public Politico masViejo() {
        Politico mejor = null;
        NodoAuditorio nF = cabeza;
        while (nF != null) {
            // recorrer hasta el último de la fila
            NodoFila actual = nF.getFila().getCabeza();
            while (actual != null) {
                Politico p = actual.getPolitico();
                if (mejor == null || p.getEdad() > mejor.getEdad()) mejor = p;
                actual = actual.getSiguiente();
            }
            nF = nF.getSiguiente();
        }
        return mejor;
    }

    /** Político con más dinero (primera fila, primer asiento). */
    public Politico masRico() {
        if (cabeza == null || cabeza.getFila().getCabeza() == null) return null;
        // La fila 1, asiento 1 puede no ser el de más dinero absoluto porque
        // dentro de la fila están ordenados por edad; buscamos el máximo global.
        Politico mejor = null;
        NodoAuditorio nF = cabeza;
        while (nF != null) {
            NodoFila actual = nF.getFila().getCabeza();
            while (actual != null) {
                Politico p = actual.getPolitico();
                if (mejor == null || p.getDinero() > mejor.getDinero()) mejor = p;
                actual = actual.getSiguiente();
            }
            nF = nF.getSiguiente();
        }
        return mejor;
    }

    /** Político con menos dinero en el auditorio. */
    public Politico menosRico() {
        Politico mejor = null;
        NodoAuditorio nF = cabeza;
        while (nF != null) {
            NodoFila actual = nF.getFila().getCabeza();
            while (actual != null) {
                Politico p = actual.getPolitico();
                if (mejor == null || p.getDinero() < mejor.getDinero()) mejor = p;
                actual = actual.getSiguiente();
            }
            nF = nF.getSiguiente();
        }
        return mejor;
    }
}
