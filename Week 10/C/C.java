import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class C {

    static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
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

            double R = Double.MAX_VALUE;
            // finding a maximum radius of the first camera WITHOUT OVERLAPPING
            // => minimum distance to any of the other cameras (r = 0)
            for (int i = 1; i < n; i++) {
                R = Math.min(R, dist(points.get(0), points.get(i)));
            }

            // choose the greater between a large circle of radius R or two smaller of radius R/2
            if (n == 2) {
                double max = Math.max(findArea(R), 2.0 * findArea(R / 2.0));
                bufferedWriter.write("Case #" + (t + 1) + ": " + max);
                bufferedWriter.newLine();
                continue;
            }

            double r = R;
            // finding a maximum radius of the rest of the cameras WITHOUT OVERLAPPING
            // => minimum radius of all cameras, except for the first one (including R = 0)
            for (int i = 1; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    r = Math.min(r, dist(points.get(i), points.get(j)) / 2.0);
                }
            }

            double RDash = Double.MAX_VALUE;
            // finding an R depending on r
            for (int i = 1; i < n; i++) {
                RDash = Math.min(RDash, dist(points.get(0), points.get(i)) - r);
            }

            double a = findArea(R); // r = 0, first camera's area of coverage
            double b = (n - 1) * findArea(r) + findArea(RDash); // total area of all cameras

            bufferedWriter.write("Case #" + (t + 1) + ": " + Math.max(a, b));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    // https://www.geeksforgeeks.org/how-to-find-the-distance-between-two-points/
    public static double dist(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    // https://www.geeksforgeeks.org/c-program-find-area-circle/?ref=gcse
    public static double findArea(double r) {
        return Math.PI * Math.pow(r, 2);
    }
}
