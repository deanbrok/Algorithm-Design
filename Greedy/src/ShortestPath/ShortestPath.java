package ShortestPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class ShortestPath {

    private int n;
    private int m;
    private int s;
    private int t;

    private int[] edgeTo;
    private int[] distTo;
    private boolean[] marked;

    private class Node implements Comparable<Node> {
        int id;
        int dist;

        public Node(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            if (this.dist < o.dist)       return -1;
            else if (this.dist > o.dist)  return 1;
            else                          return 0;
        }
    }

    private class Edge {
        int from;
        int to;
        int length;

        public Edge(int from, int to, int length) {
            this.from = from;
            this.to = to;
            this.length = length;
        }
    }

    private class Graph {
        List<Edge>[] nodes;

        public Graph(int n) {
            nodes = (ArrayList<Edge>[]) new ArrayList[n];
            for (int i = 0; i < n; i++) {
                nodes[i] = new ArrayList<>();
            }
        }

        public void addEdge(int from, int to, int length) {
            nodes[from].add(new Edge(from, to, length));
        }

        public List<Edge> adjTo(int v) {
            return nodes[v];
        }
    }


    // Implement the solve method to return the answer to the problem posed by the inputstream.
    public static String run(InputStream in) {
        return new ShortestPath().solve(in);
    }

    public String solve(InputStream in) {
        Graph G = parseInput(in);

        edgeTo = new int[n];
        marked = new boolean[n];
        distTo = new int[n];

        for (int i = 0; i < n; i++) {
            distTo[i] = Integer.MAX_VALUE;
        }
        distTo[s] = 0;

        dijkstra(G);

        if (!marked[t])      return String.valueOf(-1);
        else                 return String.valueOf(distTo[t]);
    }

    private void dijkstra(Graph G) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(s, 0));

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            int v = currentNode.id;

            if (marked[v]) continue;
            relax(G, v, pq);
        }
    }

    private void relax(Graph G, int v, PriorityQueue<Node> pq) {
        marked[v] = true;

        List<Edge> outgoingEdges = G.adjTo(v);

        for (Edge e: outgoingEdges) {
            int w = e.to;

            if (distTo[w] > distTo[v] + outgoingEdges.size() + e.length) {
                edgeTo[w] = v;
                distTo[w] = distTo[v] + outgoingEdges.size() + e.length;
                pq.add(new Node(w, distTo[w]));
            }
        }
    }

    private Graph parseInput(InputStream in) {
        Scanner sc = new Scanner(in);
        /*
         * We already parse the input for you and should not need to make changes to this part of the code.
         * You are free to change this input format however.
         */
        n = sc.nextInt();
        m = sc.nextInt();
        s = sc.nextInt() - 1;
        t = sc.nextInt() - 1;

        Graph G = new Graph(n);

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int cost = sc.nextInt();

            G.addEdge(u, v, cost);
        }

        return G;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./Greedy/src/ShortestPath/dataset7.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.println(run(targetStream));
    }
}
