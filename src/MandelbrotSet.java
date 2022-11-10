import java.util.ArrayList;

public class MandelbrotSet {
    public int WIDTH;
    public int HEIGHT;

    public double MIN_RE = -2;
    public double MAX_RE = 1;
    public double MIN_IM = -1;
    public double MAX_IM = 1;

    public MandelbrotSet(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    public ArrayList<Long> generateSet(double offsetX, double offsetY, double zoom, int maxIter) {
        ArrayList<Long> pixels = new ArrayList<>();
        // For some reason when the array is cleared the objects are still in memory.
        System.gc();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double cr = (MIN_RE + (MAX_RE - MIN_RE) * (x + offsetX) / WIDTH) / zoom;
                double ci = (MIN_IM + (MAX_IM - MIN_IM) * (y + offsetY) / HEIGHT) / zoom;
                double re = 0, im = 0;

                int n;
                for (n = 0; n < maxIter; n++) {
                    double tr = re * re - im * im + cr;
                    im = 2 * re * im + ci;
                    re = tr;

                    if (re * re + im * im > 4) break;
                }

                pixels.add(((((long) x << 12) + y) << 12) + n);
            }
        }

        return pixels;
    }
}
