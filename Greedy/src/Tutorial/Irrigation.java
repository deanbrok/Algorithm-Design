package Tutorial;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Irrigation {

    // Implement the solve method to return the answer to the problem posed by the inputstream.
    public static String run(InputStream in) {
        return new Irrigation().solve(in);
    }

    public String solve(InputStream in) {
        Scanner sc = new Scanner(in);
        /*
         * We already parse the input for you and should not need to make changes to this part of the code.
         * You are free to change this input format however.
         */
        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt();
        ArrayList<HashMap<Integer, Integer>> nodes = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nodes.add(new HashMap<>());
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            nodes.get(u).put(v, cost);
            nodes.get(v).put(u, cost);
        }
        if (n <= 1) {
            return "0";
        }
        return solve(nodes, s);
    }

    public String solve(ArrayList<HashMap<Integer, Integer>> nodes, int s ) {
        boolean[] visited = new boolean[nodes.size()];
        int[] distances = new int[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            distances[i] = Integer.MAX_VALUE / 2;
        }
        PriorityQueue<Tuple> q = new PriorityQueue<>();
        q.add(new Tuple(s, 0));
        distances[s] = 0;
        int c = 0;
        while (!q.isEmpty()) {
            Tuple curTuple = q.poll();
            if (visited[curTuple.id]) {
                continue;
            }
            visited[curTuple.id] = true;
            c += curTuple.cost;
            for (int neighbour : nodes.get(curTuple.id).keySet()) {
                int neighbourCost = nodes.get(curTuple.id).get(neighbour);
                if (distances[neighbour] > neighbourCost) {
                    q.add(new Tuple(neighbour, neighbourCost));
                }
            }
        }
        return Integer.toString(c);
    }

    class Tuple implements Comparable<Tuple> {

        int id;

        int cost;

        Tuple(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Tuple tuple = (Tuple) o;
            return id == tuple.id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public int compareTo(Tuple o) {
            int res = Integer.signum(this.cost - o.cost);
            if (res == 0) {
                return Integer.signum(this.id - o.id);
            }
            return res;
        }
    }
}
