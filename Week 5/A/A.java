import java.io.*;
import java.util.*;

public class A {

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
                String[] abw = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(abw[0]) - 1;
                int b = Integer.parseInt(abw[1]) - 1;
                int w = Integer.parseInt(abw[2]); // capacity

                graph[a][b] = graph[a][b] + w;
                graph[b][a] = graph[b][a] + w;
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            // Ford-Fulkerson with BFS
            // https://www.programiz.com/dsa/ford-fulkerson-algorithm
            int[][] residual = new int[n][n];
            int[] parent = new int[n];
            int maxFlow = 0;

            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    residual[u][v] = graph[u][v];
                }
            }

            while (bfs(residual, n - 1, parent)) {

                int pathFlow = Integer.MAX_VALUE;

                for (int v = n - 1; v != 0; v = parent[v]) {
                    int u = parent[v];
                    pathFlow = Math.min(pathFlow, residual[u][v]);
                }

                for (int v = n - 1; v != 0; v = parent[v]) {
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
