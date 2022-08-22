import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// https://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/
public class A {

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Given three collinear points p, q, r,
    // the function checks if point q lies
    // on line segment 'pr'
    static boolean onSegment(Point p, Point q, Point r) {
        return q.x <= Math.max(p.x, r.x) &&
                q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) &&
                q.y >= Math.min(p.y, r.y);
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x)
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
        return o4 == 0 && onSegment(p2, q1, q2);

        // Doesn't fall in any of the above cases
    }

    // Returns true if the point p lies
    // inside the polygon[] with n vertices
    static boolean isInside(ArrayList<Point[]> polygon, int n, Point p) {
        // There must be at least 3 vertices in polygon[]
        if (n < 3) {
            return false;
        }

        // Create a point for line segment from p to infinite
        Point extreme = new Point(1000, p.y + 1000);

        int count = 0, i = 0;
        do {
            if (doIntersect(polygon.get(i)[0], polygon.get(i)[1], p, extreme)) {
                if (orientation(polygon.get(i)[0], p, polygon.get(i)[1]) == 0) {
                    return onSegment(polygon.get(i)[0], p, polygon.get(i)[1]);
                }

                count++;
            }
            i = (i + 1) % n;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return (count % 2 == 1); // Same as (count%2 == 1)
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            var xyn = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            Point impact = new Point(xyn[0], xyn[1]);
            int n = xyn[2];

            ArrayList<Point[]> poly = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                var side = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                int x1 = side[0];
                int y1 = side[1];
                int x2 = side[2];
                int y2 = side[3];

                poly.add(new Point[]{new Point(x1, y1), new Point(x2, y2)});
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            if (isInside(poly, n, impact)) {
                bufferedWriter.write("Case #" + (t + 1) + ": jackpot");
            } else {
                bufferedWriter.write("Case #" + (t + 1) + ": too bad");
            }
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
