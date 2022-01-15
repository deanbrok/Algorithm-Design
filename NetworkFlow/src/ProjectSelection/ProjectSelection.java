package ProjectSelection;

import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectSelection {

    /**
     * You should implement this method
     *
     * @param projects List of projects, you can ignore list.get(0)
     * @return A set of feasible projects that yield the maximum possible profit.
     */
    public static int maximumProjects(List<Project> projects) {
        Node startNode = new Node(0);
        Node endNode = new Node(projects.size());

        int C = 0;
        List<Node> nodes = new ArrayList<>();

        nodes.add(startNode);

        for (int i = 1; i <= projects.size(); i++) nodes.add(new Node(i));

        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            int profit = project.getRevenue() - project.getCost();

            if (profit > 0) {
                startNode.addEdge(nodes.get(project.getId()), profit);
                C += profit;
            } else {
                nodes.get(project.getId()).addEdge(endNode, -profit);
            }

            for (Integer req: project.getRequirements()) {
                nodes.get(project.getId()).addEdge(nodes.get(req), Integer.MAX_VALUE/2);
            }
        }

        nodes.add(endNode);

        Graph G = new Graph(nodes, startNode, endNode);

        return C - MaxFlow.maximizeFlow(G);
    }




    private static class Project {

        private int id;

        private int cost;

        private int revenue;

        private List<Integer> requirements;

        public Project(int revenue, int cost) {
            this.revenue = revenue;
            this.cost = cost;
            this.requirements = new ArrayList<>();
        }

        public Project(int id, int revenue, int cost) {
            this(revenue, cost);
            this.id = id;
        }

        public void addRequirement(int requirement) {
            requirements.add(requirement);
        }

        public void addRequirements(List<Integer> requirements) {
            this.requirements.addAll(requirements);
        }

        public int getId() {
            return id;
        }

        public int getCost() {
            return cost;
        }

        public int getRevenue() {
            return revenue;
        }

        public List<Integer> getRequirements() {
            return requirements;
        }

        @Override
        public String toString() {
            return "Project{" + "id=" + id + ", cost=" + cost + ", revenue=" + revenue + ", requirements=" + requirements + '}' + "\n";
        }
    }
}
