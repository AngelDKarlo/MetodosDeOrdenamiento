package views;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Menú Principal - Visualizador de Algoritmos de Ordenamiento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Un tamaño fijo para el menú.

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 filas, 1 columna, con espaciado.
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Margen.

        // Botón para Radix Sort
        JButton radixButton = new JButton("Visualizar Radix Sort");
        radixButton.setFont(new Font("Arial", Font.BOLD, 14));
        radixButton.addActionListener(e -> {
            // ventana de Radix.
            RadixFrame radixFrame = new RadixFrame();
            radixFrame.setVisible(true);
        });

        // Añadimos los botones al panel.
        buttonPanel.add(radixButton);

        // Añadimos el panel de botones al frame.
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }
}