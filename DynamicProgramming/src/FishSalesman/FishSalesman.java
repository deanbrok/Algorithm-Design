package FishSalesman;

public class FishSalesman {
    /**
     * @param n the number of days
     * @param P the profits that can be made on day 1 through n on location P are stored in P[1] through P[n].
     * @param Q the profits that can be made on day 1 through n on location Q are stored in Q[1] through Q[n].
     * @return the maximum obtainable profit.
     */
    public static int solve(int n, int[] P, int[] Q) {

        int[][] mem = new int[2][n + 1];

        mem[0][n] = P[n];
        mem[1][n] = Q[n];

        for (int j = n - 1; j >= 0; j--) {
            mem[0][j] = Math.max(mem[0][j+1] + P[j], mem[1][j+1]);
            mem[1][j] = Math.max(mem[1][j+1] + Q[j], mem[0][j + 1]);
        }

        return mem[0][0];
    }

    public static void main(String[] args) {
        int n = 5;
        int[] P = { 0, 80, 30, 30, 70, 80 };
        int[] Q = { 0, 90, 60, 60, 50, 20 };
        System.out.println(solve(n, P, Q));
    }

}
