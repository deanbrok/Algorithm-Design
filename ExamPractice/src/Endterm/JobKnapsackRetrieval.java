package Endterm;

import java.util.LinkedList;
import java.util.List;

//2018 - 2019 Final
public class JobKnapsackRetrieval {
    /**
     *   You should implement this method.
     *   @param n The number of updates
     *   @param C the (monthly) cost budget
     *   @param benefits An array of dimension n+1 containing the benefits of all the code changes for 1 <= i <= n
     *   @param costs An array of dimension n+1 containing the costs of all the code changes for 1 <= i <= n
     *   @param M The memory array of dimension (n+1)*(C+1) filled based on the recursive formula.
     *   @return A list of all indices of the updates that should be included, in _increasing_ order.
     */
    public static List<Integer> solve(int n, int C, int[] benefits, int[] costs, int[][] M) {
        LinkedList<Integer> solution = new LinkedList<>();
        findJobs(n, C, benefits, costs, M, solution);
        return solution;
    }

    private static void findJobs(int i, int c, int[] benefits, int[] costs, int[][] M, LinkedList<Integer> solution) {
        if (i == 0) return;

        if (costs[i] > c) findJobs(i - 1, c, benefits, costs, M, solution);

        else {
            if (benefits[i] + M[i - 1][c - costs[i]] > M[i - 1][c]) {
                solution.addFirst(i);
                findJobs(i - 1, c - costs[i], benefits, costs, M, solution);
            } else {
                findJobs(i - 1, c, benefits, costs, M, solution);
            }
        }
    }
}
