package HospitalAssignment;

import ResidualGraph.Edge;
import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.util.ArrayList;
import java.util.List;

public class BloodAssignment {
    public static void main(String[] args) {
        Node s = new Node(-1);
        Node t = new Node(-2);

        List<Node> supplyNodes = new ArrayList<>();
        List<Node> patientNodes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            supplyNodes.add(new Node(i));
            patientNodes.add(new Node(i));

            supplyNodes.get(i).addEdge(patientNodes.get(i), Integer.MAX_VALUE / 2);
        }

        s.addEdge(supplyNodes.get(0), 67);
        s.addEdge(supplyNodes.get(1), 37);
        s.addEdge(supplyNodes.get(2), 5);
        s.addEdge(supplyNodes.get(3), 71);

        supplyNodes.get(0).addEdge(patientNodes.get(2), Integer.MAX_VALUE / 2);
        supplyNodes.get(1).addEdge(patientNodes.get(2), Integer.MAX_VALUE / 2);
        supplyNodes.get(3).addEdge(patientNodes.get(0), Integer.MAX_VALUE / 2);
        supplyNodes.get(3).addEdge(patientNodes.get(1), Integer.MAX_VALUE / 2);
        supplyNodes.get(3).addEdge(patientNodes.get(2), Integer.MAX_VALUE / 2);

        patientNodes.get(0).addEdge(t, 70);
        patientNodes.get(1).addEdge(t, 45);
        patientNodes.get(2).addEdge(t, 20);
        patientNodes.get(3).addEdge(t, 50);

        List<Node> nodes = new ArrayList<>();

        nodes.add(s);
        nodes.addAll(supplyNodes);
        nodes.addAll(patientNodes);
        nodes.add(t);

        Graph G = new Graph(nodes, s, t);

        System.out.println(MaxFlow.maximizeFlow(G));

        for (Edge e : G.getSource().getEdges()) {

            for (Edge middleE: e.getTo().getEdges()) {
                if (middleE.getTo().equals(s)) continue;
                System.out.println(e.getTo().getId() + " - " + middleE.getTo().getId() + ": " + middleE.getFlow());

            }

            System.out.println();
        }


    }
}
