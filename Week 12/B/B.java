import java.io.*;
import java.util.Arrays;

public class B {

    static class Vector {
        double x;
        double y;
        double z;

        public Vector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vector(double[] xy) {
            this.x = xy[0];
            this.y = xy[1];
            this.z = 1;
        }
    }

    public static Vector centroid(Vector A, Vector B, Vector C) {

        Vector midAB = mid(A, B);
        Vector midAC = mid(A, C);

        Vector altitudeAB = crossProduct(midAB, C);
        Vector altitudeAC = crossProduct(midAC, B);

        Vector intersection = crossProduct(altitudeAB, altitudeAC);

        return normalize(intersection);
    }

    private static Vector orthocenter(Vector A, Vector B, Vector C) {

        Vector ab = crossProduct(A, B);
        Vector ac = crossProduct(A, C);

        Vector F = projection(ab, C);
        Vector E = projection(ac, B);

        Vector cf = crossProduct(C, F);
        Vector be = crossProduct(B, E);

        Vector intersection = crossProduct(cf, be);

        return normalize(intersection);
    }

    private static Vector circumcenter(Vector A, Vector B, Vector C) {

        Vector ab = crossProduct(A, B);
        Vector ac = crossProduct(A, C);

        Vector midAB = mid(A, B);
        Vector midAC = mid(A, C);

        Vector bisectorAB = perpendicular(ab, midAB);
        Vector bisectorAC = perpendicular(ac, midAC);

        Vector intersection = crossProduct(bisectorAB, bisectorAC);

        return normalize(intersection);
    }

    public static Vector crossProduct(Vector a, Vector b) {

        double x = a.y * b.z - a.z * b.y;
        double y = a.z * b.x - a.x * b.z;
        double z = a.x * b.y - a.y * b.x;

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

    public static Vector mid(Vector a, Vector b) {

        double x = (a.x + b.x) / 2.0;
        double y = (a.y + b.y) / 2.0;

        return new Vector(x, y, 1);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            var vertexA = Arrays.stream(bufferedReader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
            var vertexB = Arrays.stream(bufferedReader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
            var vertexC = Arrays.stream(bufferedReader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            Vector A = new Vector(vertexA);
            Vector B = new Vector(vertexB);
            Vector C = new Vector(vertexC);

            Vector centroid = centroid(A, B, C);
            Vector orthocenter = orthocenter(A, B, C);
            Vector circumcenter = circumcenter(A, B, C);

            bufferedWriter.write("Case #" + (t + 1) + ":\n");
            bufferedWriter.write(centroid.x + " " + centroid.y + "\n");
            bufferedWriter.write(orthocenter.x + " " + orthocenter.y + "\n");
            bufferedWriter.write(circumcenter.x + " " + circumcenter.y + "\n");
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
