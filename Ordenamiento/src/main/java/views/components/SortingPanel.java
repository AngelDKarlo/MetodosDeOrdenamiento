package views.components;

import models.sorting.RadixStep;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SortingPanel extends JPanel {

    private static final int DROP_DIAMETER = 30;
    private static final int PADDING = 40;
    private static final int CHANNEL_HEIGHT = 60;
    private static final int ANIMATION_SPEED = 5; // Píxeles por fotograma

    private int[] originalArray;
    private RadixStep currentStep;

    private final Map<Integer, Color> valueToColor = new HashMap<>();
    private final Map<Integer, Point2D.Double> dropPositions = new HashMap<>();
    private final Map<Integer, Point2D.Double> targetPositions = new HashMap<>();

    private enum AnimationPhase { IDLE, DISTRIBUTING, COLLECTING }
    private AnimationPhase phase = AnimationPhase.IDLE;
    private Timer animationTimer;

    private Runnable animationCompletionCallback;

    /**
     * Establece el callback que se ejecutará al completar la animación de un paso.
     * @param callback El Runnable a ejecutar.
     */
    public void setAnimationCompletionCallback(Runnable callback) {
        this.animationCompletionCallback = callback;
    }

    public void setInitialArray(int[] array) {
        this.originalArray = array;
        this.phase = AnimationPhase.IDLE;
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        valueToColor.clear();
        dropPositions.clear();
        targetPositions.clear();

        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            if (!valueToColor.containsKey(value)) {
                valueToColor.put(value, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            }
            Point2D.Double pos = new Point2D.Double(getInitialX(i), PADDING);
            dropPositions.put(value, pos);
            targetPositions.put(value, (Point2D.Double) pos.clone());
        }
        repaint();
    }

    public void updateStep(RadixStep step) {
        this.currentStep = step;
        if (step.isFinal()) {
            phase = AnimationPhase.IDLE;
            repaint();
        } else {
            startDistributionPhase();
        }
    }

    private void startDistributionPhase() {
        phase = AnimationPhase.DISTRIBUTING;
        int[] state = currentStep.arrayState();
        int exp = currentStep.currentExp();

        Map<Integer, Integer> channelCounts = new HashMap<>();

        for (int value : state) {
            int digit = (value / exp) % 10;
            int count = channelCounts.getOrDefault(digit, 0);
            double targetX = getChannelX(digit) + (count * (DROP_DIAMETER + 5)) + 10;
            double targetY = getHeight() - CHANNEL_HEIGHT / 2.0 - PADDING;
            targetPositions.put(value, new Point2D.Double(targetX, targetY));
            channelCounts.put(digit, count + 1);
        }
        startAnimation();
    }

    private void startCollectionPhase() {
        phase = AnimationPhase.COLLECTING;
        int[] state = currentStep.arrayState();

        for (int i = 0; i < state.length; i++) {
            int value = state[i];
            targetPositions.put(value, new Point2D.Double(getInitialX(i), PADDING));
        }
        startAnimation();
    }

    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        animationTimer = new Timer(16, e -> { // ~60 FPS
            boolean allInPlace = true;
            for (int value : originalArray) {
                Point2D.Double current = dropPositions.get(value);
                Point2D.Double target = targetPositions.get(value);

                double dx = target.x - current.x;
                double dy = target.y - current.y;

                if (Math.abs(dx) < ANIMATION_SPEED && Math.abs(dy) < ANIMATION_SPEED) {
                    current.setLocation(target);
                } else {
                    allInPlace = false;
                    double angle = Math.atan2(dy, dx);
                    current.x += ANIMATION_SPEED * Math.cos(angle);
                    current.y += ANIMATION_SPEED * Math.sin(angle);
                }
            }
            repaint();

            if (allInPlace) {
                animationTimer.stop();
                handleAnimationEnd();
            }
        });
        animationTimer.start();
    }

    /**
     * ✅ **MODIFICADO**: Maneja lo que sucede después de que una fase de animación termina.
     */
    private void handleAnimationEnd() {
        if (phase == AnimationPhase.DISTRIBUTING) {
            new Timer(500, e -> startCollectionPhase()) {{ setRepeats(false); }}.start();
        } else if (phase == AnimationPhase.COLLECTING) {
            if (animationCompletionCallback != null) {
                animationCompletionCallback.run();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        drawChannels(g2d);

        if (originalArray == null) return;

        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        for (int value : originalArray) {
            Point2D.Double pos = dropPositions.get(value);
            if (pos == null) continue;

            g2d.setColor(valueToColor.get(value));
            g2d.fillOval((int)(pos.x - DROP_DIAMETER / 2.0), (int)(pos.y - DROP_DIAMETER / 2.0), DROP_DIAMETER, DROP_DIAMETER);

            g2d.setColor(Color.WHITE);
            String text = String.valueOf(value);
            int textWidth = g2d.getFontMetrics().stringWidth(text);
            g2d.drawString(text, (int)(pos.x - textWidth / 2.0), (int)pos.y + 5);
        }
    }

    private void drawChannels(Graphics2D g2d) {
        int panelWidth = getWidth();
        int channelWidth = panelWidth / 10;

        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.setColor(Color.GRAY);

        for (int i = 0; i < 10; i++) {
            int x = i * channelWidth;
            g2d.drawRect(x, getHeight() - CHANNEL_HEIGHT - PADDING, channelWidth, CHANNEL_HEIGHT);
            g2d.drawString(String.valueOf(i), x + channelWidth / 2 - 5, getHeight() - PADDING + 15);
        }

        if (phase != AnimationPhase.IDLE && currentStep != null && !currentStep.isFinal()) {
            g2d.setColor(Color.CYAN);
            g2d.drawString("Clasificando por dígito: " + currentStep.currentExp(), 10, 20);
        }
    }

    private double getInitialX(int index) {
        return PADDING + index * (DROP_DIAMETER + 10) + DROP_DIAMETER / 2.0;
    }

    private double getChannelX(int digit) {
        return (getWidth() / 10.0) * digit;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}