package models.sorting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

//para hacer la animacion finalmente me decidi por hacer segun tematica espacial
//lo que esta ahi se supone que son cohetes
public class Heap extends JPanel {
    //heap es pues el array, el monticulo que vamos a ordenar
    ArrayList<Integer> heap = new ArrayList<>();
    ArrayList<Color> colorez = new ArrayList<>();
    int elementos = 10;
    int ancho = 40;
    int alto = 100;
    int espacio = 60;
    Color[] colores = {
            new Color(37, 78, 202), new Color(151, 60, 165),new Color(209, 163, 202),
            new Color(150, 141, 206), new Color(166, 91, 117),new Color(127, 198, 255),
            new Color(141, 206, 200), new Color(241, 120, 181),new Color(244, 180, 163), new Color(202, 29, 106)  };

    Timer timer;
    int i = elementos - 1;

    public Heap() {
        setPreferredSize(new Dimension(800, 400));
        setBackground(new Color(38, 38, 71));
        crearHeap();

        JButton ordenar = new JButton("Ordenar");
        ordenar.setContentAreaFilled(false);
        ordenar.setFocusPainted(false);
        ordenar.setOpaque(false);
        ordenar.setBorderPainted(false); // Elimina el borde
        ordenar.setForeground(Color.white);
        ordenar.addActionListener(e -> iniciarHeapSort());

        this.setLayout(null);
        ordenar.setBounds(650, 20, 120, 30);
        this.add(ordenar);
    }

    void crearHeap() {
        Random rand = new Random();
        heap.clear();
        colorez.clear();

        for (int i = 0; i < elementos; i++) {
            heap.add(rand.nextInt(50) + 1);
            colorez.add(colores[i % colores.length]);
        }
    }

    void heapify(int n, int i) {
        int mayor = i;
        int izq = 2 * i + 1;
        int der = 2 * i + 2;

        if (izq < n && heap.get(izq) > heap.get(mayor)) mayor = izq;
        if (der < n && heap.get(der) > heap.get(mayor)) mayor = der;

        if (mayor != i) {
            switcheo(i, mayor);
            heapify(n, mayor);
        }
    }

    void arboleando() {
        int n = heap.size();
        for (int i = n / 2 - 1; i >= 0; i--) heapify(n, i);
    }

    void switcheo(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);

        Color tempColor = colorez.get(i);
        colorez.set(i, colorez.get(j));
        colorez.set(j, tempColor);
    }

    void iniciarHeapSort() {
        arboleando();
        i = elementos - 1;
        timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (i > 0) {
                    switcheo(0, i);
                    heapify(i, 0);
                    i--;
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < heap.size(); i++) {
            int valor = heap.get(i);
            int x = 50 + i * espacio;
            int y = 250;


            g.setColor(colorez.get(i));
            // Cuerpo
            g.fillRect(x, y - alto, ancho, alto);
            // Punta (triángulo arriba)
            int[] xPunta = { x, x + ancho / 2, x + ancho};
            int[] yPunta = { y - alto, y - alto - 30, y - alto};
            g.fillPolygon(xPunta, yPunta, 3);
            // Alitazz
            int[] xAlaIzq = { x, x - 10, x };
            int[] yAlaIzq = { y - 20, y, y };
            g.fillPolygon(xAlaIzq, yAlaIzq, 3);

            int[] xAlaDer = { x + ancho, x + ancho + 10, x + ancho};
            int[] yAlaDer = { y - 20, y, y };
            g.fillPolygon(xAlaDer, yAlaDer, 3);

            // Círculo con número
            g.setColor(Color.WHITE);
            g.fillOval(x + 10, y - 50, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(valor), x + 15, y - 35);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Heap sort");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Heap());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
