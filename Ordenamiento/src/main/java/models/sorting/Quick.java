package models.sorting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quick extends JFrame {
    private int[] arr = {6, 2, 5, 3, 8, 7, 1, 4};
    private DrawPanel drawPanel;

    public Quick() {
        setTitle("Quick Sort Visual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        drawPanel = new DrawPanel(arr);

        JButton btnOrdenar = new JButton("Ordenar con Quick Sort");
        btnOrdenar.setFont(new Font("Arial", Font.BOLD, 16));
        btnOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quickSort(arr, 0, arr.length - 1);
                drawPanel.repaint();
            }
        });

        add(drawPanel, BorderLayout.CENTER);
        add(btnOrdenar, BorderLayout.SOUTH);
    }

    private void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int pivot = partition(arr, start, end);
            quickSort(arr, start, pivot - 1);
            quickSort(arr, pivot + 1, end);
        }
    }

    private int partition(int[] arr, int start, int end) {
        int pivot = end;
        int i = start - 1;
        int j = start;

        while (j < pivot) {
            if (arr[j] > arr[pivot]) {
                j++;
            } else {
                i++;
                swap(arr, i, j);
                j++;
            }
        }

        swap(arr, i + 1, pivot);
        return i + 1;
    }

    private void swap(int[] arr, int firstIndex, int secondIndex) {
        int temp = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = temp;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Quick().setVisible(true);
        });
    }
}

class DrawPanel extends JPanel {
    private int[] arr;

    public DrawPanel(int[] arr) {
        this.arr = arr;
        setBackground(new Color(240, 248, 255)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int circleDiameter = 30;
        int spacing = 20;

        int widthPerElement = panelWidth / arr.length;

        for (int i = 0; i < arr.length; i++) {
            int xCenter = i * widthPerElement + widthPerElement / 2;
            int yBase = panelHeight - 70;

            Color bubbleColor = Color.getHSBColor((float)i / arr.length, 0.4f, 0.85f);
            g2.setColor(bubbleColor);

            for (int b = 0; b < arr[i]; b++) {
                int y = yBase - (b * (circleDiameter + 5));
                int x = xCenter - circleDiameter / 2;

                g2.setColor(new Color(0, 0, 0, 30));
                g2.fillOval(x + 3, y + 3, circleDiameter, circleDiameter);

                g2.setColor(bubbleColor);
                g2.fillOval(x, y, circleDiameter, circleDiameter);

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(x, y, circleDiameter, circleDiameter);
            }

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            String value = String.valueOf(arr[i]);
            FontMetrics fm = g2.getFontMetrics();
            int strWidth = fm.stringWidth(value);
            g2.drawString(value, xCenter - strWidth / 2, panelHeight - 20);
        }

        g2.dispose();
    }
}




