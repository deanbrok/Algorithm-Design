package FordFulkerson;

import ResidualGraph.Edge;
import ResidualGraph.Graph;
import ResidualGraph.Node;

import java.util.*;

public class FordFulkerson {


    public static int maximizeFlow(Graph g) {
        // TODO

        List<Edge> path = findPath(g);

        while (!(path == null)) {
            int bottleneck = calculateBottleneck(path);
            augment(path, bottleneck);
            path = findPath(g);
        }

        int result = 0;
        for (Edge e: g.getSource().getEdges()) {
            result += e.getFlow();
        }

        return result;
    }

    private static void augment(List<Edge> path, int bottleneck) {
        for (Edge e: path) {
            e.augmentFlow(bottleneck);
        }
    }

    private static int calculateBottleneck(List<Edge> path) {
        if (path == null || path.isEmpty()) return 0;
        int min = Integer.MAX_VALUE;
        for (Edge e: path) min = Math.min(min, e.getResidual());
        return min;
    }

    private static List<Edge> findPath(Graph g) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> seenNodes = new HashSet<>();
        Map<Node, Edge> fromEdge = new HashMap<>();

        fromEdge.put(g.getSource(), null);
        queue.add(g.getSource());
        seenNodes.add(g.getSource());

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            for (Edge e: currentNode.getEdges()) {
                if (hasFlow(e) && !seenNodes.contains(e.getTo())) {
                    queue.add(e.getTo());
                    fromEdge.put(e.getTo(), e);
                    seenNodes.add(e.getTo());
                }
            }
        }

        if (!fromEdge.containsKey(g.getSink())) return null;

        LinkedList<Edge> result = new LinkedList<>();
        Edge lastEdge = fromEdge.get(g.getSink());

        while (!(lastEdge == null)) {
            result.addFirst(lastEdge);
            lastEdge = fromEdge.get(lastEdge.getFrom());
        }

        return result;
    }

    private static boolean hasFlow(Edge e) {
        return e.getFlow() < e.getCapacity();
    }

    public static void main(String[] args) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int x = 0; x < 4; x++) nodes.add(new Node(x));
        nodes.get(0).addEdge(nodes.get(1), 10);
        nodes.get(0).addEdge(nodes.get(2), 10);
        nodes.get(1).addEdge(nodes.get(3), 10);
        nodes.get(2).addEdge(nodes.get(3), 10);
        nodes.get(1).addEdge(nodes.get(2), 2);
        Graph g = new Graph(nodes, nodes.get(0), nodes.get(3));

        System.out.println(maximizeFlow(g));
    }
}
