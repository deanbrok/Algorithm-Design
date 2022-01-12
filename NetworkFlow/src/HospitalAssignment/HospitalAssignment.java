package HospitalAssignment;

import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.util.ArrayList;
import java.util.List;

public class HospitalAssignment {

    public static /**
     * Checks if all patients can be assigned to the hospital given the constraints.
     *
     * @param n                 The number of patients
     * @param k                 The number of hospitals
     * @param patients          List of patient locations
     * @param hospitals         List of hospital locations
     * @param preferredDistance Preferred distance within which a patient can be assigned to a hospital.
     * @return If all patients can be assigned to at least one hospital
     */
    boolean isAssignmentPossible(int n, int k, List<Location> patients, List<Location> hospitals, int preferredDistance) {
        // Figure out connections
        List<Node> patientsG = new ArrayList<>();
        List<Node> hospitalsG = new ArrayList<>();

        Node startNode = new Node(0);
        Node endNode = new Node(n + k + 1);

        for (int i = 1; i <= n; i++) {
            Node newNode = new Node(i);
            patientsG.add(newNode);
            startNode.addEdge(newNode, 1);
        }
        for (int i = 1; i <= k; i++) {
            Node newNode = new Node(n + i);
            hospitalsG.add(newNode);
            newNode.addEdge(endNode, (int) Math.ceil((double) n / (double) k));
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                if (patients.get(i).distance(hospitals.get(j)) <= preferredDistance) patientsG.get(i).addEdge(hospitalsG.get(j), 1);
            }
        }

        List<Node> nodes = new ArrayList<>();
        nodes.add(startNode);
        nodes.addAll(patientsG);
        nodes.addAll(hospitalsG);
        nodes.add(endNode);

        Graph G = new Graph(nodes, startNode, endNode);

        return MaxFlow.maximizeFlow(G) == n;

    }

    private static class Connection {

        int x;

        int y;

        public Connection(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Location {

        private int x, y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distance(Location other) {
            return (int) Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
        }
    }
}
