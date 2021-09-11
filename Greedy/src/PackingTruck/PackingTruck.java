package PackingTruck;

public class PackingTruck {
    /**
     * @param n the number of packages
     * @param weights the weights of all packages 1 through n. Note that weights[0] should be ignored!
     * @param maxWeight the maximum weight a truck can carry
     * @return the minimal number of trucks required to ship the packages _in the given order_.
     */
    public static int minAmountOfTrucks(int n, int[] weights, int maxWeight) {
        if (n == 0) return 0;
        int truckN = 1;
        int currentWeight = 0;

        for(int i = 1; i <= n; i++) {
            if (currentWeight + weights[i] <= maxWeight) {
                currentWeight += weights[i];
            }
            else {
                truckN++;
                currentWeight = 0;
                currentWeight += weights[i];
            }
        }

        return truckN;
    }

    public static void main(String[] args) {
        int n = 7;
        int[] weights = { 0, 41, 29, 12, 1, 1, 26, 35, 5 };
        int maxWeight = 48;

        System.out.println(minAmountOfTrucks(n, weights, maxWeight));

    }
}
