package SequenceAlignment;

public class SequenceAlignment {
    public static int solve(String firstString, String secondString) {
        // TODO
        int n = firstString.length();
        int m = secondString.length();

        // Initialize memory
        int[][] M = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) M[i][0] = i;
        for (int i = 1; i <= m; i++) M[0][i] = i;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                M[i][j] = Math.min(cost(firstString.charAt(i - 1), secondString.charAt(j - 1)) + M[i-1][j-1],
                        Math.min(M[i][j-1] + 1, M[i-1][j] + 1));
            }
        }

        return M[n][m];
    }

    private static int cost(char a, char b) {
        if (a == b) return 0;
        else        return 1;
    }

    public static void main(String[] args) {
        String a = "kitten";
        String b = "sitting";
        System.out.println(solve(a, b));
    }
}
