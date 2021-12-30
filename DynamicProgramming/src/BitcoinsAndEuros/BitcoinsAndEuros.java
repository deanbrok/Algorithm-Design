package BitcoinsAndEuros;

public class BitcoinsAndEuros {
    /**
     * Implement this method
     *
     * @param t - the number of days you have
     * @param r - exchange rates of each day. Ignore index 0. I.e. the exchange rate of the first day can be found using r[1].
     * @return the maximum amount of euros after T days
     */
    public static double optimalTrade(int t, double[] r) {
        // TODO
        return trade(t, r, true);
    }

    private static double trade(int t, double[] r, boolean sell) {
        if (sell) {
            if (t == 0) return 0;
            return Math.max(0.95 * trade(t - 1, r, false)*r[t] , trade(t - 1, r, true));


        } else {
            if (t == 0) return 0.1;
            return Math.max((trade(t - 1, r,true) - 5) / r[t], trade(t - 1, r, false));
        }
    }

    public static void main(String[] args) {
        int t = 7;
        double[] r = { 0.0, 2010.0, 1950.0, 2020.0, 1607.5, 1904.42, 2010.0, 1904.42 };
        // Best course of action is to sell bitcoins at t = 3, buy more bitcoins at t = 4, sell at t = 5, buy more at t = 6, and again sell at t = 7

        System.out.println(optimalTrade(t, r));
    }
}
