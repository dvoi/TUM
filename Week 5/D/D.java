import java.io.*;
import java.util.*;

public class D {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        long time = 0;

        for (int test = 0; test < tests; test++) {
            String[] nm = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            int maxWins = 0;
            int[] wins = new int[n];

            int[][] matches = new int[n][n];
            int[] toBePlayed = new int[n];

            String[] ws = bufferedReader.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                int w = Integer.parseInt(ws[i]);

                wins[i] = w;
                maxWins = Math.max(w, maxWins);
            }

            for (int i = 0; i < m; i++) {
                String[] match = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(match[0]) - 1;
                int b = Integer.parseInt(match[1]) - 1;

                matches[a][b] = matches[a][b] + 1;
                matches[b][a] = matches[b][a] + 1;

                toBePlayed[a] = toBePlayed[a] + 1;
                toBePlayed[b] = toBePlayed[b] + 1;
            }

            // empty line between tests
            if (test < tests - 1) {
                bufferedReader.readLine();
            }

            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < n; k++) {

                if (wins[k] + toBePlayed[k] < maxWins) {
                    sb.append("no ");
                    continue;
                }

                // Baseball Elimination
                // inspired by: https://github.com/ananya77041/baseball-elimination/blob/c426db010a55f5a24043d234d341498d7e65eb8a/src/BaseballElimination.java#L136
                int gameCombos = (n * (n - 1)) / 2;
                int size = n + gameCombos + 2;
                int[][] graph = new int[size][size];

                for (int i = 0; i < n; i++) {

                    graph[i + gameCombos + 1][size - 1] = wins[k] + toBePlayed[k] - wins[i];

                    if (i != k) {

                        for (int j = i + 1; j < n; j++) {

                            if (j != k) {

                                if (matches[i][j] > 0) {
                                    int index = (gameCombos(n, 0) - gameCombos(n, i)) + j - i;

                                    graph[0][index] = matches[i][j];

                                    graph[index][i + gameCombos + 1] = Integer.MAX_VALUE;
                                    graph[index][j + gameCombos + 1] = Integer.MAX_VALUE;

                                }
                            }
                        }
                    }
                }



                long start = 0;
                long end = 0;

                start = System.nanoTime();


                // Ford-Fulkerson with BFS, identical to A, B and C
                // https://www.programiz.com/dsa/ford-fulkerson-algorithm
                int[][] residual = new int[size][size];
                int[] parent = new int[size];
                int maxFlow = 0;

                for (int u = 0; u < size; u++) {
                    System.arraycopy(graph[u], 0, residual[u], 0, size);
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

                end = System.nanoTime();

                System.out.println("(end - start) = " + (end - start));
                time += (end - start);

                int total = 0;
                for (int v : graph[0]) {
                    total += v;
                }

                sb.append(maxFlow < total ? "no " : "yes ");
            }

            bufferedWriter.write("Case #" + (test + 1) + ": " + sb.substring(0, sb.length() - 1));
            bufferedWriter.newLine();

        }

        System.out.println("time = " + (time / 100000));


        bufferedReader.close();
        bufferedWriter.close();
    }

    private static int gameCombos(int n, int i) {
        return ((n - i) * ((n - i) - 1)) / 2;
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