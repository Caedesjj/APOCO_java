package model;

import java.util.Comparator;

/**
 * Implementaciones de BubbleSort, InsertionSort, QuickSort y MergeSort.
 * Cada método recibe un arreglo de Politico[], un Comparator<Politico>
 * y devuelve un ResultadoOrdenamiento con las métricas (iteraciones + tiempo).
 *
 * El arreglo original NO se modifica; se trabaja sobre una copia.
 */
public class Ordenamiento {

    // ── BubbleSort ───────────────────────────────────────────────────────────

    public static ResultadoOrdenamiento bubbleSort(Politico[] datos, Comparator<Politico> cmp) {
        Politico[] arr = datos.clone();
        int n = arr.length;
        long iter = 0;

        long inicio = System.currentTimeMillis();

        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                iter++;
                if (cmp.compare(arr[j], arr[j + 1]) > 0) {
                    Politico tmp = arr[j];
                    arr[j]       = arr[j + 1];
                    arr[j + 1]  = tmp;
                    swapped      = true;
                }
            }
            if (!swapped) break; // optimización: ya ordenado
        }

        long tiempo = System.currentTimeMillis() - inicio;

        // Escribir resultado de vuelta al arreglo original
        System.arraycopy(arr, 0, datos, 0, n);

        return new ResultadoOrdenamiento("BubbleSort", iter, tiempo);
    }

    // ── InsertionSort ────────────────────────────────────────────────────────

    public static ResultadoOrdenamiento insertionSort(Politico[] datos, Comparator<Politico> cmp) {
        Politico[] arr = datos.clone();
        int n = arr.length;
        long iter = 0;

        long inicio = System.currentTimeMillis();

        for (int i = 1; i < n; i++) {
            Politico llave = arr[i];
            int j = i - 1;
            iter++;
            while (j >= 0 && cmp.compare(arr[j], llave) > 0) {
                iter++;
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = llave;
        }

        long tiempo = System.currentTimeMillis() - inicio;
        System.arraycopy(arr, 0, datos, 0, n);

        return new ResultadoOrdenamiento("InsertionSort", iter, tiempo);
    }

    // ── QuickSort ────────────────────────────────────────────────────────────

    public static ResultadoOrdenamiento quickSort(Politico[] datos, Comparator<Politico> cmp) {
        Politico[] arr = datos.clone();
        long[] contador = {0};

        long inicio = System.currentTimeMillis();
        quickSortRec(arr, 0, arr.length - 1, cmp, contador);
        long tiempo = System.currentTimeMillis() - inicio;

        System.arraycopy(arr, 0, datos, 0, arr.length);

        return new ResultadoOrdenamiento("QuickSort", contador[0], tiempo);
    }

    private static void quickSortRec(Politico[] arr, int low, int high,
                                     Comparator<Politico> cmp, long[] contador) {
        if (low < high) {
            int pi = partition(arr, low, high, cmp, contador);
            quickSortRec(arr, low, pi - 1, cmp, contador);
            quickSortRec(arr, pi + 1, high, cmp, contador);
        }
    }

    private static int partition(Politico[] arr, int low, int high,
                                  Comparator<Politico> cmp, long[] contador) {
        Politico pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            contador[0]++;
            if (cmp.compare(arr[j], pivot) <= 0) {
                i++;
                Politico tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
            }
        }
        Politico tmp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = tmp;
        return i + 1;
    }

    // ── MergeSort ────────────────────────────────────────────────────────────

    public static ResultadoOrdenamiento mergeSort(Politico[] datos, Comparator<Politico> cmp) {
        Politico[] arr = datos.clone();
        long[] contador = {0};

        long inicio = System.currentTimeMillis();
        mergeSortRec(arr, 0, arr.length - 1, cmp, contador);
        long tiempo = System.currentTimeMillis() - inicio;

        System.arraycopy(arr, 0, datos, 0, arr.length);

        return new ResultadoOrdenamiento("MergeSort", contador[0], tiempo);
    }

    private static void mergeSortRec(Politico[] arr, int left, int right,
                                     Comparator<Politico> cmp, long[] contador) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortRec(arr, left, mid, cmp, contador);
            mergeSortRec(arr, mid + 1, right, cmp, contador);
            merge(arr, left, mid, right, cmp, contador);
        }
    }

    private static void merge(Politico[] arr, int left, int mid, int right,
                               Comparator<Politico> cmp, long[] contador) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        Politico[] L = new Politico[n1];
        Politico[] R = new Politico[n2];
        System.arraycopy(arr, left,      L, 0, n1);
        System.arraycopy(arr, mid + 1,   R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            contador[0]++;
            if (cmp.compare(L[i], R[j]) <= 0) { arr[k++] = L[i++]; }
            else                                { arr[k++] = R[j++]; }
        }
        while (i < n1) { arr[k++] = L[i++]; }
        while (j < n2) { arr[k++] = R[j++]; }
    }
}
