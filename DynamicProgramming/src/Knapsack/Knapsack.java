package Knapsack;

public class Knapsack {

    /**
     * @param n the number of items
     * @param W the maximum weight
     * @param w the weight of the items, indexed w[1] to w[n].
     * @param v the value of the items, indexed v[1] to v[n];
     * @return the maximum obtainable value.
     */
    public static int mathijsFavouriteProblem(int n, int W, int[] w, int[] v) {
        int[][] O = new int[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            O[i][0] = 0;
        }

        for (int j = 1; j <= W; j++) {
            for (int i = 1; i <= n; i++) {
                if (w[i] <= j) {
                    O[i][j] = Math.max(O[i - 1][j], O[i - 1][j - w[i]] + v[i]);
                } else {
                    O[i][j] = O[i - 1][j];
                }
            }
        }

        return O[n][W];
    }

    public static void main(String[] args) {
        int n = 3;
        int W = 10;
        int[] w = { 0, 8, 3, 5 };
        int[] v = { 0, 8, 4, 9 };
        assert 13 == mathijsFavouriteProblem(n, W, w, v);
    }
}
