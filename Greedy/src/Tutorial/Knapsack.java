package Tutorial;

import java.util.Arrays;

public class Knapsack {
    /**
     * Return the minimum number of items we need to get to the weight we want to get to.
     * @param n the number of item categories
     * @param w the weight we want to achieve with as few items as possible.
     * @param num the number of items in each category c_1 through c_n stored in num[1] through num[n] (NOTE: you should ignore num[0]!)
     * @param weight the weight of items in each category c_1 through c_n stored in weight[1] through weight[n] (NOTE: you should ignore weight[0]!)
     * @return minimum number of items needed to get to the required weight
     */
    public static int run(int n, int w, int[] num, int[] weight) {
        return new Knapsack().solve(n, w, num, weight);
    }

    public int solve(int n, int w, int[] num, int[] weight) {
        // TODO
        Category[] categories = new Category[n];
        for (int i = 1; i <= n; i++) {
            categories[i - 1] = new Category(num[i], weight[i]);
        }

        Arrays.sort(categories);

        int numItems = 0;
        int currentWeight = 0;

        for (Category c: categories) {
            for (int i = 0; i < c.num; i++) {
                if (currentWeight + c.weight <= w) {
                    numItems++;
                    currentWeight += c.weight;
                } else {
                    break;
                }
            }

        }


        return numItems;
    }

    private class Category implements Comparable<Category>{
        int num;
        int weight;

        public Category(int num, int weight) {
            this.num = num;
            this.weight = weight;
        }

        @Override
        public int compareTo(Category o) {
            if (this.weight < o.weight) return -1;
            if (this.weight > o.weight) return 1;
            return 0;
        }
    }
}
