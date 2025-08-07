// Archivo: controllers/RadixSort.java

package controllers;

import models.sorting.Radix;
import models.sorting.RadixStep;
import views.components.SortingPanel;
import javax.swing.SwingUtilities;

public class RadixSort {
    private final Radix model;
    private final SortingPanel view;

    public RadixSort(SortingPanel view) {
        this.model = new Radix();
        this.view = view;
    }

    public void startSort(int[] array) {
        view.setInitialArray(array.clone());

        new Thread(() -> {
            // Creamos un objeto que nos sirva de lock
            final Object lock = new Object();

            // Configuramos la vista con un 'callback' que se ejecutará
            view.setAnimationCompletionCallback(() -> {
                synchronized (lock) {
                    lock.notify(); // Despierta al hilo del algoritmo
                }
            });

            model.sort(array, step -> {
                // Pasamos el paso a la vista para que gestione la animación
                SwingUtilities.invokeLater(() -> view.updateStep(step));

                // Si no es el paso final, el hilo del algoritmo espera.
                if (!step.isFinal()) {
                    synchronized (lock) {
                        try {
                            // El hilo se pone en espera hasta que 'lock.notify()' sea llamado.
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            });
        }).start();
    }
}