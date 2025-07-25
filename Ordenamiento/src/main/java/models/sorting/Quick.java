package models.sorting;

import javax.swing.*;
import java.awt.*;

public class Quick extends JFrame {
    private int[] arr = {6, 2, 5, 3, 8, 7, 1, 4};
    private DrawPanel drawPanel;

    public Quick() {
        setTitle("Quick Sort Visual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        drawPanel = new DrawPanel(arr);
        add(drawPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Quick().setVisible(true);
        });
    }

    // Clase interna para dibujar las barras
    class DrawPanel extends JPanel {
        private int[] arr;

        public DrawPanel(int[] arr) {
            this.arr = arr;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth() / arr.length;
            int max = Integer.MIN_VALUE;
            for (int n : arr) {
                if (n > max) max = n;
            }

            for (int i = 0; i < arr.length; i++) {
                int height = (int)(((double)arr[i] / max) * (getHeight() - 30));
                g.setColor(Color.getHSBColor((float)i/arr.length, 0.7f, 0.9f));
                g.fillRect(i * width, getHeight() - height, width - 2, height);

                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(arr[i]), i * width + (width/4), getHeight() - 5);
            }
        }
    }
}
