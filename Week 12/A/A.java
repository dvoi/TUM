import java.io.*;
import java.util.Arrays;

public class A {

    static class Vector {
        double x;
        double y;
        double z;

        public Vector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    // all methods are based on the slides
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

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            var points = Arrays.stream(bufferedReader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();

            // first sword
            Vector p11 = new Vector(points[0], points[1], 1);
            Vector p12 = new Vector(points[2], points[3], 1);
            Vector p13 = new Vector(points[4], points[5], 1);

            Vector l1 = crossProduct(p11, p12); // first crossguard
            Vector r1 = projection(l1, p13); // projection onto the crossguard
            Vector blade1 = crossProduct(r1, p13);

            // second sword
            Vector p21 = new Vector(points[6], points[7], 1);
            Vector p22 = new Vector(points[8], points[9], 1);
            Vector p23 = new Vector(points[10], points[11], 1);

            Vector l2 = crossProduct(p21, p22); // second crossguard
            Vector r2 = projection(l2, p23); // projection onto the crossguard
            Vector blade2 = crossProduct(r2, p23);

            // intersection of the two blades
            Vector res = crossProduct(blade1, blade2);

            bufferedWriter.write("Case #" + (t + 1) + ": ");

            if (Math.abs(res.z) < 0.0001) {
                bufferedWriter.write("strange");
                bufferedWriter.newLine();
                continue;
            }

            res = normalize(res);

            if (res.y < Math.min(p13.y, p23.y)) {
                bufferedWriter.write("strange");
                bufferedWriter.newLine();
                continue;
            }

            bufferedWriter.write(res.x + " " + res.y);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
