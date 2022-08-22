import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class B {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {

            String[] nmb = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nmb[0]);
            int m = Integer.parseInt(nmb[1]);
            int b = Integer.parseInt(nmb[2]);

            int size = m + b + 2;
            var graph = new int[size][size];

            for (int i = 1; i <= m; i++) {
                graph[0][i] = 1;
            }

            for (int i = 1; i <= b; i++) {
                graph[m + i][size - 1] = 1;
            }

            for (int i = 0; i < n; i++) {
                String[] mb = bufferedReader.readLine().split(" ");

                int meal = Integer.parseInt(mb[0]);
                int beverage = Integer.parseInt(mb[1]);

                graph[meal][m + beverage] = 1;
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            // Ford-Fulkerson with BFS, identical to A
            // https://www.programiz.com/dsa/ford-fulkerson-algorithm
            int[][] residual = new int[size][size];
            int[] parent = new int[size];
            int maxFlow = 0;

            for (int u = 0; u < size; u++) {
                for (int v = 0; v < size; v++) {
                    residual[u][v] = graph[u][v];
                }
            }

            while (bfs(residual, size - 1, parent)) {

                int pathFlow = Integer.MAX_VALUE;

                for (int v = size - 1; v != 0; v = parent[v]) {
                    int u = parent[v];
                    pathFlow = Math.min(pathFlow, residual[u][v]);
                }

                for (int v = size - 1; v != 0; v = parent[v]) {
                    int u = parent[v];
                    residual[u][v] -= pathFlow;
                    residual[v][u] += pathFlow;
                }

                maxFlow += pathFlow;
            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + (maxFlow > 0 ? maxFlow : "impossible"));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static boolean bfs(int[][] residual, int sink, int[] parent) {

        Queue<Integer> queue = new LinkedList<>();

        boolean[] visited = new boolean[residual.length];
        Arrays.fill(visited, false);

        queue.add(0);
        visited[0] = true;
        parent[0] = -1;

        while (!queue.isEmpty()) {

            int current = queue.poll();

            for (int i = 0; i < residual.length; i++) {

                if (!visited[i] && residual[current][i] > 0) {

                    if (i == sink) {
                        parent[i] = current;
                        return true;
                    }

                    queue.add(i);
                    visited[i] = true;
                    parent[i] = current;
                }
            }
        }

        return false;
    }
}


