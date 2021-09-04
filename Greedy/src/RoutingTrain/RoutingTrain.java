package RoutingTrain;

import Maze.Maze;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class RoutingTrain {
    private int n;
    private int m;
    private int s;
    private int t;
    private Node[] nodes;
    private boolean[] onStack;

    private class Node {

        List<Node> outgoingEdges;

        int id;

        boolean marked;

        public Node(int id) {
            this.outgoingEdges = new ArrayList<>();
            this.marked = false;
            this.id = id;
        }

        public String toString() {
            return Integer.toString(id);
        }

        @Override
        public int hashCode() {
            return id;
        }
    }


    // Implement the solve method to return the answer to the problem posed by the inputstream.
    public static String run(InputStream in) {
        return new RoutingTrain().solve(in);
    }

    public String solve(InputStream in) {
        parseInput(in);
        dfsCycle(nodes[s]);

        if (nodes[t].marked) return "yes";
        else                 return "no";
    }

    private boolean dfsCycle(Node node) {
        node.marked = true;
        onStack[node.id] = true;

        for (Node n: node.outgoingEdges) {
            if (onStack[n.id])          return true;
            else if (!n.marked)         return dfsCycle(n);
        }
        onStack[node.id] = false;

        return false;
    }

    private void parseInput(InputStream in) {
        Scanner sc = new Scanner(in);

        String[] firstLine = sc.nextLine().split("\\s");
        n = Integer.parseInt(firstLine[0]);
        m = Integer.parseInt(firstLine[1]);
        s = Integer.parseInt(firstLine[2]) - 1;

        onStack = new boolean[n];
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
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
        File f = new File("./Greedy/src/RoutingTrain/example.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.println(run(targetStream));

    }
}
