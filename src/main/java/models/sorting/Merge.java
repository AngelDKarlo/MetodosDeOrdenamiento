import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mergesort extends JPanel {
    private int[] arr = {2, 9, 8, 3, 6, 4, 10, 7};
    private int[] currentArr;
    private String mensaje = "Array inicial";
    private int paso = 0;
    private Timer timer;

    public mergesort() {
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
                    //mensajes que se muestran en la ventana mientras sucede el procedimiento
                    mensaje = "empezando";
                    repaint();
                } else if (paso == 2) {
                    mensaje = "ordenando array";
                    currentArr = mergesort(arr, 0, arr.length - 1);
                    repaint();
                } else {
                    timer.stop();
                    mensaje = "se ordeno el array";
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuja título
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("Merge Sort", 20, 30);

        // Dibuja mensaje
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(mensaje, 20, 60);

        // Dibuja los elementos como círculos conectados
        int centerX = getWidth() / 2;
        int centerY = 200;
        int radius = 30;
        int spacing = 80;

        // Calcula posición inicial para centrar
        int startX = centerX - ((currentArr.length - 1) * spacing) / 2;

        for (int i = 0; i < currentArr.length; i++) {
            int x = startX + i * spacing;
            int y = centerY;

            // Color basado en el valor para mejor visualización
            Color circleColor = getColorByValue(currentArr[i]);

            // Dibuja sombra del círculo
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillOval(x - radius + 3, y - radius + 3, radius * 2, radius * 2);

            // Dibuja el círculo
            g2d.setColor(circleColor);
            g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);

            // Dibujar el borde del círculo
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);

            // Dibuja el número dentro del círculo
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g.getFontMetrics();
            String value = "" + currentArr[i];
            int textWidth = fm.stringWidth(value);
            int textHeight = fm.getAscent();
            g.drawString(value, x - textWidth/2, y + textHeight/2 - 2);

            // Dibuja índice debajo del círculo
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            fm = g.getFontMetrics();
            String index = "" + i;
            int indexWidth = fm.stringWidth(index);
            g.drawString(index, x - indexWidth/2, y + radius + 20);

            // Dibuja flecha hacia el siguiente elemento (excepto el último)
            if (i < currentArr.length - 1) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.setStroke(new BasicStroke(2));

                // Línea de la flecha
                int arrowStartX = x + radius;
                int arrowEndX = startX + (i + 1) * spacing - radius;
                g2d.drawLine(arrowStartX, y, arrowEndX, y);

                // Punta de la flecha
                int[] arrowX = {arrowEndX, arrowEndX - 10, arrowEndX - 10};
                int[] arrowY = {y, y - 5, y + 5};
                g2d.fillPolygon(arrowX, arrowY, 3);
            }
        }
    }

    // Método para asignar colores basados en el valor
    private Color getColorByValue(int value) {
        if (value <= 3) return new Color(178, 150, 212);   // Rojo para valores bajos
        else if (value <= 6) return new Color(108, 144, 171); // Azul para valores medios
        else return new Color(145, 113, 210);             // Verde para valores altos
    }

    public void empezar() {
        timer.start();
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Merge Sort");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mergesort panel = new mergesort();
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