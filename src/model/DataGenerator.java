package model;

import java.util.Random;

public class DataGenerator {

    private static final String[] NOMBRES = {
        "Juan","María","Pedro","Ana","Carlos","Lucía","Luis","Sofía","Diego","Isabel",
        "Miguel","Laura","José","Camila","Andrés","Valentina","Fernando","Paula"
    };

    private static final String[] APELLIDOS = {
        "Gómez","Pérez","Rodríguez","López","Sánchez","Ramírez","García","Díaz","Torres","Vargas"
    };

    /** Genera n políticos con datos aleatorios y los agrega a la lista. */
    public static void generarPoliticosAleatorios(ListaPoliticos lista, int n, long seed) {
        Random rnd = (seed >= 0) ? new Random(seed) : new Random();
        for (int i = 0; i < n; i++) {
            Politico p = new Politico();
            String nombre = NOMBRES[rnd.nextInt(NOMBRES.length)]
                          + " " + APELLIDOS[rnd.nextInt(APELLIDOS.length)];
            int edad   = 25 + rnd.nextInt(56);       // 25..80
            int dinero = rnd.nextInt(1_000_000);      // 0..999999

            p.setNombre(nombre);
            p.setEdad(edad);
            p.setDinero(dinero);
            lista.agregarPolitico(p);
        }
    }

    /** Semilla aleatoria por defecto. */
    public static void generarPoliticosAleatorios(ListaPoliticos lista, int n) {
        generarPoliticosAleatorios(lista, n, -1);
    }
}
