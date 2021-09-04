package BetterAverage;

import java.util.Random;

public class BetterAverage {
    public static double solve(int n, double[] list) {

        shuffleArray(list);
        quicksort3way(list, 0, n - 1);

        if (n % 2 == 1) {
            int mIndex = n / 2;
            return list[mIndex];
        } else {
            return (list[n/2] + list[n/2 - 1]) / 2;
        }
    }


    private static void quicksort3way(double[] list, int lo, int hi) {
        if (lo >= hi) return;

        int[] loHi = partition3way(list, lo, hi);

        quicksort3way(list, lo, loHi[0]);
        quicksort3way(list, loHi[1], hi);
    }


    private static int[] partition3way(double[] list, int lo, int hi) {
        int i = lo;
        int j = lo - 1;
        int k = hi + 1;

        double v = list[lo];

        while (i < k) {
            if (list[i] < v)            swap(i++, ++j, list);
            else if (list[i] == v)      i++;
            else if (list[i] > v)       swap(i, --k, list);
        }

        return new int[] {j, k};

    }

    private static void swap(int i, int j, double[] list) {
        double temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    private static void shuffleArray(double[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(i, j, array);
        }
    }



}
