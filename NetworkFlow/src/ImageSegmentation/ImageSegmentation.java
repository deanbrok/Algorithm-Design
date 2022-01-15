package ImageSegmentation;

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

public class ImageSegmentation {

    private Graph G;
    private int nPixels;
    private int nEdges;
    private int probabilitiesSum;

    public int solve(InputStream in) {
        // TODO

        parseInput(in);

        int minCost = MaxFlow.maximizeFlow(G);

        return minCost;
    }


    private void parseInput(InputStream in) {
        Scanner sc = new Scanner(in);

        nPixels = sc.nextInt();
        nEdges = sc.nextInt();

        Node startNode = new Node(0);
        Node endNode = new Node(nPixels + 1);

        List<Node> nodes = new ArrayList<>();
        nodes.add(startNode);
        for (int i = 1; i <= nPixels ; i++) {

            int id = sc.nextInt();
            int f_i = sc.nextInt();
            int b_i = sc.nextInt();

            probabilitiesSum += f_i;
            probabilitiesSum += b_i;

            nodes.add(new Node(id));
            startNode.addEdge(nodes.get(i), f_i);
            nodes.get(i).addEdge(endNode, b_i);
        }

        while (sc.hasNext()) {
            int firstNode = sc.nextInt();
            int secondNode = sc.nextInt();

            nodes.get(firstNode).addEdge(nodes.get(secondNode), 10);
            nodes.get(secondNode).addEdge(nodes.get(firstNode), 10);
        }
        sc.close();

        System.out.println(nodes);
        nodes.add(endNode);

        G = new Graph(nodes, startNode, endNode);
    }

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./NetworkFlow/src/ImageSegmentation/example.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.println(new ImageSegmentation().solve(targetStream));
    }
}
