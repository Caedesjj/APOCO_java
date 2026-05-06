package model;

/**
 * Nodo de la lista enlazada exterior del auditorio.
 * Cada nodo contiene una FilaAuditorio y apuntadores
 * al nodo-fila siguiente y anterior.
 */
public class NodoAuditorio {

    private FilaAuditorio fila;
    private NodoAuditorio siguiente;
    private NodoAuditorio anterior;

    public NodoAuditorio(FilaAuditorio fila) {
        this.fila      = fila;
        this.siguiente = null;
        this.anterior  = null;
    }

    public FilaAuditorio getFila()              { return fila; }
    public void setFila(FilaAuditorio f)        { this.fila = f; }

    public NodoAuditorio getSiguiente()         { return siguiente; }
    public void setSiguiente(NodoAuditorio n)   { this.siguiente = n; }

    public NodoAuditorio getAnterior()          { return anterior; }
    public void setAnterior(NodoAuditorio n)    { this.anterior = n; }
}
