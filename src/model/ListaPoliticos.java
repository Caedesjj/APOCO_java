package model;

public class ListaPoliticos {

    private Nodo cabeza;
    private Nodo cola;

    public ListaPoliticos() {
        this.cabeza = null;
        this.cola   = null;
    }

    // ── Getters / Setters ────────────────────────────────────────────────────
    public Nodo getCabeza()        { return cabeza; }
    public void setCabeza(Nodo n)  { this.cabeza = n; }
    public Nodo getCola()          { return cola; }
    public void setCola(Nodo n)    { this.cola = n; }

    // ── Operaciones básicas ──────────────────────────────────────────────────

    /** Agrega un político al final de la lista. */
    public void agregarPolitico(Politico p) {
        Nodo nodo = new Nodo();
        nodo.setPolitico(p);

        if (cabeza == null) {
            cabeza = nodo;
            cola   = nodo;
        } else {
            cola.setSiguiente(nodo);
            nodo.setAnterior(cola);
            cola = nodo;
        }
    }

    /** Recorre la lista y devuelve cada político como texto. */
    public String recorrerLista() {
        Nodo actual = cabeza;
        if (actual == null) return "Lista vacía";

        StringBuilder sb = new StringBuilder();
        int i = 1;
        while (actual != null) {
            sb.append(i++).append(". ").append(actual.getPolitico().toString()).append('\n');
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }

    /** Cuenta los políticos en la lista. */
    public int contarPoliticos() {
        int cnt = 0;
        Nodo actual = cabeza;
        while (actual != null) { cnt++; actual = actual.getSiguiente(); }
        return cnt;
    }

    // ── Conversión arreglo ↔ lista (para ordenamientos) ─────────────────────

    /**
     * Exporta los políticos de la lista a un arreglo.
     * El arreglo es independiente de los nodos; modificarlo no afecta la lista.
     */
    public Politico[] toArray() {
        int n = contarPoliticos();
        Politico[] arr = new Politico[n];
        Nodo actual = cabeza;
        for (int i = 0; i < n; i++) {
            arr[i] = actual.getPolitico();
            actual  = actual.getSiguiente();
        }
        return arr;
    }

    /**
     * Recarga la lista a partir de un arreglo (ya ordenado).
     * Preserva los nodos existentes para evitar crear objetos innecesarios.
     */
    public void fromArray(Politico[] arr) {
        Nodo actual = cabeza;
        for (Politico p : arr) {
            actual.setPolitico(p);
            actual = actual.getSiguiente();
        }
    }
}
