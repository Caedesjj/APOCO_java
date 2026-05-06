package model;

/**
 * Nodo de la lista enlazada interna de una fila del auditorio.
 * Cada nodo contiene un Politico y apuntadores al siguiente y anterior.
 */
public class NodoFila {

    private Politico politico;
    private NodoFila siguiente;
    private NodoFila anterior;

    public NodoFila(Politico p) {
        this.politico  = p;
        this.siguiente = null;
        this.anterior  = null;
    }

    public Politico getPolitico()           { return politico; }
    public void setPolitico(Politico p)     { this.politico = p; }

    public NodoFila getSiguiente()          { return siguiente; }
    public void setSiguiente(NodoFila n)    { this.siguiente = n; }

    public NodoFila getAnterior()           { return anterior; }
    public void setAnterior(NodoFila n)     { this.anterior = n; }
}
