// Archivo: models/sorting/Radix.java

package models.sorting;

import java.util.Arrays;
import java.util.function.Consumer;

public class Radix {

    public void sort(int[] arr, Consumer<RadixStep> visualizer) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int max = getMax(arr);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp, visualizer);
        }

        visualizer.accept(new RadixStep(arr.clone(), 0, true));
    }

    private int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    private void countingSortByDigit(int[] arr, int exp, Consumer<RadixStep> visualizer) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }

        visualizer.accept(new RadixStep(arr.clone(), exp, false));
    }
}