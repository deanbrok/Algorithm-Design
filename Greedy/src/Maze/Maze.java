package Maze;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Maze {

    private int n;
    private int m;
    private int s;
    private int t;
    private Node[] nodes;

    private class Node {

        List<Node> outgoingEdges;

        boolean marked;

        public Node() {
            this.outgoingEdges = new ArrayList<>();
            this.marked = false;
        }
    }

    // Implement the solve method to return the answer to the problem posed by the inputstream.
    public static String run(InputStream in) {
        return new Maze().solve(in);
    }

    public String solve(InputStream in) {
        parseInput(in);
        bfs();

        if (nodes[t].marked) return "yes";
        else                 return "no";
    }

    private void bfs() {
        Queue<Node> q = new LinkedList<>();
        q.add(nodes[s]);

        while (!q.isEmpty()) {
            Node current = q.poll();
            current.marked = true;

            for (Node n: current.outgoingEdges) {
                if (!n.marked) {
                    q.add(n);
                }
            }
        }
    }

    private void parseInput(InputStream in) {
        Scanner sc = new Scanner(in);

        String[] firstLine = sc.nextLine().split("\\s");
        n = Integer.parseInt(firstLine[0]);
        m = Integer.parseInt(firstLine[1]);
        s = Integer.parseInt(firstLine[2]) - 1;
        t = Integer.parseInt(firstLine[3]) - 1;

        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }

        while (sc.hasNext()) {
            String[] line = sc.nextLine().split("\\s");
            int from = Integer.parseInt(line[0]) - 1;
            int to = Integer.parseInt(line[1]) - 1;
            nodes[from].outgoingEdges.add(nodes[to]);
        }

        sc.close();

    }

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./Greedy/src/Maze/dataset7.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.println(run(targetStream));

    }
}
