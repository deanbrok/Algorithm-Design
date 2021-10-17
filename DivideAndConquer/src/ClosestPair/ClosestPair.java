package ClosestPair;

import java.util.*;

public class ClosestPair {

    private static class EnhancedPoint extends Point{

        private int xIndex;

        public EnhancedPoint(double x, double y, int xIndex) {
            super(x, y);
            this.xIndex = xIndex;
        }

        @Override
        public String toString() {
            return "EnhancedPoint{" +
                    "xIndex=" + xIndex +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }


    /**
     * Takes a list of points and returns the distance between the closest pair.
     * This is done with divide and conquer.
     *
     * @param points
     *     - list of points that need to be considered.
     * @return smallest pair-wise distance between points.
     */
    public static double closestPair(List<Point> points) {

        List<Point> pointsCopy = new ArrayList<>(points);
        Util.sortByX(pointsCopy);

        List<EnhancedPoint> enhancedPointsX = new ArrayList<>();
        for (int i = 0; i < pointsCopy.size(); i++) enhancedPointsX.add(new EnhancedPoint(pointsCopy.get(i).x, pointsCopy.get(i).y, i));

        List<EnhancedPoint> enhancedPointsY = new ArrayList<>(enhancedPointsX);
        Collections.sort(enhancedPointsY, Comparator.comparingDouble(p -> p.y));

        return closestPair(enhancedPointsX, enhancedPointsY);

    }

    private static double closestPair(List<EnhancedPoint> listX, List<EnhancedPoint> listY) {
        if (listX.size() <= 2) return bruteForce(listX);


        int middle = listX.size() / 2;
        int indexTo = listX.get(middle).xIndex;

        //Create Q and R
        List<EnhancedPoint> qX = listX.subList(0, middle);
        List<EnhancedPoint> qY = new ArrayList<>();
        createYList(listY, qY, indexTo, true);

        List<EnhancedPoint> rX = listX.subList(middle, listX.size());
        List<EnhancedPoint> rY = new ArrayList<>();
        createYList(listY, rY, indexTo, false);

        // Recurse into left and right halves
        double leftMin = closestPair(qX, qY);
        double rightMin = closestPair(rX, rY);

        // Combine results
        double recursionMin = Double.min(leftMin, rightMin);
        double lineL = qX.get(qX.size() - 1).x;
        List<EnhancedPoint> sY = new ArrayList<>();
        for (EnhancedPoint p: listY) {
            if (p.x >= lineL - recursionMin && p.x <= lineL + recursionMin) sY.add(p);
        }

        int sizeS = sY.size();
        double combineMin = Double.POSITIVE_INFINITY;

        for (int i = 0; i < sizeS - 1; i++) {
            EnhancedPoint currentPoint = sY.get(i);
            if (sizeS - i < 16) {
                for (int j = i + 1; j < sizeS; j++) {
                    double currentDist = Util.distance(currentPoint, sY.get(j));
                    if (currentDist < combineMin && currentDist >= 0) combineMin = currentDist;
                }
            } else {
                for (int j = i + 1; j <= i + 15; j++) {
                    double currentDist = Util.distance(currentPoint, sY.get(j));
                    if (currentDist < combineMin && currentDist >= 0) combineMin = currentDist;
                }
            }
        }

        if (combineMin < recursionMin) return combineMin;
        return recursionMin;
    }

    private static void createYList(List<EnhancedPoint> listY, List<EnhancedPoint> newList, int indexTo, boolean left) {
        if (left) {
            for (EnhancedPoint p: listY) {
                if (p.xIndex < indexTo) newList.add(p);
            }
        } else {
            for (EnhancedPoint p: listY) {
                if (p.xIndex >= indexTo) newList.add(p);
            }
        }

    }


    public static double bruteForce(List<EnhancedPoint> points) {
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


    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2, 3));
        points.add(new Point(12, 30));
        points.add(new Point(40, 50));
        points.add(new Point(5, 1));
        points.add(new Point(12, 10));
        points.add(new Point(3, 4));

        System.out.println(closestPair(points));
    }

}
