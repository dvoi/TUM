import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// NOT SOLVED
public class C {


    // Given three collinear points p, q, r, the function checks if
    // point q lies on line segment 'pr'
    static boolean onSegment(Vector p, Vector q, Vector r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;

        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(Vector p, Vector q, Vector r) {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        int val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0; // collinear

        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    static boolean doIntersect(Vector p1, Vector q1, Vector p2, Vector q2) {

        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are collinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are collinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are collinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    static Vector lineLineIntersection(Vector A, Vector B, Vector C, Vector D) {
        // Line AB represented as a1x + b1y = c1
        int a1 = B.y - A.y;
        int b1 = A.x - B.x;
        int c1 = a1 * (A.x) + b1 * (A.y);

        // Line CD represented as a2x + b2y = c2
        int a2 = D.y - C.y;
        int b2 = C.x - D.x;
        int c2 = a2 * (C.x) + b2 * (C.y);

        int determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return new Vector(Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
        } else {
            int x = (b2 * c1 - b1 * c2) / determinant;
            int y = (a1 * c2 - a2 * c1) / determinant;
            return new Vector(x, y, 1);
        }
    }


    static class Vector {

        int x;
        int y;
        int z;

        public Vector(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "Vector{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    static class Legde {

        Vector a;
        Vector b;
        Vector segment;

        public Legde(Vector a, Vector b) {
            this.a = a;
            this.b = b;
        }

        public Legde(int[] ledge) {

            Vector a = new Vector(ledge[0], ledge[1], 1);
            Vector b = new Vector(ledge[2], ledge[3], 1);

            this.a = a;
            this.b = b;
            this.segment = normalize(crossProduct(a, b));
        }

        @Override
        public String toString() {
            return "Legde{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }

    public static Vector crossProduct(Vector a, Vector b) {

        int x = a.y * b.z - a.z * b.y;
        int y = a.z * b.x - a.x * b.z;
        int z = a.x * b.y - a.y * b.x;

        return new Vector(x, y, z);
    }

    public static Vector perpendicular(Vector l, Vector p) {
        Vector q = crossProduct(l, new Vector(0, 0, 1));
        Vector qOrt = new Vector(q.y, -(q.x), 0);
        return crossProduct(p, qOrt); // m
    }

    public static Vector projection(Vector l, Vector p) {
        Vector m = perpendicular(l, p);
        return crossProduct(m, l);
    }

    public static Vector normalize(Vector vector) {
        return new Vector(vector.x / vector.z, vector.y / vector.z, 1);
    }


    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            var nxy = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int n = nxy[0];
            int x = nxy[1];
            int y = nxy[2];

            Vector source = new Vector(x, y, 1);
            Vector bottom = new Vector(source.x, 0, 1);

            Vector rayDown = normalize(crossProduct(source, bottom));

            var ledges = new ArrayList<Legde>();
            for (int i = 0; i < n; i++) {
                var points = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                ledges.add(new Legde(points));
            }

            Vector topIntersection = null;
            Legde firstLedge = null;

            for (Legde ledge : ledges) {

                if (topIntersection == null) {

                    if (doIntersect(source, bottom, ledge.a, ledge.b)) {
                        topIntersection = lineLineIntersection(source, bottom, ledge.a, ledge.b);
                        firstLedge = ledge;
                    }

                } else {

                    if (doIntersect(source, bottom, ledge.a, ledge.b)) {

                        Vector temp = lineLineIntersection(source, bottom, ledge.a, ledge.b);

                        if (temp.y > topIntersection.y) {
                            topIntersection = temp;
                            firstLedge = ledge;
                        }
                    }
                }
            }

            System.out.println("firstLedge = " + firstLedge);
            System.out.println("topIntersection = " + topIntersection);
//            if (true) break;

            ArrayList<Vector> visited = new ArrayList<>();
            ArrayList<Vector> groundTouched = new ArrayList<>();

            if (firstLedge.a.y == firstLedge.b.y) {
                System.out.println("horizontal");
                rec(firstLedge.a, ledges, visited, groundTouched);
                rec(firstLedge.b, ledges, visited, groundTouched);

            } else if (firstLedge.a.y > firstLedge.b.y) {
                System.out.println("a higher");
                rec(firstLedge.b, ledges, visited, groundTouched);
            } else {
                System.out.println("b higher");
                rec(firstLedge.a, ledges, visited, groundTouched);
            }


            System.out.println("groundTouched = " + groundTouched);


//            for (Vector ledge : ledges) {
//                if (ledge.equals(firstLedge)) {
//                    continue;
//                }
//            }


        }

    }

    private static void rec(Vector edge, ArrayList<Legde> ledges, ArrayList<Vector> visited, ArrayList<Vector> groundTouched) {

        if (visited.contains(edge)) {
            return;
        }

        visited.add(edge);

        Vector bottom = new Vector(edge.x, 0, 1);
//        Vector rayDown = normalize(crossProduct(edge, bottom));

        ledges.removeIf(legde -> legde.a.x < edge.x && legde.b.x > edge.x && (legde.a.y > edge.y && legde.b.y > edge.y));

        Vector topIntersection = null;
        Legde firstLedge = null;
        int intersections = 0;

        for (Legde ledge : ledges) {

            if (topIntersection == null) {

                if (doIntersect(edge, bottom, ledge.a, ledge.b)) {
                    topIntersection = lineLineIntersection(edge, bottom, ledge.a, ledge.b);
                    firstLedge = ledge;
                    intersections++;
                }

            } else {

                if (doIntersect(edge, bottom, ledge.a, ledge.b)) {

                    Vector temp = lineLineIntersection(edge, bottom, ledge.a, ledge.b);

                    if (temp.y > topIntersection.y) {
                        topIntersection = temp;
                        firstLedge = ledge;
                        intersections++;
                    }
                }
            }
        }

        System.out.println("topIntersection = " + topIntersection);
        System.out.println("firstLedge = " + firstLedge);

        if (intersections == 0) {
            groundTouched.add(edge);
        } else {

            if (firstLedge.a.y == firstLedge.b.y) {
                System.out.println("horizontal");
                rec(firstLedge.a, ledges, visited, groundTouched);
                rec(firstLedge.b, ledges, visited, groundTouched);
            } else if (firstLedge.a.y > firstLedge.b.y) {
                System.out.println("a higher");
                rec(firstLedge.b, ledges, visited, groundTouched);
            } else {
                System.out.println("b higher");
                rec(firstLedge.a, ledges, visited, groundTouched);
            }

        }
    }

}
