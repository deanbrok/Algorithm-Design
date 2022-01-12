package BipartiteMatching;

import ResidualGraph.Edge;
import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.util.*;

public class RecoverMatches {

    /**
     * Recovers the solution for maximum matches.
     *
     * @param n           the number of nodes in X and Y (i.e. n = |X| = |Y|)
     * @param connections set of connections between one object from X and one object from Y. Objects in X and Y are labelled 1 <= label <= n
     * @return solution to the maximum matches problem
     */
    public static Set<Match> recoverSolution(int n, Set<Connection> connections) {
        Graph graph = maximumMatching(n, connections);
        MaxFlow.maximizeFlow(graph);
        return recoverMatches(graph);
    }

    public static /**
     * Recovers the matches from a 1-1 bipartite matching problem
     *
     * @param graph the graph on which maximum matching algorithm was applied
     * @return a set of matches recovered
     */
    Set<Match> recoverMatches(Graph graph) {

        Set<Match> matches = new HashSet<>();

        Set<Node> startNodes = new HashSet<>();
        Set<Node> endNodes = new HashSet<>();

        for (Node node: graph.getNodes()) {
            for (Edge e: node.getEdges()) {
                if (e.getFrom().equals(graph.getSource()) && e.getFlow() == 1) startNodes.add(e.getTo());
                else if (e.getTo().equals(graph.getSink()) && e.getFlow() == 1) endNodes.add(e.getFrom());
            }
        }


        for (Node node: graph.getNodes()) {
            if (node.equals(graph.getSource()) || node.equals(graph.getSink())) continue;
            for (Edge e: node.getEdges()) {
                if (e.getTo().equals(graph.getSource()) || e.getTo().equals(graph.getSink())) continue;
                if (e.getFlow() == 1 && startNodes.contains(e.getFrom()) && endNodes.contains(e.getTo())) {
                    matches.add(new Match(e.getFrom().getId(), e.getTo().getId()));

                }
            }
        }

        return matches;
    }

    /**
     * Construct network flow graph from the set of connections
     *
     * @param n           the number of nodes in X and Y (i.e. n = |X| = |Y|)
     * @param connections set of connections between one object from X and one object from Y. Objects in X and Y are labelled 1 <= label <= n
     * @return graph representing the connections
     */
    public static Graph maximumMatching(int n, Set<Connection> connections) {
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(-1, 0);
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);
        Node[] xs = new Node[n + 1];
        Node[] ys = new Node[n + 1];
        // Create one node for every object in X, and every object in Y.
        // We recommend we put them in xs and ys for easy reference, but make sure to also put them in nodes!
        for (int i = 1; i <= n; i++) {
            xs[i] = new Node(i);
            ys[i] = new Node(i);
            source.addEdge(xs[i], 1);
            ys[i].addEdge(sink, 1);
            nodes.add(xs[i]);
            nodes.add(ys[i]);
        }
        for (Connection con : connections) {
            xs[con.x].addEdge(ys[con.y], 1);
        }
        Graph g = new Graph(nodes, source, sink);
        return g;
    }

    private static class Match {

        int x;

        int y;

        public Match(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + " - " + y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Match match = (Match) o;
            return x == match.x && y == match.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Connection {

        int x;

        int y;

        public Connection(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Connection that = (Connection) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        int n = 3;
        Set<Connection> connections = new HashSet<>();
    /* 1 can be matched with 1, 2
           2 can be matched with 3
           3 can be matched with 3
           So we can make a matching of size 2
         */
        connections.add(new Connection(1, 1));
        connections.add(new Connection(1, 2));
        connections.add(new Connection(2, 3));
        connections.add(new Connection(3, 3));

        System.out.println(  recoverSolution(n, connections));

    }

}
