package Practice;

import java.util.LinkedList;
import java.util.List;

public class DP {
    public static int mathijsFavouriteProblem(int n, int W, int[] w, int[] v) {
        int[][] mem = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                if (w[i] > j) mem[i][j] = mem[i - 1][j];
                else {
                    mem[i][j] = Math.max(mem[i - 1][j], v[i] + mem[i - 1][j - w[i]]);
                }
            }
        }

        return mem[n][W];
    }
    public static List<Integer> retrieveKnapsack(int n, int W, int[] w, int[] v, int[][] mem) {
        LinkedList<Integer> solution = new LinkedList<>();
        findSolution(n, W, w, v, mem, solution);
        return solution;
    }

    private static void findSolution(int i, int W, int[] w, int[] v, int[][] mem, LinkedList<Integer> solution) {
        if (i == 0) return;

        if (w[i] > W || mem[i - 1][W] > v[i] + mem[i - 1][W - w[i]]) {
            findSolution(i - 1, W, w, v, mem, solution);
        } else {
            solution.addFirst(i);
            findSolution(i - 1, W - w[i], w, v, mem, solution);
        }
    }

    public static double optimalTrade(int t, double[] r) {
        double[][] mem = new double[t + 1][2];

        mem[0][0] = 0.1;

        for (int i = 1; i <= t ; i++) {
            mem[i][0] = Math.max((mem[i - 1][1] - 5) / r[i], mem[i - 1][0]);
            mem[i][1] = Math.max(mem[i - 1][1], 0.95 * mem[i - 1][0] * r[i]);
        }

        return mem[t][1];
    }

    public static int maxGrade(int n, int h, int[][] f) {
        int[][] mem = new int[n + 1][h + 1];

        for (int i = 1; i <= n; i++) mem[i][0] = f[i][0];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= h; j++) {
                for (int k = 0; k <= j; k++) {
                    if (j - k >= 0) mem[i][j] = Math.max(mem[i][j], f[i][k] + mem[i - 1][j - k]);
                }
            }
        }

        return mem[n][h];
    }
}

