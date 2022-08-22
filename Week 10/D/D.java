import java.io.*;
import java.util.*;

public class D {

    static class Point {
        double x;
        double y;

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

    // https://stackoverflow.com/questions/2303278/find-if-4-points-on-a-plane-form-a-rectangle
    public static ArrayList<Point> isRectangular(Point a, Point b, Point c, Point d) {
        if (isRectangle(a, b, c, d)) {
            return new ArrayList<>(List.of(a, b, c, d));
        } else if (isRectangle(b, c, a, d)) {
            return new ArrayList<>(List.of(b, c, a, d));
        } else if (isRectangle(c, a, b, d)) {
            return new ArrayList<>(List.of(c, a, b, d));
        } else {
            return new ArrayList<>();
        }
    }

    public static boolean isRectangle(Point a, Point b, Point c, Point d) {
        return isOrthogonal(a, b, c) && isOrthogonal(b, c, d) && isOrthogonal(c, d, a);
    }

    public static boolean isOrthogonal(Point a, Point b, Point c) {
        return (b.x - a.x) * (b.x - c.x) + (b.y - a.y) * (b.y - c.y) == 0;
    }

    // https://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/
    // Given three collinear points p, q, r,
    // the function checks if point q lies
    // on line segment 'pr'
    static boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) &&
                q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) &&
                q.y >= Math.min(p.y, r.y)) {
            return true;
        }
        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x)
                - (q.x - p.x) * (r.y - q.y);

        if (val == 0) {
            return 0; // collinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // The function that returns true if
    // line segment 'p1q1' and 'p2q2' intersect.
    static boolean doIntersect(Point p1, Point q1,
                               Point p2, Point q2) {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are collinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        // p1, q1 and p2 are collinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        // p2, q2 and p1 are collinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        // p2, q2 and q1 are collinear and
        // q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }

        // Doesn't fall in any of the above cases
        return false;
    }

    // Returns true if the point p lies
    // inside the polygon[] with n vertices
    static boolean isInside(ArrayList<Point> polygon, int n, Point p) {
        // There must be at least 3 vertices in polygon[]
        if (n < 3) {
            return false;
        }

        // Create a point for line segment from p to infinite
        Point extreme = new Point(1010, p.y + 1000);

        // Count intersections of the above line
        // with sides of polygon
        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon.get(i), polygon.get(next), p, extreme)) {
                // If the point 'p' is collinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon.get(i), p, polygon.get(next)) == 0) {
                    return onSegment(polygon.get(i), p,
                            polygon.get(next));
                }

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return (count % 2 == 1); // Same as (count%2 == 1)
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        outer:
        for (int t = 0; t < tests; t++) {
            int n = Integer.parseInt(bufferedReader.readLine());

            var points = new ArrayList<Point>();

            for (int i = 0; i < n; i++) {
                var xy = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                points.add(new Point(xy[0], xy[1]));
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            ArrayList<ArrayList<Point>> rectangles = new ArrayList<>();
            for (int i = 1; i < (int) Math.pow(2, n); i++) {
                var count = Integer.toBinaryString(i).chars().filter(c -> c == '1').count();

                if (count == 4) {

                    int number = i;
                    int[] mask = new int[n];

                    for (int j = 0; j < n; j++) {
                        mask[j] = number % 2;
                        number /= 2;
                    }

                    var shape = new ArrayList<Point>();

                    for (int j = 0; j < mask.length; j++) {
                        if (mask[j] == 1) {
                            shape.add(points.get(j));
                        }
                    }

                    Point a = shape.get(0);
                    Point b = shape.get(1);
                    Point c = shape.get(2);
                    Point d = shape.get(3);

                    var rectangle = isRectangular(a, b, c, d);

                    if (!rectangle.isEmpty()) {
                        rectangles.add(rectangle);
                    }
                }

            }

            for (ArrayList<Point> rectangle : rectangles) {

                var pointsLeft = new ArrayList<>(points);
                pointsLeft.removeAll(rectangle);

                var inside = new ArrayList<Point>();
                for (Point point : pointsLeft) {
                    if (isInside(rectangle, 4, point)) {
                        inside.add(point);
                    }
                }
                pointsLeft.removeAll(inside);

                here:
                for (int i = 1; i < (int) Math.pow(2, pointsLeft.size()); i++) {
                    var count = Integer.toBinaryString(i).chars().filter(c -> c == '1').count();

                    if (count == 3) {

                        int number = i;
                        int[] mask = new int[pointsLeft.size()];

                        for (int j = 0; j < pointsLeft.size(); j++) {
                            mask[j] = number % 2;
                            number /= 2;
                        }

                        var triangle = new ArrayList<Point>();

                        for (int j = 0; j < mask.length; j++) {
                            if (mask[j] == 1) {
                                triangle.add(pointsLeft.get(j));
                            }
                        }

                        for (Point point : rectangle) {
                            if (isInside(triangle, 3, point)) {
                                continue here;
                            }
                        }

                        for (Point point : triangle) {
                            if (isInside(rectangle, 4, point)) {
                                continue here;
                            }
                        }


                        for (int k = 0; k < 4; k++) {
                            Point a = rectangle.get(k);
                            Point b = rectangle.get((k + 1) % 4);

                            for (int l = 0; l < 3; l++) {
                                Point c = triangle.get(l);
                                Point d = triangle.get((l + 1) % 3);

                                if (doIntersect(a, b, c, d)) {
                                    continue here;
                                }
                            }
                        }

                        rectangle.addAll(triangle);

                        bufferedWriter.write("Case #" + (t + 1) + ": possible\n");
                        for (Point point : rectangle) {
                            bufferedWriter.write((int) point.x + " " + (int) point.y);
                            bufferedWriter.newLine();
                        }

                        continue outer;
                    }
                }
            }

            bufferedWriter.write("Case #" + (t + 1) + ": impossible\n");
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
