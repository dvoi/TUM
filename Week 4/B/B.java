import java.io.*;
import java.util.*;

public class B {

    static class Edge {
        int source;
        int dest;
        double rate;

        public Edge(int source, int dest, double rate) {
            this.source = source;
            this.dest = dest;
            this.rate = rate;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {
            String[] nm = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            ArrayList<Edge> edges = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                String[] abc = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(abc[0]) - 1;
                int b = Integer.parseInt(abc[1]) - 1;
                double c = Double.parseDouble(abc[2]);

                edges.add(new Edge(a, b, c));
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            if (n == 1) {
                bufferedWriter.write("Case #" + (j + 1) + ": " + 1.0);
                bufferedWriter.newLine();
                continue;
            }

            // Bellman Ford using Edge list:
            // https://github.com/williamfiset/Algorithms/blob/master/src/main/java/com/williamfiset/algorithms/graphtheory/BellmanFordEdgeList.java

            double[] logRates = new double[n];
            Arrays.fill(logRates, Double.MAX_VALUE);
            logRates[0] = 0;

            // relax edges n - 1 times
            for (int i = 0; i < n - 1; i++) {

                for (Edge edge : edges) {

                    double logDist = logRates[edge.source] + Math.log(edge.rate);

                    if (logDist < logRates[edge.dest]) {
                        logRates[edge.dest] = logDist;
                    }
                }

            }

            StringBuilder sb = new StringBuilder();

            for (Edge edge : edges) {
                double logDist = logRates[edge.source] + Math.log(edge.rate);
                if (logDist < logRates[edge.dest]) {
                    sb.append("Jackpot");
                    break;
                }
            }

            if (sb.toString().isEmpty()) {

                if (logRates[n - 1] != 0) {
                    double res = Math.exp(logRates[n - 1]);

                    sb.append(res != Double.POSITIVE_INFINITY ? res : "impossible");
                }
            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + sb);
            bufferedWriter.newLine();

        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
