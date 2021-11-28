package engine.graph;

import java.util.HashMap;

public class PolynomialNoise extends Noise {
    int N = 3;
    float h00 = (float) (Math.random());
    float h10 = (float) (Math.random());
    float h01 = (float) (Math.random());
    float h11 = (float) (Math.random());
    HashMap<Integer, int[][]> arrayMap;
    int[][] cij3 = {{1, -1, 1}, {-1, 1, -1}, {-1, 1, -1}};

    public PolynomialNoise(int xres, int yres) {
        super(xres, yres);
        arrayMap = new HashMap<>();
        arrayMap.put(3, cij3);
    }
//
//    public float OctaveEval(float x, float y, int octaves, float persistence) {
//        float total = 0;
//        float frequency = 1;
//        float amplitude = 1;
//        float maxValue = 0;            // Used for normalizing result to 0.0 - 1.0
//        for (int i = 0; i < octaves; i++) {
//            total += compute(x * frequency, y * frequency) * amplitude;
//
//            maxValue += amplitude;
//
//            amplitude *= persistence;
//            frequency *= 2;
//        }
//
//        return total / maxValue;
//    }


    @Override
    public float[][] generateMap() {
//        float[][] randomMap = new float[xres / 10 + 2][yres / 10 + 2];
//        for (int x = 0; x < xres / 10 + 2; x++) {
//            for (int y = 0; y < yres / 10 + 2; y++) {
//                randomMap[x][y] = (float) Math.random();
//            }
//        }
        float[][] noiseMap = new float[xres][yres];
        noiseMap[0][0] = h00;
        noiseMap[xres - 1][0] = h10;
        noiseMap[0][yres - 1] = h01;
        noiseMap[xres - 1][yres - 1] = h11;
//        helper(0, yres - 1, 0, xres - 1, noiseMap);
        float middle = (float) Math.random();
        noiseMap[xres / 2][yres / 2] = middle;
        noiseMap[xres / 2 - 1][yres / 2] = middle;
        noiseMap[xres / 2][yres / 2 - 1] = middle;
        noiseMap[xres / 2 - 1][yres / 2 - 1] = middle;
        for (int x = 0; x < xres / 2; x++) {
            for (int y = 0; y < yres / 2; y++) {
                if (noiseMap[x][y] == 0)
                    noiseMap[x][y] = compute(x, y, 0, yres / 2 - 1, 0, xres / 2 - 1, noiseMap);
            }
        }
        for (int x = xres / 2; x < xres; x++) {
            for (int y = 0; y < yres / 2; y++) {
                if (noiseMap[x][y] == 0)
                    noiseMap[x][y] = compute(x, y, 0, yres / 2 - 1, xres / 2, xres - 1, noiseMap);
            }
        }
        for (int x = 0; x < xres / 2; x++) {
            for (int y = yres / 2; y < yres; y++) {
                if (noiseMap[x][y] == 0)
                    noiseMap[x][y] = compute(x, y, yres / 2, yres - 1, 0, xres / 2 - 1, noiseMap);
            }
        }
        for (int x = xres / 2; x < xres; x++) {
            for (int y = yres / 2; y < yres; y++) {
                if (noiseMap[x][y] == 0)
                    noiseMap[x][y] = compute(x, y, yres / 2, yres - 1, xres / 2, xres - 1, noiseMap);
            }
        }
        return noiseMap;
    }


    private void helper(int u, int d, int l, int r, float[][] map) {
        if (u >= d) {
            return;
        }
        if (l >= r) {
            return;
        }
        int udm = u + (d - u) / 2;
        int lrm = l + (r - l) / 2;
        map[udm][lrm] = compute(lrm, udm, u, d, l, r, map);
        map[udm + 1][lrm] = compute(lrm, udm + 1, u, d, l, r, map);
        map[udm][lrm + 1] = compute(lrm + 1, udm, u, d, l, r, map);
        map[udm + 1][lrm + 1] = compute(lrm + 1, udm + 1, u, d, l, r, map);

        map[l][udm] = (float) (Math.random());
        map[l][udm + 1] = map[l][udm];
        map[r][udm] = (float) (Math.random());
        map[r][udm + 1] = map[r][udm];
        map[lrm][u] = (float) (Math.random());
        map[lrm + 1][u] = map[lrm][u];
        map[lrm][d] = (float) (Math.random());
        map[lrm + 1][d] = map[lrm][d];
        helper(udm + 1, d, l, lrm, map);
        helper(udm + 1, d, lrm + 1, r, map);
        helper(u, udm, lrm + 1, r, map);
        helper(u, udm, l, lrm, map);
    }

    private float fade(float t) {
        return 3 * t * t - 2 * t * t * t;
    }


    private float compute(float x, float y, int u, int d, int l, int r, float[][] map) {
        int xlen = r - l;
        int ylen = d - u;
        System.out.println(x + " " + y + " " + u + " " + d + " " + l + " " + r + " " + xlen + " " + ylen);
        x = x - l;
        y = y - u;
        x = x / (float) xlen;
        y = y / (float) ylen;
        System.out.println(x + " " + y);
        float deltax = map[r][u] - map[l][u];
        float deltay = map[l][d] - map[l][u];
        float A = map[r][d] + map[l][u] - map[r][u] - map[l][d];
        float res = map[l][u] + fade(x) * deltax + fade(y) * deltay + A * (fade(x) * y + fade(y) * x + x * y);
        System.out.println(res);
        return res;
    }
}
