package models.sorting;

import javax.swing.*;
import java.awt.*;

public class Quick extends JFrame {
    private int[] numeros = {6, 2, 5, 3, 8, 7, 1, 4};
    private PanelCartas panel;

    public Quick() {
        setTitle("Quick Sort");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new PanelCartas(numeros);
        JButton botonOrdenar = new JButton("Ordenar");
        botonOrdenar.setFont(new Font("Arial", Font.BOLD, 16));
        botonOrdenar.setBackground(new Color(70, 130, 180));
        botonOrdenar.setForeground(Color.WHITE);
        botonOrdenar.addActionListener(e -> {
            ordenarRapido(numeros, 0, numeros.length - 1);
            panel.repaint();
        });

        add(panel, BorderLayout.CENTER);
        add(botonOrdenar, BorderLayout.SOUTH);
    }

    private void ordenarRapido(int[] lista, int inicio, int fin) {
        if (inicio < fin) {
            int medio = dividir(lista, inicio, fin);
            ordenarRapido(lista, inicio, medio - 1);
            ordenarRapido(lista, medio + 1, fin);
        }
    }

    private int dividir(int[] lista, int inicio, int fin) {
        int pivote = lista[fin];
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (lista[j] <= pivote) {
                intercambiar(lista, ++i, j);
            }
        }
        intercambiar(lista, i + 1, fin);
        return i + 1;
    }

    private void intercambiar(int[] lista, int a, int b) {
        int temp = lista[a];
        lista[a] = lista[b];
        lista[b] = temp;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Quick().setVisible(true));
    }
}

class PanelCartas extends JPanel {
    private int[] datos;

    public PanelCartas(int[] datos) {
        this.datos = datos;
        setBackground(new Color(255, 255, 240));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int anchoCarta = 60;
        int altoCarta = 80;
        int espacio = 20;
        int totalAncho = datos.length * (anchoCarta + espacio);
        int inicioX = (getWidth() - totalAncho) / 2;

        for (int i = 0; i < datos.length; i++) {
            int x = inicioX + i * (anchoCarta + espacio);
            int y = 100 + (i % 2 == 0 ? 0 : 30);
            Color color = Color.getHSBColor((float) i / datos.length, 0.4f, 0.9f);

            g2.setColor(color);
            g2.fillRoundRect(x, y, anchoCarta, altoCarta, 20, 20);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRoundRect(x, y, anchoCarta, altoCarta, 20, 20);

            g2.setFont(new Font("Arial", Font.BOLD, 22));
            String texto = String.valueOf(datos[i]);
            int anchoTexto = g2.getFontMetrics().stringWidth(texto);
            g2.setColor(Color.WHITE);
            g2.drawString(texto, x + (anchoCarta - anchoTexto) / 2, y + altoCarta / 2 + 10);
        }
    }
}





