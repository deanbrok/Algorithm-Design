package Endterm;

import java.util.*;

// 2018-2019 Final
public class MaximizeJobProfit {
    /**
     *   You should implement the method below. Note that you can use the graph structure below.
     *   @param n The number of code changes.
     *   @param benefits An array of dimension n+1 containing the benefits of all the code changes for 1 <= i <= n
     *   @param costs An array of dimension n+1 containing the costs of all the code changes for 1 <= i <= n
     *   @param dependencies is an array of dimension (n+1)*(n+1) that contains value 1 iff code change i depends on j and 0 otherwise, for all 1 <= i,j <= n.
     *   @return
     */
    public static int solve(int n, int[] benefits, int[] costs, int[][] dependencies) {
        List<Node> nodes = new ArrayList<>();

        Node s = new Node(0);
        Node t = new Node(n + 1);
        nodes.add(s);

        for (int i = 1; i <= n; i++) {
            nodes.add(new Node(i));

            int profit = benefits[i] - costs[i];
            if (profit > 0) {
                s.addEdge(nodes.get(i), profit);
            } else {
                nodes.get(i).addEdge(t, -profit);
            }
        }
        nodes.add(t);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (dependencies[i][j] == 1) {
                    nodes.get(i).addEdge(nodes.get(j), Integer.MAX_VALUE / 2);
                }
            }
        }

        Graph G = new Graph(nodes, s, t);

        G.maximizeFlow();

        int C = 0;
        int maxFlow = 0;
        for (Edge e: G.getSource().edges) {
            C += e.getCapacity();
            maxFlow += e.getFlow();
        }

        return C - maxFlow;

    }

    static class MaxFlow {

        private static List<Edge> findPath(Graph g, Node start, Node end) {
            Map<Node, Edge> mapPath = new HashMap<Node, Edge>();
            Queue<Node> sQueue = new LinkedList<Node>();
            Node currentNode = start;
            sQueue.add(currentNode);
            while (!sQueue.isEmpty() && currentNode != end) {
                currentNode = sQueue.remove();
                for (Edge e : currentNode.getEdges()) {
                    Node to = e.getTo();
                    if (to != start && mapPath.get(to) == null && e.getResidual() > 0) {
                        sQueue.add(e.getTo());
                        mapPath.put(to, e);
                    }
                }
            }
            if (sQueue.isEmpty() && currentNode != end)
                return null;
            LinkedList<Edge> path = new LinkedList<Edge>();
            Node current = end;
            while (mapPath.get(current) != null) {
                Edge e = mapPath.get(current);
                path.addFirst(e);
                current = e.getFrom();
            }
            return path;
        }

        public static void maximizeFlow(Graph g) {
            Node sink = g.getSink();
            Node source = g.getSource();
            List<Edge> path;
            while ((path = findPath(g, source, sink)) != null) {
                int r = Integer.MAX_VALUE;
                for (Edge e : path) {
                    r = Math.min(r, e.getResidual());
                }
                for (Edge e : path) {
                    e.augmentFlow(r);
                }
            }
        }
    }

    static class Graph {

        private List<Node> nodes;

        private Node source;

        private Node sink;

        public Graph(List<Node> nodes, Node source, Node sink) {
            this.nodes = nodes;
            this.source = source;
            this.sink = sink;
        }

        public Node getSink() {
            return sink;
        }

        public Node getSource() {
            return source;
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public boolean equals(Object other) {
            if (other instanceof Graph) {
                Graph that = (Graph) other;
                return this.nodes.equals(that.nodes);
            }
            return false;
        }

        public void maximizeFlow() {
            MaxFlow.maximizeFlow(this);
        }
    }

    static class Node {

        protected int id;

        protected Collection<Edge> edges;

        public Node(int id) {
            this.id = id;
            this.edges = new ArrayList<Edge>();
        }

        public void addEdge(Node to, int capacity) {
            Edge e = new Edge(capacity, this, to);
            edges.add(e);
            to.getEdges().add(e.getBackwards());
        }

        public Collection<Edge> getEdges() {
            return edges;
        }

        public int getId() {
            return id;
        }

        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node that = (Node) other;
                if (id == that.getId())
                    return edges.equals(that.getEdges());
            }
            return false;
        }
    }

    static class Edge {

        protected int capacity;

        protected int flow;

        protected Node from;

        protected Node to;

        protected Edge backwards;

        private Edge(Edge e) {
            this.flow = e.getCapacity();
            this.capacity = e.getCapacity();
            this.from = e.getTo();
            this.to = e.getFrom();
            this.backwards = e;
        }

        protected Edge(int capacity, Node from, Node to) {
            this.capacity = capacity;
            this.from = from;
            this.to = to;
            this.flow = 0;
            this.backwards = new Edge(this);
        }

        public void augmentFlow(int add) {
            assert (flow + add <= capacity);
            flow += add;
            backwards.setFlow(getResidual());
        }

        public Edge getBackwards() {
            return backwards;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getFlow() {
            return flow;
        }

        public Node getFrom() {
            return from;
        }

        public int getResidual() {
            return capacity - flow;
        }

        public Node getTo() {
            return to;
        }

        private void setFlow(int f) {
            assert (f <= capacity);
            this.flow = f;
        }

        public boolean equals(Object other) {
            if (other instanceof Edge) {
                Edge that = (Edge) other;
                return this.capacity == that.capacity && this.flow == that.flow && this.from.getId() == that.getFrom().getId() && this.to.getId() == that.getTo().getId();
            }
            return false;
        }
    }


}
