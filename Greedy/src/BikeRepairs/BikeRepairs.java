package BikeRepairs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class BikeRepairs {

    private int noRepairs;
    private ArrayList<Repair> labels;
    private PriorityQueue<Repair> pq;

    private class Repair implements Comparable<Repair>{
        int start;
        int end;

        public Repair(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Repair o) {
            if (this.start < o.start) return -1;
            else if (this.start > o.start) return 1;
            return 0;
        }

        public boolean isCompatible(Repair r2) {
            return this.end <= r2.start;
        }
    }

    // Implement the solve method to return the answer to the problem posed by the inputstream.
    public static String run(InputStream in) {
        return new BikeRepairs().solve(in);
    }

    public String solve(InputStream in) {
        parseInput(in);

        while (!pq.isEmpty()) {
            Repair r = pq.poll();
            boolean labelled = false;

            for (int i = 0; i < labels.size(); i++) {
                if (labels.get(i).isCompatible(r)) {
                    labels.set(i, r);
                    labelled = true;
                    break;
                }
            }

            if (!labelled) labels.add(r);
        }

        return String.valueOf(labels.size());
    }

    private void parseInput(InputStream in) {
        Scanner sc = new Scanner(in);

        noRepairs = sc.nextInt();
        pq = new PriorityQueue<>();
        labels = new ArrayList<>();

        while (sc.hasNext()) {
            int start = sc.nextInt();
            int time = sc.nextInt();
            pq.add(new Repair(start, start + time));
        }
        sc.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./Greedy/src/BikeRepairs/example.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.println(run(targetStream));
    }
}
