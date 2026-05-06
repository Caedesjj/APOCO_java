# Apoco

# Proyecto CC1

# Estructura

src/

├── Main.java                           
├── controller

│   └── PoliticoController.java         
├── model/

│   ├── Politico.java                   
│   ├── ListaPoliticos.java             
│   ├── Nodo.java                       
│   ├── DataGenerator.java              
│   ├── Ordenamiento.java               
│   ├── ResultadoOrdenamiento.java      
│   ├── MatrizAuditorio.java            
│   ├── NodoAuditorio.java              
│   ├── FilaAuditorio.java              
│   └── NodoFila.java                   
└── view/
    └── VistaPoliticos.java        
# EJES DEL PROGRAMA

# 1. Generacion de datos (DataGenerator.java)
- Utiliza dos arreglos fijos de nombres y apellidos para construir nombres aleatorios.
- La edad se genera entre 25 y 80 años.
- El dinero (cantidad que robarían) es un entero entre 0 y 999.999.
- Se puede especificar una semilla (seed) para que la generación sea reproducible. 
Por defecto se usa 42L para que al regenerar 10.000 políticos siempre se obtenga el mismo conjunto, 
permitiendo comparar algoritmos en igualdad de condiciones.

# 2. Algoritmos de ordenamiento (Ordenamiento.java)
Cada método recibe un arreglo de Politico[] y un Comparator<Politico>.
No modifica el arreglo original directamente sino que trabaja sobre una copia, 
y al final copia el resultado ordenado al arreglo original. 
Esto permite medir las iteraciones sin interferir con los datos.

# contador de iteraciones:
- BubbleSort: cuenta cada comparación entre dos elementos.
- InsertionSort: cuenta cada comparación en el bucle interno.
- QuickSort y MergeSort usan un arreglo long[] contador pasado 
por referencia para acumular las comparaciones entre elementos 
en cada llamada recursiva.

# 3. El Auditorio (MatrizAuditorio, FilaAuditorio, NodoAuditorio, NodoFila)
El auditorio es una lista enlazada doble de listas enlazadas dobles:
- La lista exterior (NodoAuditorio) contiene cada fila del auditorio.
- Cada fila (FilaAuditorio) es una lista doblemente enlazada de NodoFila, cada uno con un Politico.

# proceso de construcción (método construirAuditorio en el controlador):

1. Ordena todos los políticos por dinero descendente (usando el algoritmo que el usuario elija).

2. Toma los primeros k * m políticos de esa lista ordenada.
 
3. Los inserta uno a uno en las filas:
- Se crea la primera fila vacía.

- Por cada político, se intenta insertar en la fila actual usando insertar() de FilaAuditorio.

- insertar() coloca al político en la posición correcta por edad ascendente dentro de la fila (ordenación por inserción en lista enlazada).

- Cuando la fila actual alcanza m elementos, se crea una nueva fila (si no se ha llegado a k filas).

De esta forma se garantiza que:

- La fila 1 tiene a los m políticos con más dinero (ordenados por edad).
- La fila 2 tiene a los siguientes m políticos con más dinero (ordenados por edad), etc.

# Consultas especiales (métodos en MatrizAuditorio):

- masJoven(): recorre el primer asiento de cada fila (el más joven de cada fila) y se queda con el de menor edad de entre ellos.

- masViejo(): recorre todos los asientos de todas las filas y busca el de mayor edad.

- masRico(): recorre todos los asientos y busca el máximo dinero (aunque por construcción el primer asiento de la fila 1 debería ser el más rico, se recorre todo por seguridad).

- menosRico(): recorre todos los asientos y busca el mínimo dinero.

# 4. La lista principal de políticos (ListaPoliticos.java)

Es una lista doblemente enlazada de Nodo (clase que contiene un Politico). Los métodos clave son:

- toArray(): exporta los datos a un arreglo de Politico[] sin copiar los objetos (solo las referencias). Esto es importante porque los algoritmos de ordenamiento trabajan sobre arreglos de referencias.

- fromArray(): recibe un arreglo ya ordenado y sobrescribe los contenidos de los nodos existentes con las referencias ordenadas. No crea nuevos nodos, preservando así la estructura original de la lista.

# TENER EN CUENTA
En la generacion de un millon de datos tanto bubblesort o insertionsort pueden demorar hasta minutos