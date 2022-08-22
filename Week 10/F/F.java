import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class F {

    static class Point {
        int index;
        int x;
        int y;

        public Point(int index, int x, int y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "index=" + index +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    static class Edge {
        Point a;
        Point b;

        public Edge(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            var whn = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int wGrid = whn[0];
            int hGrid = whn[0];
            int n = whn[0];

            ArrayList<Edge> edges = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                var xywh = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                int x = xywh[0];
                int y = xywh[1];
                int w = xywh[2];
                int h = xywh[3];

                int id = 1;

                for (int width = 0; width < w - 1; width++) {

                    Point from = new Point(id, x + width, y);
                    Point to = new Point(++id, x + width + 1, y);

                    Edge edge = new Edge(from, to);
                    edges.add(edge);
                }


                for (int height = 0; height < h - 1; height++) {

                    Point from = new Point(id, x + w - 1, y + height);
                    Point to = new Point(++id, x + w - 1, y + height + 1);

                    Edge edge = new Edge(from, to);
                    edges.add(edge);
                }



                for (Edge edge : edges) {
                    System.out.println("edge = " + edge);
                }

            }
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
