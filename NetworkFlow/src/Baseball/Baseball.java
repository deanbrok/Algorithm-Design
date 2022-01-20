package Baseball;

import ProjectSelection.AdvancedProjectSelection;
import ResidualGraph.Edge;
import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Baseball {
    /**
     * Returns true if team x can still win the Cup.
     */
    public static boolean solve(InputStream in) {
        // TODO
        List<Team> teams = new ArrayList<>();
        int m = parseInput(in, teams);

        // Team we want to determine if eliminated or not
        Team x = teams.get(0);
        int maxWins = x.wins + x.gamesLeft;

        // Keep track of all nodes
        Node[] teamNodes = new Node[m + 1];
        boolean[][] existEdge = new boolean[m + 1][m + 1];

        List<Node> allNodes = new ArrayList<>();

        Node startNode = new Node(-1);
        Node endNode = new Node(-2);

        for (int i = 1; i < m; i++) {
            Team currentTeam = teams.get(i);

            Node teamNode = new Node(currentTeam.id());
            teamNodes[currentTeam.id()] = teamNode;

            teamNode.addEdge(endNode, maxWins - currentTeam.wins());
            allNodes.add(teamNode);
        }

        for (int i = 1; i < m; i++) {
            Team currentTeam = teams.get(i);

            for (int opponent: currentTeam.opponents()) {
                if (opponent != x.id() && !existEdge[currentTeam.id()][opponent]) {
                    existEdge[currentTeam.id()][opponent] = true;
                    existEdge[opponent][currentTeam.id()] = true;

                    Node gameNode = new Node(currentTeam.id() + opponent);

                    startNode.addEdge(gameNode, 1);

                    gameNode.addEdge(teamNodes[currentTeam.id()], Integer.MAX_VALUE / 2);
                    gameNode.addEdge(teamNodes[opponent], Integer.MAX_VALUE / 2);

                    allNodes.add(gameNode);
                }
            }
        }

        allNodes.add(startNode);
        allNodes.add(endNode);

        Graph G = new Graph(allNodes, startNode, endNode);

        MaxFlow.maximizeFlow(G);
        for (Edge e: G.getSource().getEdges()) {
            if (e.getFlow() < e.getCapacity()) return false;
        }

        return true;
    }

    private static int parseInput(InputStream in, List<Team> teams) {
        Scanner sc = new Scanner(in);

        int m = sc.nextInt();


        for (int i = 0; i < m; i++) {
            int id = sc.nextInt();
            int wins = sc.nextInt();
            int gamesLeft = sc.nextInt();
            int[] opponents = new int[gamesLeft];

            for (int j = 0; j < gamesLeft; j++) {
                opponents[j] = sc.nextInt();
            }

            teams.add(new Team(id, wins, gamesLeft, opponents, i));
        }

        return m;
    }

    private record Team(int id, int wins, int gamesLeft, int[] opponents, int positionInArray) {}

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./NetworkFlow/src/Baseball/example.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.println(solve(targetStream));
    }
}
