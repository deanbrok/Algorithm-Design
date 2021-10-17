package ClosestPair;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Point {

    public double x;

    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

/**
 * Useful methods for this assignment.
 */
class Util {

    /**
     * Takes two points and computes the euclidean distance between the two points.
     *
     * @param point1 - first point.
     * @param point2 - second point.
     * @return euclidean distance between the two points.
     * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">https://en.wikipedia.org/wiki/Euclidean_distance</a>
     */
    public static double distance(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2.0D) + Math.pow(point1.y - point2.y, 2.0D));
    }

    /**
     * Takes a list of points and sorts it on x (ascending).
     *
     * @param points - points that need to be sorted.
     */
    public static void sortByX(List<Point> points) {
        Collections.sort(points, Comparator.comparingDouble(point -> point.x));
    }

    /**
     * Takes a list of points and sorts it on y (ascending) .
     *
     * @param points - points that need to be sorted.
     */
    public static void sortByY(List<Point> points) {
        Collections.sort(points, Comparator.comparingDouble(point -> point.y));
    }

    /**
     * Takes a list of points and returns the distance between the closest pair.
     * This is done by brute forcing.
     *
     * @param points - list of points that need to be considered.
     * @return smallest pair-wise distance between points.
     */
    public static double bruteForce(List<Point> points) {
        int size = points.size();
        if (size <= 1)
            return Double.POSITIVE_INFINITY;
        double bestDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < size - 1; i++) {
            Point point1 = points.get(i);
            for (int j = i + 1; j < size; j++) {
                Point point2 = points.get(j);
                double distance = Util.distance(point1, point2);
                if (distance < bestDist)
                    bestDist = distance;
            }
        }
        return bestDist;
    }
}