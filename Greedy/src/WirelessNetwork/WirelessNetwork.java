package WirelessNetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class WirelessNetwork {

    // Implement the solve method to return the answer to the problem posed by the inputstream.
    public static String run(InputStream in) {
        return new WirelessNetwork().solve(in);
    }

    private int n;
    private int m;
    private int budget;

    private Graph G;

    private int[] edgeTo;
    private int[] costTo;
    private boolean[] marked;

    private List<Edge> MST;

    public String solve(InputStream in) {
        parseInput(in);
        prim();

        Collections.sort(MST);

        int currentCost = 0;
        int connectionsNo = 0;

        for (int i = 0; i < MST.size(); i++) {
            currentCost += MST.get(i).cost;
            if (currentCost <= budget) connectionsNo++;
        }

        return currentCost + " " + connectionsNo;
    }

    private void prim() {
        edgeTo = new int[n];
        marked = new boolean[n];
        MST = new ArrayList<>();
        costTo = new int[n];

        for (int i = 0; i < costTo.length; i++) {
            costTo[i] = Integer.MAX_VALUE;
        }
        costTo[0] = 0;

        PriorityQueue<Node> nodes = new PriorityQueue<>();
        nodes.add(new Node(0, 0));

        while (!nodes.isEmpty()) {
            Node n = nodes.poll();
            if (marked[n.id]) continue;
            marked[n.id] = true;

            if (n.id != 0) MST.add(new Edge(n.id, edgeTo[n.id], costTo[n.id]));
            relax(n.id, nodes);
        }
    }

    private void relax(int v, PriorityQueue<Node> nodes) {
        for (Edge e: G.adjTo(v)) {
            int w = e.other(v);
            if (!marked[w]) {
                if (costTo[w] > e.cost) {
                    costTo[w] = e.cost;
                    edgeTo[w] = v;
                    nodes.add(new Node(w, e.cost));
                }
            }

        }
    }

    private void parseInput(InputStream in) {
        Scanner sc = new Scanner(in);

        this.n = sc.nextInt();
        this.m = sc.nextInt();
        this.budget = sc.nextInt();

        this.G = new Graph(n);


        while (sc.hasNext()) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();

            G.addEdge(u, v, cost);
        }
    }


    private class Edge implements Comparable<Edge> {
        private final int v;
        private final int w;
        private final int cost;

        public Edge(int v, int w, int cost) {
            this.v = v;
            this.w = w;
            this.cost = cost;
        }

        public int other(int node) {
            if (node == v) return w;
            else           return v;
        }

        @Override
        public int compareTo(Edge o) {
            if (this.cost < o.cost)      return -1;
            else if (this.cost > o.cost) return 1;
            return 0;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "v=" + v +
                    ", w=" + w +
                    ", cost=" + cost +
                    '}';
        }
    }

    private class Graph {
        private final List<Edge>[] nodes;

        private Graph(int n) {
            nodes = (ArrayList<Edge>[]) new ArrayList[n];

            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new ArrayList<>();
            }
        }

        public void addEdge(int v, int w, int cost) {
            Edge e = new Edge(v, w, cost);
            nodes[v].add(e);
            nodes[w].add(e);
        }

        public Iterable<Edge> adjTo(int v) {
            return nodes[v];
        }

        @Override
        public String toString() {
            return "Graph{" +
                    "nodes=" + Arrays.toString(nodes) +
                    '}';
        }

    }

    private class Node implements Comparable<Node>{
        private final int id;
        private final int cost;

        private Node(int id, int currentCost) {
            this.id = id;
            this.cost = currentCost;
        }

        @Override
        public int compareTo(Node o) {
            if (this.cost < o.cost) return -1;
            else if (this.cost > o.cost) return 1;
            return 0;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./Greedy/src/WirelessNetwork/example.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.println(run(targetStream));
    }


}
