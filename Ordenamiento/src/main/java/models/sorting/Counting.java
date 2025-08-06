package models.sorting;


import java.util.Random;

public class Counting {

    /**
     * Ordena un array de enteros usando el algoritmo Counting Sort.
     * @param array El array a ordenar.
     */
    public static void countSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        // Encontrar el valor máximo y mínimo en el array
        int max = array[0];
        int min = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] > max) {
                max = array[j];
            }
            if (array[j] < min) {
                min = array[j];
            }
        }

        // Crear el array de conteo con el rango adecuado
        int range = max - min + 1;
        int[] count = new int[range];

        // Contar la frecuencia de cada número
        for (int num : array) {
            count[num - min]++;
        }

        // Reconstruir el array ordenado usando el array de conteo
        int arrIndex = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                array[arrIndex++] = i + min;
                count[i]--;
            }
        }
    }

    public static void main(String[] args) {
        // Parámetros para el array aleatorio
        int size = 20;
        int minValue = 5;
        int maxValue = 50;

        // Generar un array de 20 números aleatorios entre 5 y 50 (inclusive)
        int[] data = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            data[i] = rand.nextInt(maxValue - minValue + 1) + minValue;
        }

        // Mostrar el array original
        System.out.println("Array original:");
        for (int num : data) {
            System.out.print(num + " ");
        }
        System.out.println();

        // Ordenar el array usando Counting Sort
        countSort(data);

        // Mostrar el array ordenado
        System.out.println("Array ordenado:");
        for (int num : data) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
