package models.sorting;
import java.util.Arrays;

public class Counting {
    public static void countSort(int[] array){
        if (array == null || array.length == 0){
            return;
        }

        // Encontrar el valor máximo y mínimo
        int max = array[0];
        int min = array[0];
        for (int j = 1; j < array.length; j++){
            if (array[j] > max){
                max = array[j];
            }
            if (array[j] < min){
                min = array[j];
            }
        }

        // Crear array de conteo del tamaño adecuado
        int range = max - min + 1;
        int[] count = new int[range];
        for (int num : array){
            count[num - min]++;
        }

        // Reconstruir el array ordenado
        int arrIndex = 0;
        for (int i = 0; i < count.length; i++){
            while (count[i] > 0) {
                array[arrIndex++] = i + min;
                count[i]--;
            }
        }
    }

    public static void main (String[] args) {
        int[] data = {4, 2, 5, 6, 8, 9, 9, 9, 1, 1, 3, 5, 2};

        System.out.println("array original:");
        for (int num : data) {
            System.out.print(num + " ");
        }
        System.out.println();

        Counting.countSort(data); // corregido

        System.out.println("array acomodado:");
        for (int num : data) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
