package Tutorial;

import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.util.ArrayList;
import java.util.List;

public class Aeronauts {
    /**
     * @param l          the number of flights Lee has already done
     * @param n          the number of competitors
     * @param m          the number of open slots left
     * @param flights    the number of flights each of the competitors has done. You should use flights[1] to flights[n]
     * @param compatible 2D boolean array such that slot i can be used by competitor j iff compatible[i][j] is true. Note that compatible[0][x] and compatible[x][0] should not be used.
     * @return
     */
    public static boolean solve(int l, int n, int m, int[] flights, boolean[][] compatible) {
        Node s = new Node(-1);
        Node t = new Node(-2);

        List<Node> slotNodes = new ArrayList<>();
        for (int i = 1; i <= m; i++) {
            slotNodes.add(new Node(i));
            s.addEdge(slotNodes.get(i - 1), 1);
        }

        List<Node> compNodes = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            compNodes.add(new Node(i));
            compNodes.get(i - 1).addEdge(t, l - flights[i] - 1);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (compatible[i][j]) {
                    slotNodes.get(i - 1).addEdge(compNodes.get(j - 1), Integer.MAX_VALUE / 2);
                }
            }
        }

        List<Node> nodes = new ArrayList<>();
        nodes.add(s);
        nodes.addAll(slotNodes);
        nodes.addAll(compNodes);
        nodes.add(t);

        int maxFlow = MaxFlow.maximizeFlow(new Graph(nodes,s,t));
        return maxFlow == m;
    }
}
