package engine.graph;

public class PerlinNoise extends Noise {

    //The original permutation
    int[] permutation = {151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36,
            103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0,
            26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56,
            87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
            77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55,
            46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132,
            187, 208, 89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109,
            198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126,
            255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183,
            170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43,
            172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112,
            104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162,
            241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106,
            157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205,
            93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180};

    int[][] gradients = {{1, -1}, {-1, 1}, {1, 1}, {-1, -1}};

    public PerlinNoise(int xres, int yres) {
        super(xres, yres);
    }

    @Override
    public float[][] generateMap() {
        float[][] noiseMap = new float[xres * 10][yres * 10];
        for (int x = 0; x < xres * 10; x++) {
            for (int y = 0; y < yres * 10; y++) {
                noiseMap[x][y] = getPoint(x / 10.0f, y / 10.0f);
            }
        }
        return noiseMap;
    }

    private float getPoint(float x, float y) {
        int xfloor = (int) Math.floor(x);
        int yfloor = (int) Math.floor(y);
        int A = xfloor % 256;
        int B = (xfloor + 1) % 256;
        int C = yfloor % 256;
        int D = (yfloor + 1) % 256;
        x = x - xfloor;
        y = y - yfloor;
        float xfade = fade(x);
        float yfade = fade(y);
        int NW = permutation[(permutation[A] + C) % 256];
        int NE = permutation[(permutation[B] + C) % 256];
        int SW = permutation[(permutation[A] + D) % 256];
        int SE = permutation[(permutation[B] + D) % 256];
        float gNW = grad(NW % 4, x, y);
        float gNE = grad(NE % 4, x, y);
        float gSW = grad(SW % 4, x, y);
        float gSE = grad(SE % 4, x, y);
        float top = lerp(xfade, gNW, gNE);
        float bot = lerp(xfade, gSW, gSE);
//        System.out.println(top + " " + bot + " " + xfade + " " + yfade);
        return lerp(yfade, top, bot);
    }

    private float grad(int dir, float x, float y) {
        return gradients[dir][0] * x + gradients[dir][1] * y;
    }


    private float fade(float t) {
        return 6 * (float) Math.pow(t, 5) - 15 * (float) Math.pow(t, 4) + 10 * (float) Math.pow(t, 3);
    }


    private float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }
}
