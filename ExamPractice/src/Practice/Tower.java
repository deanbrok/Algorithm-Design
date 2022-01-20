package Practice;

import java.util.Arrays;

public class Tower {

    public static int solve(int n, int m, int[][] graph) {
    /*
    //
    // Come up with an iterative dynamic programming solution to the ladder problem.
    // TODO mem[0] = ...; // Base case
    // TODO mem[i] = ...;
    */
        int[][] mem = new int[n][m];
        // TODO return 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mem[i][j] = Integer.MAX_VALUE;
            }
        }

        mem[0][0] = 0;


        for (int i = 0; i < n*m - 1; i++) {
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < m; y++) {
                    if (x - 1 > 0) mem[x][y] = Math.min(mem[x][y], Math.max(mem[x - 1][y], calculateCost(x, y, x - 1, y, graph)));
                    if (y - 1 > 0) mem[x][y] = Math.min(mem[x][y], Math.max(mem[x][y - 1], calculateCost(x, y, x, y - 1, graph)));
                    if (x + 1 < n) mem[x][y] = Math.min(mem[x][y], Math.max(mem[x + 1][y], calculateCost(x, y, x + 1, y, graph)));
                    if (y + 1 < m) mem[x][y] = Math.min(mem[x][y], Math.max(mem[x][y + 1], calculateCost(x, y, x, y + 1, graph)));
                }
            }
        }



        return mem[n - 1][m - 1];

    }

    private static int calculateCost(int fromX, int fromY, int toX, int toY, int[][] graph) {
        return graph[toX][toY] - graph[fromX][fromY] < 0 ? 0 : graph[toX][toY] - graph[fromX][fromY];
    }

    public static void main(String[] args) {
        int n = 2;
        int m = 3;
        int[][] graph = { { 3, 5, 6 }, { 4, 2, 1 } };
        System.out.println(solve(n,m,graph));
    }
}
