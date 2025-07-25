package models.sorting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Merge extends JPanel {
    private int[] arr = {2, 9, 8, 3, 6, 4, 10, 7};
    private int[] currentArr;
    private String mensaje = "Array inicial";
    private int paso = 0;
    private Timer timer;

    public Merge() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
        currentArr = new int[arr.length];

        // Copiar array original
        for (int i = 0; i < arr.length; i++) {
            currentArr[i] = arr[i];
        }

        timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paso++;
                if (paso == 1) {
                    mensaje = "Empezando merge sort...";
                    repaint();
                } else if (paso == 2) {
                    mensaje = "Ordenando el array...";
                    currentArr = mergesort(arr, 0, arr.length - 1);
                    repaint();
                } else {
                    timer.stop();
                    mensaje = "¡Array ordenado!";
                    repaint();
                }
            }
        });
    }

    public static int[] mergesort(int[] arr, int lo, int hi) {
        if (lo == hi) {
            int[] ba = new int[1];
            ba[0] = arr[lo];
            return ba;
        }

        int mid = (lo + hi) / 2;
        int arr1[] = mergesort(arr, lo, mid);
        int arr2[] = mergesort(arr, mid + 1, hi);
        return merge(arr1, arr2);
    }

    public static int[] merge(int[] arr1, int[] arr2) {
        int i = 0, j = 0, k = 0;
        int n = arr1.length;
        int m = arr2.length;
        int[] arr3 = new int[m + n];

        while (i < n && j < m) {
            if (arr1[i] < arr2[j]) {
                arr3[k] = arr1[i];
                i++;
            } else {
                arr3[k] = arr2[j];
                j++;
            }
            k++;
        }

        while (i < n) {
            arr3[k] = arr1[i];
            i++;
            k++;
        }

        while (j < m) {
            arr3[k] = arr2[j];
            j++;
            k++;
        }

        return arr3;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar título
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("Merge Sort", 20, 30);

        // Dibujar mensaje
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(mensaje, 20, 60);

        // Dibujar las barras
        int x = 50;
        int y = 300;

        for (int i = 0; i < currentArr.length; i++) {
            // Altura de la barra basada en el valor
            int altura = currentArr[i] * 20;

            // Dibujar la barra
            g.setColor(Color.BLUE);
            g.fillRect(x + i * 60, y - altura, 50, altura);

            // Dibujar el borde
            g.setColor(Color.BLACK);
            g.drawRect(x + i * 60, y - altura, 50, altura);

            // Dibujar el número
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("" + currentArr[i], x + i * 60 + 20, y - altura/2 + 5);

            // Dibujar índice
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString("" + i, x + i * 60 + 20, y + 15);
        }
    }

    public void empezar() {
        timer.start();
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Merge Sort");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Merge panel = new Merge();
        ventana.add(panel);

        JButton boton = new JButton("Empezar");
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.empezar();
            }
        });

        JPanel botones = new JPanel();
        botones.add(boton);

        ventana.add(botones, BorderLayout.SOUTH);
        ventana.pack();
        ventana.setVisible(true);
    }
}