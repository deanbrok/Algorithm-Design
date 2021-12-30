package TightOnTime;

import java.util.Arrays;

public class TightOnTime {
    /**
     * Implement this method
     *
     * @param n - the number of assignments
     * @param h - the number of hours you can spend
     * @param f - the function in the form of a (n + 1) x (h + 1) matrix.
     *          Index 0 of the number of assignments should be ignored.
     *          Index 0 of the number of hours spend is the minimum grade for this assignment.
     * @return the max grade achievable
     */
    public static int maxGrade(int n, int h, int[][] f) {

        int[][] mem = new int[n + 1][h + 1];

        for (int i = 1; i <= n; i++) {
            mem[i][0] = i ;
        }

        for (int c = 1; c <= n; c++) {
            for (int t = 1; t <= h; t++) {
                mem[c][t] = 1;
                for (int i = 0; i <= t; i++) {
                    mem[c][t] = Math.max(mem[c][t], mem[c - 1][t - i] + f[c][i]);
                }
            }
        }
        
        return mem[n][h];
    }

    public static void main(String[] args) {
        int n = 2;
        int h = 1;
        int[][] f = { { 0, 0 }, { 1, 2 }, { 1, 4 } };
        System.out.println(maxGrade(n, h, f));
    }
}
