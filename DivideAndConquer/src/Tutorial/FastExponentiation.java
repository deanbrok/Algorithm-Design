package Tutorial;

public class FastExponentiation {
    /**
     * Computes the matrix C, containing the values for a^b, for all values in a and b.
     *
     * @param a array containing the bases
     * @param b array containing the exponents
     * @return matrix C
     */
    public static int[][] computeC(int[] a, int[] b) {
        // TODO
        int[][] result = new int[a.length][b.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i][j] = fastExponentiation(a[i], b[i]);
            }
        }

        return result;
    }

    private static int fastExponentiation(int a, int b) {
        if (b == 1) return a;

        if (b % 2 == 0) {
            int partialRes = fastExponentiation(a, b / 2);
            return partialRes * partialRes;
        } else {
            int partialRes = fastExponentiation(a, (b - 1) / 2);
            return partialRes * partialRes * a;
        }
    }

    public static void main(String[] args) {

        System.out.println(fastExponentiation(5, 10));
    }
}
