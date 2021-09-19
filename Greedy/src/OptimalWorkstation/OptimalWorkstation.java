package OptimalWorkstation;

import java.util.*;

public class OptimalWorkstation {

    public static /**
     * @param n number of researchers
     * @param m number of minutes after which workstations lock themselves
     * @param start start times of jobs 1 through n. NB: you should ignore start[0]
     * @param duration duration of jobs 1 through n. NB: you should ignore duration[0]
     * @return the number of unlocks that can be saved.
     */
    int solve(int n, int m, int[] start, int[] duration) {
        Interval[] requests = new Interval[n];
        for (int i = 1; i <= n; i++) {
            requests[i - 1] = new Interval(start[i], start[i] + duration[i]);
        }

        Arrays.sort(requests);
        PriorityQueue<Interval> stations = new PriorityQueue<>(new EndComparator());

        int result = 0;

        for (Interval interval: requests) {
            while (!stations.isEmpty()) {
                if (!isCompatibleInterval(stations.peek(), interval)) break;

                Interval station = stations.poll();
                if (!isLocked(station, interval, m)) {
                    result++;
                    break;
                }
            }
            stations.add(interval);
        }

        return result;
    }

    private static boolean isCompatibleInterval(Interval i1, Interval i2) {
        return i1.end <= i2.start;
    }

    private static boolean isLocked(Interval station, Interval i, int m) {
        return i.start - station.end > m;
    }

    private static class Interval implements Comparable<Interval> {
        int start;
        int end;
        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval o) {
            if (this.start < o.start) return -1;
            else if (this.start > o.start) return 1;
            return 0;
        }
    }

    private static class EndComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            if (o1.end < o2.end) return -1;
            else if (o1.end > o2.end) return 1;
            return 0;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        int m = 10;
        int[] start = { 0, 2, 1, 17, 3, 15 };
        int[] end = { 0, 6, 2, 7, 9, 6,};
        System.out.println(solve(n, m, start, end));
    }
}
