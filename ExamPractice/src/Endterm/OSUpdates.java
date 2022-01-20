package Endterm;

// 2018-2019 Final
public class OSUpdates {
    /**
     *   You should implement this method. Note that your solution should be _iterative_!
     *   @param n The number of updates to ship.
     *   @param c The cost of shipping one bundle of updates.
     *   @param costs The costs matrix of dimension (n+1)*(n+1), where costs[i][j] denotes the costs of bundling updates i,i+1,...,j; given for all 1 <= i <= j <= n
     *   @return The minimal costs of bundling.
     */
    public static int solve(int n, int c, int[][] costs) {
        int[] mem = new int[n + 1];

        for (int j = 1; j <= n; j++) {
            mem[j] = Integer.MAX_VALUE;
            for (int i = 1; i <= j; i++) {
                mem[j] = Math.min(mem[j],costs[i][j] + c + mem[i - 1]);
            }
        }

        return mem[n];
    }
}
