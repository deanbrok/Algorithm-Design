package SmallerBetter;

public class SmallerBetter {

    /**
     * Finds the x coordinate with the smallest distance between two linear equations, by recursively evaluating the median of the range and that integer + 1.
     * Depending on the value, a new evaluation is made with a smaller range to find the x coordinate with the smallest distance.
     * @param e1 the first equation to evaluate
     * @param e2 the second equation to evaluate
     * @param low the lower boundary of the range
     * @param high the upper boundary of the range
     * @return the x coordinate with the minimum difference between e1 and e2
     */
    public static long findMin(Equation e1, Equation e2, long low, long high) {
        // TODO

        if (low >= high) return low;
        long median = low + (high - low) / 2;

        long e1Median = e1.evaluate(median);
        long e2Median = e2.evaluate(median);

        long e1Median1 = e1.evaluate(median + 1);
        long e2Median1 = e2.evaluate(median + 1);

        if (Math.abs(e1Median - e2Median) < Math.abs(e1Median1 - e2Median1)) {
            return findMin(e1, e2, low, median);
        } else {
            return findMin(e1, e2, median + 1, high);
        }
    }

    public static void main(String[] args) {
        Equation eq1 = new QuadraticEquation(0, 8, -240);
        Equation eq2 = new QuadraticEquation(0, 6, -156);
        assert 42 == SmallerBetter.findMin(eq1, eq2, 0, 100);

        System.out.println(SmallerBetter.findMin(eq1, eq2, 0, 100));
    }
}
