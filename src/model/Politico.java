package model;

public class Politico {
    private String nombre;
    private int dinero;
    private int edad;

    public Politico() {
        nombre = "";
        dinero = 0;
        edad = 0;
    }

    public Politico(String nombre, int dinero, int edad) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.edad = edad;
    }

    public String getNombre() { return nombre; }
    public int getEdad()      { return edad; }
    public int getDinero()    { return dinero; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDinero(int dinero)    { this.dinero = dinero; }
    public void setEdad(int edad)        { this.edad = edad; }

    @Override
    public String toString() {
        return nombre + " - $" + dinero + " - " + edad + " años";
    }
}
