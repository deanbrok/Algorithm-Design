package Knapsack;

import java.util.LinkedList;
import java.util.List;

public class KnapsackRetrieval {
    /**
     * Retrieves the knapsack items used in an optimal solution.
     *
     * @param n the number of items.
     * @param W the maximum weight.
     * @param w the weight of the items, indexed w[1] to w[n].
     * @param v the value of the items, indexed v[1] to v[n].
     * @param mem is a (n x W) integer array, where element mem[i][j] is
     *            the maximum value using only elements 1 to i and max weight of j.
     *
     * @return list containing the id of the items used in the optimal solution, ordered increasingly.
     */
    public static List<Integer> mathijsFavouriteProblem(int n, int W, int[] w, int[] v, int[][] mem) {
        // TODO
        LinkedList<Integer> results = new LinkedList<>();

        int i = n;
        int j = W;
        int value = mem[n][W];

        while (i > 0) {
            if (w[i] > j) {
                i--;
            } else {
                if (mem[i - 1][j - w[i]] + v[i] > mem[i - 1][j]) {
                    results.addFirst(i);
                    j = j - w[i];
                }
                i--;
            }
        }

        return results;
    }

    public static void main(String[] args) {
        int n = 3;
        int W = 10;
        int[] w = { 0, 8, 3, 5 };
        int[] v = { 0, 8, 4, 9 };
        int[][] mem = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 8 },
                        { 0, 0, 0, 4, 4, 4, 4, 4, 8, 8, 8 },
                        { 0, 0, 0, 4, 4, 9, 9, 9, 13, 13, 13 } };

        System.out.println(mathijsFavouriteProblem(n, W, w, v, mem));
    }
}
