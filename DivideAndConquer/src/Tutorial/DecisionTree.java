package Tutorial;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DecisionTree {
    public static int decisionTree(int d, List<Pair<Integer[], Integer>> samples) {

        Set<Integer> validIndices = new HashSet<>();

        for (int i = 0; i < samples.get(0).getL().length; i++) {
            validIndices.add(i);
        }



        return decisionTreeRec(d, samples, validIndices);
    }

    private static int decisionTreeRec(int d, List<Pair<Integer[], Integer>> samples, Set<Integer> validIndices) {
        if (d == 0) {
            int num0 = 0;
            for (Pair<Integer[], Integer> p: samples) {
                if (p.getR() == 0) {
                    num0++;
                }
            }

            return Math.max(num0, samples.size() - num0);
        }

        int bestClassification = 0;

        for (int featureIndex: validIndices) {
            List<Pair<Integer[], Integer>> samples0 = getWithFeatureValues(featureIndex, 0, samples);
            List<Pair<Integer[], Integer>> samples1 = getWithFeatureValues(featureIndex, 1, samples);

            Set<Integer> newValidIndices = new HashSet<>(validIndices);
            newValidIndices.remove(featureIndex);
            int result0 = decisionTreeRec(d - 1, samples0, newValidIndices);
            int result1 = decisionTreeRec(d - 1, samples1, newValidIndices);


            bestClassification = Math.max(bestClassification, result0 + result1);
        }

        return bestClassification;
    }

    private static List<Pair<Integer[], Integer>> getWithFeatureValues(int featureIndex, int value, List<Pair<Integer[], Integer>> samples) {
        List<Pair<Integer[], Integer>> result = new ArrayList<>();

        for (Pair<Integer[], Integer> pair: samples) {
            if (pair.getL()[featureIndex] == value) {
                result.add(pair);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Pair<Integer[], Integer>> samples = new ArrayList<>();
        samples.add(new Pair<Integer[], Integer>(new Integer[] { 0, 0, 0 }, 0));
        samples.add(new Pair<Integer[], Integer>(new Integer[] { 0, 1, 0 }, 1));
        System.out.println(decisionTree(1, samples));
    }



}








class Pair<L, R> {

    private L l;

    private R r;

    /**
     * Constructor
     * @param l left element
     * @param r right element
     */
    public Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }

    /**
     * @return the left element
     */
    public L getL() {
        return l;
    }

    /**
     * @return the right element
     */
    public R getR() {
        return r;
    }

    /**
     * @param l left element
     */
    public void setL(L l) {
        this.l = l;
    }

    /**
     * @param r right element
     */
    public void setR(R r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "l=" + l +
                ", r=" + r +
                '}';
    }
}