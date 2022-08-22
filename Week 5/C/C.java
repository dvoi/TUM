import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class C {


    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {
            String[] nm = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            int size = 2 * n - 2;
            int[][] graph = new int[size][size];

            for (int i = 1; i < n - 1; i++) {
                int p = i * 2 - 1;

                graph[p][p + 1] = 1;
                graph[p + 1][p] = 1;
            }

            for (int i = 0; i < m; i++) {
                String[] ab = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(ab[0]) - 1;
                int b = Integer.parseInt(ab[1]) - 1;

                if (a != 0) {
                    a = a * 2 - 1;
                }

                if (b != 0) {
                    b = b * 2 - 1;
                }

                graph[a == 0 || a == size - 1 ? a : a + 1][b] = 1;
                graph[b == 0 || b == size - 1 ? b : b + 1][a] = 1;
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            // Ford-Fulkerson with BFS, identical to A, B and C
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
