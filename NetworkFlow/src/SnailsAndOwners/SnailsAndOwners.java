package SnailsAndOwners;

import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SnailsAndOwners {
    /**
     * You should implement this method
     *
     * @param n           the number of nodes in X and Y (i.e. n = |X| = |Y|)
     * @param connections set of connections between one object from X and one object from Y. Objects in X and Y are labelled 1 <= label <= n
     * @return the size of the maximum matching
     */
    public static int maximumMatching(int n, Set<Connection> connections) {
        // TODO
        List<Node> nodes = new ArrayList<>();
        Node startNode = new Node(0);
        Node endNode = new Node(2*n + 1);

        List<Node> X = new ArrayList<>();
        List<Node> Y = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            X.add(new Node(i));
            startNode.addEdge(X.get(i - 1), 1);
            Y.add(new Node(i + n));
            Y.get(i - 1).addEdge(endNode, 1);
        }

        for (Connection c: connections) {
            X.get(c.x - 1).addEdge(Y.get(c.y - 1), 1);
        }

        nodes.add(startNode);
        nodes.addAll(X);
        nodes.addAll(Y);
        nodes.add(endNode);

        Graph g = new Graph(nodes, startNode, endNode);

        return MaxFlow.maximizeFlow(g);
    }
    
    private static class Connection {

        int x;

        int y;

        public Connection(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
