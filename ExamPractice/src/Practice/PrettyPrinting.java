package Practice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PrettyPrinting {
    public static List<String> solve(InputStream in) {
        Scanner sc = new Scanner(in);

        int L = sc.nextInt();
        int n = sc.nextInt();

        String[] words = new String[n + 1];

        for (int i = 1; i <= n; i++) {
            words[i] = sc.next();
        }

        int[][] slacks = calculateSlacks(words, n, L);

        int[] mem = new int[n + 1];
        for (int j = 1; j <= n; j++) {
            mem[j] = Integer.MAX_VALUE;
            for (int i = 1; i <= j; i++) {
                if (slacks[i][j] == Integer.MAX_VALUE) continue;
                int slackSquared = slacks[i][j] * slacks[i][j];
                mem[j] = Math.min(mem[j], slackSquared + mem[i - 1]);
            }
        }

        return findSolution(n, mem, slacks, words);

    }

    private static LinkedList<String> findSolution(int j, int[] mem, int[][] slacks, String[] words) {
        if (j == 0) return new LinkedList<>();

        int minIndex = -1;
        int min = Integer.MAX_VALUE;

        for (int i = 1; i <= j; i++) {
            if (slacks[i][j] == Integer.MAX_VALUE) continue;
            int slackSquared = slacks[i][j] * slacks[i][j];

            if (min > slackSquared + mem[i - 1]) {
                minIndex = i;
                min = slackSquared + mem[i - 1];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = minIndex; i < j; i++) {
            sb.append(words[i]);
            sb.append(" ");
        }
        sb.append(words[j]);

        LinkedList<String> partialSol = findSolution(minIndex - 1, mem, slacks, words);

        partialSol.add(sb.toString());

        return partialSol;
    }
    private static int[][] calculateSlacks(String[] words, int n, int L) {
        int[][] slacks = new int[n + 1][n + 1];

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= j; i++) {
                slacks[i][j] = calculateSlack(words, i, j, L);
            }
        }

        return slacks;
    }

    private static int calculateSlack(String[] words, int i, int j, int L) {
        int totalCharacters = 0;

        for (int k = i; k < j; k++) {
            totalCharacters += words[k].length() + 1;
        }

        totalCharacters += words[j].length();

        if (totalCharacters > L) {
            return Integer.MAX_VALUE;
        }

        return L - totalCharacters;
    }

    public static void main(String[] args) {
        InputStream in = new ByteArrayInputStream(("42\n14\nThe Answer to the Great Question of Life, " + "the Universe and Everything is Forty-two.").getBytes());
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("The Answer to the Great Question of Life,");
        expected.add("the Universe and Everything is Forty-two.");

        System.out.println(solve(in));

    }
}
