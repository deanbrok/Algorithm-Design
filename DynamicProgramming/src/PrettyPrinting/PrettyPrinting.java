package PrettyPrinting;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class PrettyPrinting {
    public static List<String> solve(InputStream in) {

        // Parse input
        Scanner sc = new Scanner(in);
        int maxLength = sc.nextInt();
        int n = sc.nextInt();
        String[] words = new String[n];

        for (int i = 0; i < n; i++)
            words[i] = sc.next();

        sc.close();

        // Calculate slacks
        int[][] slacks = new int[n][n];

        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= j; i++) {
                slacks[i][j] = calculateSlack(i, j, words, maxLength);
            }
        }

        // Dynamic programming algorithm
        int[] mem = new int[n + 1];

        for (int j = 1; j <= n; j++) {
            mem[j] = Integer.MAX_VALUE;
            for (int i = 1; i <= j; i++) {
                if (slacks[i - 1][j - 1] == Integer.MAX_VALUE) continue;

                int slackSquared = slacks[i - 1][j - 1]* slacks[i - 1][j - 1];
                if (slackSquared + mem[i - 1] < mem[j]) {
                    mem[j] = slackSquared + mem[i - 1];
                }
            }
        }

        return getResult(n,slacks, words, mem);
    }

    /**
     * Get result given the computed memory by tracing back the recurrence equation
     * @param j the current position we are at
     * @param slacks the matrix of computed slack
     * @param words the words parse from the input
     * @param mem the computed memory
     * @return the pretty print result
     */
    private static List<String> getResult(int j, int[][] slacks, String[] words, int[] mem) {
        if (j == 0) {
            return new ArrayList<>();
        }

        int currentValue = Integer.MAX_VALUE;
        int partitionIndex = 1;
        for (int i = 1; i <= j; i++) {
            if (slacks[i - 1][j - 1] == Integer.MAX_VALUE) continue;

            int slackSquared = slacks[i - 1][j - 1]* slacks[i - 1][j - 1];
            if (slackSquared + mem[i - 1] < currentValue) {
                partitionIndex = i;
                currentValue = slackSquared + mem[i - 1];
            }
        }

        List<String> result = getResult(partitionIndex - 1, slacks, words, mem);

        StringBuilder sb = new StringBuilder();
        for (int i = partitionIndex; i < j; i++) {
            sb.append(words[i - 1]);
            sb.append(" ");
        }

        sb.append(words[j - 1]);

        result.add(sb.toString());

        return result;

    }
    
    private static int calculateSlack(int i, int j, String[] words, int maxLength) {
        int totalLength = 0;

        for (int k = i; k < j; k++) {
            totalLength += words[k].length() + 1;
        }

        totalLength += words[j].length();

        if (totalLength > maxLength) {
            return Integer.MAX_VALUE;
        }

        return maxLength - totalLength;
    }

    public static void main(String[] args) {
        InputStream in = new ByteArrayInputStream(("42\n14\nThe Answer to the Great Question of Life, " + "the Universe and Everything is Forty-two.").getBytes());
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("The Answer to the Great Question of Life,");
        expected.add("the Universe and Everything is Forty-two.");

        System.out.println(solve(in));
    }
}
