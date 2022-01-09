package Zoombies;

import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZoombiesMeeting {

    /**
     * You should implement this method
     *
     * @param n           the number of nodes
     * @param startNodeId    the id (index) of the start node (1 <= startNodeId <= n)
     * @param endNodeId      the id (index) of the end node (1 <= endNodeId <= n)
     * @param connections set of connections between one object from X and one object from Y. Objects in X and Y are labelled 1 <= label <= n
     * @return the maximum number of disjoint paths
     */
    public static int zombieFreeRuns(int n, int startNodeId, int endNodeId, Set<Connection> connections) {
        List<Node> nodes = new ArrayList<>();
        Node source, sink;

        for (int i = 1; i <= n; i++) {
            nodes.add(new Node(i));
        }

        source = nodes.get(startNodeId - 1);
        sink = nodes.get(endNodeId - 1);

        for (Connection c: connections) {
            nodes.get(c.x - 1).addEdge(nodes.get(c.y - 1), 1);
        }

        Graph g = new Graph(nodes, source, sink);

        int doubleResult = MaxFlow.maximizeFlow(g);

        return doubleResult / 2;
    }


    private static class Connection {

        int x;

        int y;

        public Connection(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        int n = 3;
        Set<Connection> connections = new HashSet<>();
        connections.add(new Connection(1, 2));
        connections.add(new Connection(2, 3));
        System.out.println( zombieFreeRuns(n, 1, 3, connections));
    }

}

