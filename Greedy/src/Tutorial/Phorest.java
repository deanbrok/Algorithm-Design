package Tutorial;

import java.util.*;

public class Phorest {

    private static class Edge {

        // from, to and length
        int x, y, l;

        public Edge(int from, int to, int length) {
            x = from;
            y = to;
            l = length;
        }
    }

    private static class UF {
        int[] ids;
        int[] size;


        public UF(int n) {
            this.ids = new int[n];
            this.size = new int[n];

            for (int i = 0; i < n; i++) {
                ids[i] = i;
            }
        }

        public int root(int i) {
            while (ids[i] != i) {
                ids[i] = ids[ids[i]];
                i = ids[i];
            }
            return i;
        }

        public boolean union(int i1, int i2) {
            int id1 = root(i1);
            int id2 = root(i2);

            if (id1 == id2) return false;

            if (size[id1] > size[id2]) {
                ids[id2] = id1;
                size[id1] += size[id2];
            } else {
                ids[id1] = id2;
                size[id2] += size[id1];
            }

            return true;
        }
    }

    /**
     * Optimizes the provided Phorest to be as close to an MST as possible.
     * @param n the number of nodes in the network
     * @param g all edges in the full graph
     * @param p the edges in the Phorest
     * @return total edge weight of optimized Phorest
     */
    public static String run(int n, Edge[] g, Edge[] p) {
        return new Phorest().solve(n, g, p);
    }

    public String solve(int nodes, Edge[] graph, Edge[] phorest) {
        UF uf = new UF(nodes);

        List<Edge> optimizedPhorest = Arrays.asList(phorest);

        int result = 0;
        for (Edge e: phorest) {
            uf.union(e.x, e.y);
            result += e.l;
        }

        Arrays.sort(graph, Comparator.comparingInt(x -> x.l));

        for (Edge e: graph) {
            if (uf.union(e.x, e.y)) {
                optimizedPhorest.add(e);
                result += e.l;
            }
        }

        return String.valueOf(result);
    }

    static Edge[] makeGraph(Scanner sc) {
        int m = sc.nextInt();
        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
        Arrays.sort(edges, Comparator.comparingInt(e -> e.l));
        return edges;
    }

}

