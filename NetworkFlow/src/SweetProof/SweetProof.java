package SweetProof;

import ResidualGraph.Graph;
import ResidualGraph.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SweetProof {
    /**
     * You should implement this method
     *
     * @param n           the number of frequent users
     * @param k           the number of candy to be reviewed
     * @param connections a set of connections (x, y) representing that person x does _not_ like candy y.
     * @param candyQuantities the amount qi of each candy provided for the event (1<=i<=k) (q)
     * @param  personMinCandy the amount of candy for a person such that the review is worthwhile (d)
     * @param personMaxCandy the safe amount of candy without risking a sugar spike for a person (a)
     * @param candyMinApprovers the number of people needed to try a specific candy (c)
     * @return true, if the testing event is successful in reviewing all the candy, false otherwise.
     */
    public static boolean isCandyProofingPossible(int n, int k, Set<Connection> connections, int[] personMinCandy, int[] personMaxCandy, int[] candyMinApprovers, int[] candyQuantities) {
        // TODO your implementation
        Node s = new Node(0);
        Node t = new Node(n + k);
        t.addEdge(s, 0, Integer.MAX_VALUE);

        List<Node> consumers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            consumers.add(new Node(i));
            s.addEdge(consumers.get(i - 1), personMinCandy[i], personMaxCandy[i]);
        }

        List<Node> candies = new ArrayList<>();
        for (int i = 1; i <= k; i++) {
            candies.add(new Node(i));
            candies.get(i - 1).addEdge(t, candyMinApprovers[i], candyQuantities[i]);
        }


        for (int i = 1; i <= n; i++) {
            Node currentConsumer = consumers.get(i - 1);

            Set<Integer> dislikedCandies = new HashSet<>();
            for (Connection c: connections) {
                if (c.x() == currentConsumer.getId()) dislikedCandies.add(c.y());
            }

            for (int j = 1; j <= k; j++) {
                if (dislikedCandies.contains(j)) continue;
                currentConsumer.addEdge(candies.get(j - 1), 1);
            }
        }

        List<Node> nodes = new ArrayList<>();
        nodes.add(s);
        nodes.addAll(consumers);
        nodes.addAll(candies);
        nodes.add(t);

        Graph G = new Graph(nodes,s, t);

        return G.hasCirculation();
    }

    record Connection(int x, int y) {}
}
