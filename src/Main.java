import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Main extends JFrame implements MouseListener {
    public static String TITLE = "Mandelbrot Set";
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static int MAX_ITERATIONS = 200;

    private double OFFSET_X = 0, OFFSET_Y = 0;
    private double ZOOM = 1;
    private static long calcTime = 0;

    private final MandelbrotSet set = new MandelbrotSet(WIDTH, HEIGHT);
    private ArrayList<Long> pixels;

    public Main() {
        setContentPane(new DrawPane());
        addMouseListener(this);
        generateSet();
    }

    public void generateSet() {
        long time = System.currentTimeMillis();
        pixels = set.generateSet(OFFSET_X, OFFSET_Y, ZOOM, MAX_ITERATIONS);
        calcTime = System.currentTimeMillis() - time;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double x = e.getX(), y = e.getY();

        if (e.getButton() == 1) {
            OFFSET_X += x - (double) WIDTH / 2;
            OFFSET_Y += y - (double) HEIGHT / 2;
        } else if (e.getButton() == 3) {
            ZOOM += 1;
        } else {
            return;
        }

        setTitle(TITLE + " - (calculating...)");
        generateSet();
        repaint();
        setTitle(TITLE + " - " + calcTime + " ms");
    }

    private class DrawPane extends JPanel {
        public void paintComponent(Graphics g) {
            for (long pixel : pixels) {
                int x = (int) (pixel >> 24) & 0xFFF;
                int y = (int) (pixel >> 12) & 0xFFF;
                float n = pixel & 0xFFF;

                g.setColor(Color.getHSBColor(1 * n / MAX_ITERATIONS, 1, n < MAX_ITERATIONS ? 1 : 0));
                g.fillRect(x, y, 1, 1);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new Main();

        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle(TITLE + " - " + calcTime + " ms");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
