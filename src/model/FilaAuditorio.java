package model;

/**
 * Una fila del auditorio: lista doblemente enlazada de Politico
 * ordenada de forma ascendente por edad (más joven primero).
 */
public class FilaAuditorio {

    private NodoFila cabeza;
    private NodoFila cola;
    private int      tamanio;
    private final int capacidad;   // columnas (m)

    public FilaAuditorio(int capacidad) {
        this.capacidad = capacidad;
        this.cabeza    = null;
        this.cola      = null;
        this.tamanio   = 0;
    }

    public boolean estaLlena()  { return tamanio >= capacidad; }
    public boolean estaVacia()  { return tamanio == 0; }
    public int getTamanio()     { return tamanio; }
    public int getCapacidad()   { return capacidad; }
    public NodoFila getCabeza() { return cabeza; }

    /**
     * Inserta un político en la posición correcta por edad (ascendente).
     * Retorna false si la fila ya está llena.
     */
    public boolean insertar(Politico p) {
        if (estaLlena()) return false;

        NodoFila nuevo = new NodoFila(p);

        if (cabeza == null) {
            cabeza = nuevo;
            cola   = nuevo;
        } else if (p.getEdad() <= cabeza.getPolitico().getEdad()) {
            // Va al frente
            nuevo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevo);
            cabeza = nuevo;
        } else {
            // Buscar posición correcta
            NodoFila actual = cabeza;
            while (actual.getSiguiente() != null
                   && actual.getSiguiente().getPolitico().getEdad() <= p.getEdad()) {
                actual = actual.getSiguiente();
            }
            NodoFila siguiente = actual.getSiguiente();
            actual.setSiguiente(nuevo);
            nuevo.setAnterior(actual);
            if (siguiente != null) {
                nuevo.setSiguiente(siguiente);
                siguiente.setAnterior(nuevo);
            } else {
                cola = nuevo;
            }
        }
        tamanio++;
        return true;
    }

    /** Devuelve una representación de la fila para visualización. */
    public String toTexto(int numFila) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  Fila %2d: ", numFila));
        NodoFila actual = cabeza;
        int col = 1;
        while (actual != null) {
            Politico p = actual.getPolitico();
            sb.append(String.format("[%d] %-20s $%-8d %d años",
                col, p.getNombre(), p.getDinero(), p.getEdad()));
            actual = actual.getSiguiente();
            if (actual != null) sb.append(" | ");
            col++;
        }
        return sb.toString();
    }
}
