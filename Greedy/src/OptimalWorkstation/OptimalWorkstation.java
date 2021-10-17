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
        Interval[] intervals = parseIntervals(n, start, duration);
        Arrays.sort(intervals);
        PriorityQueue<Interval> workstationPQ = new PriorityQueue<>(Comparator.comparingInt(x -> x.f));

        int result = 0;

        for (Interval interval: intervals) {
            boolean found = false;

            while (!workstationPQ.isEmpty()) {
                Interval currentWorkstation = workstationPQ.peek();

                if (currentWorkstation.isCompatible(interval)) {
                    workstationPQ.poll();
                    if (currentWorkstation.isWithinLocktime(interval, m)) {
                        result++;
                        found = true;
                        workstationPQ.add(interval);
                        break;
                    } else {
                        continue;
                    }
                } else {
                    break;
                }
            }

            if (!found) {
                workstationPQ.add(interval);
            }
        }

        return result;
    }

    private static Interval[] parseIntervals(int n, int[] start, int[] duration) {
        Interval[] intervals = new Interval[n];
        for (int i = 1; i <= n; i++) {
            intervals[i - 1] = new Interval(start[i], start[i] + duration[i]);
        }
        return intervals;
    }

    private static class Interval implements Comparable<Interval> {

        int s, f;

        public Interval (int start, int finish) {
            s = start;
            f = finish;
        }

        public boolean isCompatible(Interval i) {
            return this.f <= i.s;
        }

        public boolean isWithinLocktime (Interval i, int lockTime) {
            return i.s - this.f <= lockTime;
        }

        @Override
        public int compareTo(Interval o) {
            if (this.s < o.s) return -1;
            else if (this.s > o.s) return 1;
            return 0;
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "s=" + s +
                    ", f=" + f +
                    '}';
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
