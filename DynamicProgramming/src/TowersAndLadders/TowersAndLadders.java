package TowersAndLadders;

import java.util.Objects;

public class TowersAndLadders {

    public static int solve(int n, int m, int[][] graph) {
        int[][] mem = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mem[i][j] = Integer.MAX_VALUE;
            }
        }

        mem[n - 1][m - 1] = 0;

        for (int i = 0; i < n * m; i++) {
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < m; y++) {
                    if (x - 1 >= 0) mem[x][y] = Math.min(mem[x][y], Math.max(mem[x - 1][y], graph[x - 1][y] - graph[x][y]));
                    if (x + 1 < n)  mem[x][y] = Math.min(mem[x][y], Math.max(mem[x + 1][y], graph[x + 1][y] - graph[x][y]));
                    if (y - 1 >= 0) mem[x][y] = Math.min(mem[x][y], Math.max(mem[x][y - 1], graph[x][y - 1] - graph[x][y]));
                    if (y + 1 < m)  mem[x][y] = Math.min(mem[x][y], Math.max(mem[x][y + 1], graph[x][y + 1] - graph[x][y]));
                }
            }
        }

        return mem[0][0];
    }


    public static void main(String[] args) {
        int n = 1;
        int m = 3;
        int[][] graph = { { 3, 4, 5 }};

        System.out.println(solve(n,m, graph));
    }


}
