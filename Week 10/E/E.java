import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class E {

    static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // https://www.geeksforgeeks.org/find-the-centroid-of-a-non-self-intersecting-closed-polygon/
    public static Point find_Centroid(ArrayList<Point> points) {

        double[] ans = new double[2];

        int n = points.size();
        double signedArea = 0;

        // For all vertices
        for (int i = 0; i < n; i++) {

            int m = (i + 1) % n;

            double x0 = points.get(i).x, y0 = points.get(i).y;
            double x1 = points.get(m).x, y1 = points.get(m).y;

            // Calculate value of A
            // using shoelace formula
            double A = (x0 * y1) - (x1 * y0);
            signedArea += A;

            // Calculating coordinates of
            // centroid of polygon
            ans[0] += (x0 + x1) * A;
            ans[1] += (y0 + y1) * A;
        }

        signedArea *= 0.5;
        ans[0] = (ans[0]) / (6 * signedArea);
        ans[1] = (ans[1]) / (6 * signedArea);

        return new Point(ans[0], ans[1]);
    }

    // http://www.sunshine2k.de/coding/java/PointOnLine/PointOnLine.html
    public static boolean isProjectedPointOnLineSegment(Point v1, Point v2, Point p) {
        Point e1 = new Point(v2.x - v1.x, v2.y - v1.y);
        double recArea = dotProduct(e1, e1);

        Point e2 = new Point(p.x - v1.x, p.y - v1.y);
        double val = dotProduct(e1, e2);

        return (0 < val && val < recArea);
    }

    public static double dotProduct(Point a, Point b) {
        return a.x * b.x + a.y * b.y;
    }

    // from the slides
    static double ccw(Point a, Point b, Point p) {
        return (p.y - a.y) * (b.x - a.x) - (p.x - a.x) * (b.y - a.y);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            int n = Integer.parseInt(bufferedReader.readLine());

            var points = new ArrayList<Point>();

            for (int i = 0; i < n; i++) {
                var xy = Arrays.stream(bufferedReader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                points.add(new Point(xy[0], xy[1]));
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            Point com = find_Centroid(points);
            int stableSides = 0;

            outer:
            for (int i = 0; i < n; i++) {

                int m = (i + 1) % n;

                Point a = points.get(i);
                Point b = points.get(m); // to include the "closing" side between the last and the first point

                // check for orthogonal projection of the center of mass to lay on the current side
                if (!isProjectedPointOnLineSegment(a, b, com)) {
                    continue;
                }

                double prevSide = 0;
                for (int p = 0; p < n; p++) {

                    // rest of the points
                    if (p != i && p != m) {

                        double ccw = ccw(a, b, points.get(p));

                        // point p is collinear, which is forbidden
                        if (ccw == 0) {
                            continue outer;
                        }

                        // side is not yet defined, so set with the value of the first point
                        if (prevSide == 0) {
                            prevSide = ccw;
                        } else {
                            // check the current point against the last
                            // different signs <=> different sides
                            if (Math.signum(prevSide) != Math.signum(ccw)) {
                                continue outer;
                            }
                        }
                    }
                }

                stableSides++;
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + stableSides);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
