import java.io.*;
import java.util.*;

public class E {

    private static class Pair {
        Integer source;
        Integer dest;
        Integer dist;
        boolean used;

        public Pair(Integer source, Integer dest, Integer dist, boolean used) {
            this.source = source;
            this.dest = dest;
            this.dist = dist;
            this.used = used;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {
            String[] nmk = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nmk[0]);
            int m = Integer.parseInt(nmk[1]);
            int k = Integer.parseInt(nmk[2]);

            HashMap<Integer, ArrayList<Pair>> ways = new HashMap<>();
            ArrayList<Integer> dailyPath = new ArrayList<>();

            String[] path = bufferedReader.readLine().split(" ");
            for (int i = 0; i < k; i++) {
                dailyPath.add(Integer.parseInt(path[i]) - 1);
            }

            for (int i = 0; i < m; i++) {
                String[] abc = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(abc[0]) - 1;
                int b = Integer.parseInt(abc[1]) - 1;
                int c = Integer.parseInt(abc[2]);

                if (!ways.containsKey(a)) {
                    ways.put(a, new ArrayList<>());
                }
                ways.get(a).add(new Pair(a, b, c, false));

                if (!ways.containsKey(b)) {
                    ways.put(b, new ArrayList<>());
                }
                ways.get(b).add(new Pair(b, a, c, false));
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            // Dijkstra's
            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE);

            boolean[] visited = new boolean[n];
            Arrays.fill(visited, false);

            ArrayList<ArrayList<Integer>> predecessors = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                predecessors.add(new ArrayList<>());
            }

            dist[0] = 0;
            visited[0] = true;
            predecessors.get(0).add(0);

            PriorityQueue<Pair> queue = new PriorityQueue<>(Comparator.comparing(pair -> pair.dist));

            for (Pair pair : ways.get(0)) {
                queue.add(pair);
            }

            while (!queue.isEmpty()) {

                Pair current = queue.poll();
                int dest = current.dest;
                int source = current.source;

                if (!visited[dest]) {

                    dist[dest] = current.dist;
                    visited[dest] = true;
                    predecessors.get(dest).add(source);

                    for (Pair pair : ways.get(dest)) {
                        if (!pair.used) {
                            int newDist = dist[dest] + pair.dist;
                            queue.add(new Pair(pair.source, pair.dest, newDist, false));
                        }
                    }

                } else {

                    if (current.dist == dist[dest]) {

                        predecessors.get(dest).add(source);

                        for (Pair pair : ways.get(dest)) {
                            if (!pair.used) {
                                pair.used = true;
                                int newDist = dist[dest] + pair.dist;
                                queue.add(new Pair(pair.source, pair.dest, newDist, true));
                            }
                        }
                    }
                }

            }

            String res = "";

            for (int junction : dailyPath) {
                if (predecessors.get(junction).size() > 1) {
                    res = "yes";
                    break;
                }
            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + (res.isEmpty() ? "no" : res));
            bufferedWriter.newLine();

        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
