import java.io.*;
import java.util.*;

public class C {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {
            String[] nmsab = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nmsab[0]); // cities
            int m = Integer.parseInt(nmsab[1]); // roads
            int s = Integer.parseInt(nmsab[2]); // stores

            int a = Integer.parseInt(nmsab[3]) - 1; // Lea
            int b = Integer.parseInt(nmsab[4]) - 1; // Peter

            int[][] graph = new int[n][n];

            HashMap<Integer, Integer> markets = new HashMap<>();

            for (int i = 0; i < m; i++) {
                String[] xyz = bufferedReader.readLine().split(" ");

                int x = Integer.parseInt(xyz[0]) - 1;
                int y = Integer.parseInt(xyz[1]) - 1;
                int z = Integer.parseInt(xyz[2]);

                if (x != y) {
                    if (graph[x][y] != 0) {
                        if (z < graph[x][y]) {
                            graph[x][y] = z;
                            graph[y][x] = z;
                        }
                    } else {
                        graph[x][y] = z;
                        graph[y][x] = z;
                    }
                }
            }

            for (int i = 0; i < s; i++) {
                String[] cw = bufferedReader.readLine().split(" ");

                int c = Integer.parseInt(cw[0]) - 1;
                int w = Integer.parseInt(cw[1]);

                if (markets.isEmpty() || !markets.containsKey(c)) {
                    markets.put(c, w);
                } else {
                    if (markets.containsKey(c)) {
                        if (w < markets.get(c)) {
                            markets.put(c, w);
                        }
                    }
                }
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            int[] distFromLea = shortestPaths(graph, n, a);
            int[] distFromPeter = shortestPaths(graph, n, b);

            int minDist = Integer.MAX_VALUE;

            for (Integer currentStore : markets.keySet()) {

                if (distFromLea[currentStore] != Integer.MAX_VALUE && distFromPeter[currentStore] != Integer.MAX_VALUE) {
                    minDist = Math.min(minDist, distFromLea[currentStore] + distFromPeter[currentStore] + markets.get(currentStore));
                }

            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + (minDist != Integer.MAX_VALUE ? format(minDist) : "impossible"));
            bufferedWriter.newLine();

        }

        bufferedReader.close();
        bufferedWriter.close();

    }

    private static int[] shortestPaths(int[][] graph, int n, int start) {

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparing(node -> dist[node]));

        dist[start] = 0;
        queue.add(start);

        while (!queue.isEmpty()) {

            int current = queue.poll();

            ArrayList<Integer> neighbours = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if (graph[current][i] > 0) {
                    neighbours.add(i);
                }
            }

            for (int neighbour : neighbours) {
                if (dist[current] + graph[current][neighbour] < dist[neighbour]) {
                    dist[neighbour] = dist[current] + graph[current][neighbour];
                    queue.add(neighbour);
                }
            }
        }

        return dist;
    }

    private static String format(int minutes) {
        int hours = minutes / 60;
        minutes -= hours * 60;
        return hours + ":" + (minutes < 10 ? "0" + minutes : minutes);
    }

}
