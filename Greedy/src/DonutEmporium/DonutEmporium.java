package DonutEmporium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class DonutEmporium {
    // Implement the solve method to return the answer to the problem posed by the inputstream.
    public static String run(InputStream in) {
        return new DonutEmporium().solve(in);
    }

    public String solve(InputStream in) {
        Scanner sc = new Scanner(in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        List<House> houses = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            houses.add(new House(i, sc.nextInt(), sc.nextInt()));
        }
        int m = n * (n - 1) / 2;
        List<Distance> distances = new ArrayList<>(m);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distances.add(new Distance(houses.get(i), houses.get(j)));
            }
        }

        UnionFind unionFind = new UnionFind(houses);

        Collections.sort(distances, new distanceComparator());

        int count = 0;

        for (Distance d: distances) {
            if (count >= n - k) break;

            if (unionFind.find(d.a).id != unionFind.find(d.b).id) {
                unionFind.join(d.a, d.b);
                count++;
            }
        }

        StringBuilder result = new StringBuilder();

        for (List<House> houseList: unionFind.clusters()) {
            double xAvg = 0;
            double yAvg = 0;

            for (House h: houseList) {
                xAvg += h.x;
                yAvg += h.y;
            }

            xAvg /= houseList.size();
            yAvg /= houseList.size();

            result.append(xAvg);
            result.append(" ");
            result.append(yAvg);
            result.append("\n");
        }

        result.delete(result.lastIndexOf("\n"), result.lastIndexOf("\n") + 1);

        return result.toString();
    }

    private class distanceComparator implements Comparator<Distance> {

        @Override
        public int compare(Distance o1, Distance o2) {
            if (o1.distance < o2.distance)      return -1;
            else if (o1.distance > o2.distance) return 1;
            return 0;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./Greedy/src/DonutEmporium/example.in");
        InputStream targetStream = new FileInputStream(f);

        System.out.print(run(targetStream));

    }
}
