package model;

public class Nodo {

    private Politico politico;
    private Nodo siguiente;
    private Nodo anterior;

    public Nodo() {
        this.politico  = null;
        this.siguiente = null;
        this.anterior  = null;
    }

    public Politico getPolitico()          { return politico; }
    public void setPolitico(Politico p)    { this.politico = p; }

    public Nodo getSiguiente()             { return siguiente; }
    public void setSiguiente(Nodo n)       { this.siguiente = n; }

    public Nodo getAnterior()              { return anterior; }
    public void setAnterior(Nodo n)        { this.anterior = n; }

    @Override
    public String toString() {
        return "Nodo: politico=" + politico;
    }
}
