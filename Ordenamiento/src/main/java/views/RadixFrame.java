package views;

import controllers.RadixSort;
import views.components.SortingPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RadixFrame extends JFrame {
    public RadixFrame() {
        setTitle("Visualizador de Radix Sort - Gotas de Agua");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SortingPanel sortingPanel = new SortingPanel();
        RadixSort controller = new RadixSort(sortingPanel);

        JButton startButton = new JButton("Iniciar Ordenamiento");
        startButton.addActionListener(e -> {
            // Genera un array de numeros al azar
            int cantidadDeNumeros = 12;
            int valorMaximo = 1000;
            int[] arregloAleatorio = new int[cantidadDeNumeros];
            Random rand = new Random();

            for (int i = 0; i < arregloAleatorio.length; i++) {
                arregloAleatorio[i] = rand.nextInt(valorMaximo) + 1;
            }

            // Inicia el proceso de ordenamiento
            controller.startSort(arregloAleatorio);
        });

        // Configura el callback
        final Object lock = new Object();
        sortingPanel.setAnimationCompletionCallback(() -> {
            synchronized (lock) {
                lock.notify();
            }
        });

        add(sortingPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }
}