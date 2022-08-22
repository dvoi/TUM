import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class A {

    private static class Pair {
        Integer index;
        Integer distance;

        Pair(Integer index, Integer distance) {
            this.index = index;
            this.distance = distance;
        }

        Integer getDistance() {
            return distance;
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

            int[][] graph = new int[n][n];

            for (int i = 0; i < m; i++) {
                String[] vwc = bufferedReader.readLine().split(" ");

                int v = Integer.parseInt(vwc[0]) - 1;
                int w = Integer.parseInt(vwc[1]) - 1;
                int c = Integer.parseInt(vwc[2]);

                graph[v][w] = c;
                graph[w][v] = c;
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            // Dijkstra's SSSP:
            // https://www.youtube.com/watch?v=pSqmAO-m7Lk

            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[0] = 0;

            PriorityQueue<Pair> queue = new PriorityQueue<>(Comparator.comparing(Pair::getDistance));

            for (int i = 0; i < n; i++) {
                queue.add(new Pair(i, dist[i]));
            }

            while (!queue.isEmpty()) {

                int current = queue.poll().index;

                ArrayList<Integer> neighbours = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    if (graph[current][i] > 0) {
                        neighbours.add(i);
                    }
                }

                for (int neighbour : neighbours) {
                    if (dist[current] + graph[current][neighbour] < dist[neighbour]) {
                        dist[neighbour] = dist[current] + graph[current][neighbour];
                        queue.add(new Pair(neighbour, dist[neighbour]));
                    }
                }
            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + dist[n - 1]);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
