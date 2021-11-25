import java.util.Random;

public abstract class Noise {
    int[][] directions_four = {{1, 0}, {0, 1}, {0, -1}, {-1, 0}};
    int[][] directions_eight = {{1, 0}, {0, 1}, {0, -1}, {-1, 0}, {1, -1}, {-1, 1}, {-1, -1}, {1, 1}};
    int seed = 2021;

    Random generator = new Random(seed);
    int xres;
    int yres;

    public Noise(int xres, int yres) {
        this.xres = xres;
        this.yres = yres;
    }

    public abstract double[][] generateMap();
}
