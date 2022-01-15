package ProjectSelection;

import ImageSegmentation.ImageSegmentation;
import ResidualGraph.Edge;
import ResidualGraph.Graph;
import ResidualGraph.MaxFlow;
import ResidualGraph.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class AdvancedProjectSelection {

    private int memNum;
    private int jobNum;
    private List<Member> members;
    private List<Job> jobs;
    private int totalCost;

    public boolean solve(InputStream in) {
        parseInput(in);

        Node startNode = new Node(-1);
        Node endNode = new Node(-2);

        List<Node> memNodes = new ArrayList<>();
        List<Node> jobNodes = new ArrayList<>();

        for (int i = 0; i < memNum; i++) {
            memNodes.add(new Node(i));
            startNode.addEdge(memNodes.get(i), members.get(i).time());
        }
        for (int i = 0; i < jobNum; i++) {
            jobNodes.add(new Node(i));
            jobNodes.get(i).addEdge(endNode, jobs.get(i).timeCost());
        }

        for (Member m: members) {
            for (Job j: jobs) {
                if (m.skills().containsAll(j.skills())) {
                    memNodes.get(m.id()).addEdge(jobNodes.get(j.id()), j.timeCost());
                }
            }
        }

        List<Node> nodes = new ArrayList<>();
        nodes.add(startNode);
        nodes.addAll(memNodes);
        nodes.addAll(jobNodes);
        nodes.add(endNode);

        Graph G = new Graph(nodes, startNode, endNode);

        int totalFlow = MaxFlow.maximizeFlow(G);

        System.out.println(totalFlow);

        return totalFlow == totalCost;
    }

    private void parseInput(InputStream in) {
        Scanner sc = new Scanner(in);

        memNum = sc.nextInt();
        jobNum = sc.nextInt();


        members = new ArrayList<>();
        for (int i = 0; i < memNum; i++) {
            String name = sc.next();
            int time = sc.nextInt();
            int skillNum = sc.nextInt();

            Set<String> skills = new HashSet<>();
            for (int j = 0; j < skillNum; j++) {
                skills.add(sc.next());
            }

            members.add(new Member(i, name, time, skills));
        }

        jobs = new ArrayList<>();
        for (int i = 0; i < jobNum; i++) {
            String name = sc.next();
            int cost = sc.nextInt();
            int skillNum = sc.nextInt();

            Set<String> skills = new HashSet<>();
            for (int j = 0; j < skillNum; j++) {
                skills.add(sc.next());
            }

            totalCost += cost;
            jobs.add(new Job(i, name, cost, skills));
        }

        sc.close();
    }

    private record Member(int id, String name, int time, Set<String> skills) {}
    private record Job(int id, String name, int timeCost, Set<String> skills) {}

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./NetworkFlow/src/ProjectSelection/example.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.println(new AdvancedProjectSelection().solve(targetStream));
    }
}

