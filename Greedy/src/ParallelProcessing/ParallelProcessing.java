package ParallelProcessing;

import java.util.Arrays;
import java.util.PriorityQueue;

public class ParallelProcessing {
    private static class Processor implements Comparable<Processor>{
        final int id;
        int endTime;
        public Processor (int id, int endTime) {
            this.id  = id;
            this.endTime = endTime;
        }
        @Override
        public int compareTo(Processor o) {
            if (this.endTime < o.endTime) return -1;
            else if (this.endTime > o.endTime) return 1;
            return 0;
        }
    }

    /**
     * @param n the number of jobs
     * @param m the number of processors
     * @param deadlines the deadlines of the jobs 1 through n. NB: you should ignore deadlines[0]
     * @return the minimised maximum lateness.
     */
    public static int solve(int n, int m, int[] deadlines) {
        int[] sortedDeadlines = deadlines.clone();
        Arrays.sort(sortedDeadlines);

        PriorityQueue<Processor> processors = new PriorityQueue<>();
        for (int i = 1; i <= m; i++) {
            processors.add(new Processor(i, 0));
        }
        int maxLateness = 0;

        for (int i = 1; i <= n; i++) {
            Processor currentProcessor = processors.poll();
            currentProcessor.endTime = currentProcessor.endTime + 1;
            int currentLateness = currentProcessor.endTime - sortedDeadlines[i];
            maxLateness = Integer.max(maxLateness, currentLateness);
            processors.add(currentProcessor);
        }
        return maxLateness;
    }

    public static void main(String[] args) {
        int n = 5;
        int m = 3;
        int[] deadlines = { 0, 3, 1, 1, 1, 2 };
        System.out.println(solve(n, m, deadlines));
    }
}
