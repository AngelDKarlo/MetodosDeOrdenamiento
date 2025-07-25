package models.sorting;
import java.util.ArrayList;
import java.util.Random;

public class Heap {
    int elementos;
    ArrayList<Integer> heap = new ArrayList<>();


    void creacion(){
        Random rand = new Random();
        elementos = 6;
        for (int i = 0; i < elementos; i++) {
            heap.add(rand.nextInt(50));
        }
    }

    void arboleando(){
            int n = heap.size();
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(n, i);
            }
        }

        void heapify(int n, int i) {
            int mayor = i;
            int izq = 2 * i + 1;
            int der = 2 * i + 2;

            if (izq < n && heap.get(izq) > heap.get(mayor)) {
                mayor = izq;
            }

            if (der < n && heap.get(der) > heap.get(mayor)) {
                mayor = der;
            }

            if (mayor != i) {
                switcheo(i, mayor);
                heapify(n, mayor);
            }
        }

        void heapsort() {
            int n = heap.size();
            arboleando();

            for (int i = n - 1; i > 0; i--) {
                switcheo(0, i);
                heapify(i, 0);
            }
        }

        void switcheo(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        void imprimirHeap() {
            System.out.println(heap);
        }

        public static void main(String[] args) {
            Heap miHeap = new Heap();
            miHeap.creacion();
            System.out.println("Arreglo original:");
            miHeap.imprimirHeap();

            miHeap.heapsort();
            System.out.println("Después de heapsort:");
            miHeap.imprimirHeap();
        }
    }
