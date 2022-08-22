import java.io.*;
import java.util.*;

public class E {


    private static int b = 0;

    static class Edge {
        int from;
        int to;
        int index;
        int capacity;
        int flow;

        public Edge(int from, int to, int capacity, int flow, int index) {
            this.from = from;
            this.to = to;
            this.index = index;
            this.capacity = capacity;
            this.flow = flow;
        }
    }


    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int test = 0; test < tests; test++) {
            String[] nkd = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nkd[0]); // territory n*n
            int k = Integer.parseInt(nkd[1]); // population of goat riders
            int d = Integer.parseInt(nkd[2]); // nights to survive

            int[][] grid = new int[n][n];
            int[][] initialPositions = new int[k][2]; // initial positions of k goat riders
            int[] levelsAtEachNight = new int[d]; // level on the morning of day j [0..d]

            for (int i = 0; i < n; i++) {
                String[] heights = bufferedReader.readLine().split(" ");

                for (int j = 0; j < n; j++) {
                    grid[i][j] = Integer.parseInt(heights[j]);
                }
            }

            for (int i = 0; i < k; i++) {
                String[] rc = bufferedReader.readLine().split(" ");

                int r = Integer.parseInt(rc[0]) - 1;
                int c = Integer.parseInt(rc[1]) - 1;

                initialPositions[i] = new int[]{r, c};
            }

            for (int j = 0; j < d; j++) {
                levelsAtEachNight[j] = Integer.parseInt(bufferedReader.readLine());
            }

            // empty line between tests
            if (test < tests - 1) {
                bufferedReader.readLine();
            }


            // through pain and tears implemented
            // Push-Relabel with O(V^2 E^{1/2}) vs previous Dinic's with O(V^2 E)
            // https://codeforces.com/blog/entry/14378
            int size = 2 + 2 * (n * n) * (d + 1);

            int source = size - 2;
            int sink = size - 1;

            List<Edge>[] adj = new List[size];
            List<Integer>[] B = new List[size];

            for (int i = 0; i < size; i++) {
                adj[i] = new ArrayList<>();
                B[i] = new ArrayList<>();
            }

//            List<Edge>[] graph = new List[size];
//            for (int i = 0; i < size; i++) {
//                graph[i] = new ArrayList<>();
//            }


            for (int[] p : initialPositions) {
//                addEdge(graph, source, index(p[0], p[1], 0, false, n, d), 1);
                addEdge(adj, source, index(p[0], p[1], 0, false, n, d), 1);
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    addEdge(adj, index(i, j, d, true, n, d), sink, 1);
                }
            }

            for (int day = 0; day < d + 1; day++) {
                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {

                        addEdge(adj, index(row, col, day, false, n, d), index(row, col, day, true, n, d), 1);

                        if (row < n - 1) {
                            int src = index(row, col, day, false, n, d);
                            int dst = index(row + 1, col, day, true, n, d);
                            addEdge(adj, src, dst, 1);
                        }

                        if (row > 0) {
                            int src = index(row, col, day, false, n, d);
                            int dst = index(row - 1, col, day, true, n, d);
                            addEdge(adj, src, dst, 1);
                        }

                        if (col < n - 1) {
                            int src = index(row, col, day, false, n, d);
                            int dst = index(row, col + 1, day, true, n, d);
                            addEdge(adj, src, dst, 1);
                        }

                        if (col > 0) {
                            int src = index(row, col, day, false, n, d);
                            int dst = index(row, col - 1, day, true, n, d);
                            addEdge(adj, src, dst, 1);
                        }
                    }
                }
            }

            for (int night = 0; night < d; night++) {
                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {

                        if (grid[row][col] > levelsAtEachNight[night]) {
                            int src = index(row, col, night, true, n, d);
                            int dst = index(row, col, night + 1, false, n, d);
                            addEdge(adj, src, dst, 1);
                        }
                    }
                }
            }

            int[] dist = new int[size];
            int[] excess = new int[size];
            int[] count = new int[size + 1];
            boolean[] active = new boolean[size];
            // B above

            for (Edge edge : adj[source]) {
                excess[source] += edge.capacity;
            }


            

//            for (Edge edge : adj[source]) {
//                System.out.println(edge.capacity);
//            }

//            System.out.println("excess[source] = " + excess[source]);

            count[0] = size;
            enqueue(dist, active, excess, B, source, size);
            active[sink] = true;

            while (b >= 0) {

//                System.out.println("b = " + b);

                if (!B[b].isEmpty()) {
                    int v = B[b].get(B[b].size() - 1);
                    B[b].remove(B[b].size() - 1);
                    active[v] = false;
                    discharge(adj, count, dist, active, excess, B, n, v);
                } else {
                    b--;
                }
            }

            for (List<Edge> edges : adj) {
                for (Edge edge : edges) {
                    System.out.println("edge.capacity = " + edge.capacity);
                }
            }

//            System.out.println("maxFlow = " + maxFlow);
            bufferedWriter.write("Case #" + (test + 1) + ": " + excess[sink]);
            bufferedWriter.newLine();


        }

        bufferedReader.close();
        bufferedWriter.close();
    }

//    public static void addEdge(List<Edge>[] graph, int a, int b, int capacity) {
//        graph[a].add(new Edge(b, graph[b].size(), capacity));
//        graph[b].add(new Edge(a, graph[a].size() - 1, 0));
//    }

//    static int dfs(List<Edge>[] graph, int[] dist, int dest, int current, int flow) {
//
//        if (current == dest) {
//            return flow;
//        }
//
//        for (Edge edge : graph[current]) {
//
//            if (dist[edge.from] == dist[current] + 1 && edge.flow < edge.capacity) {
//
//                int df = dfs(graph, dist, dest, edge.from, Math.min(flow, edge.capacity - edge.flow));
//
//                if (df > 0) {
//
//                    edge.flow += df;
//                    graph[edge.from].get(edge.to).flow -= df;
//
//                    return df;
//                }
//            }
//        }
//
//        return 0;
//    }

//    static boolean bfs(List<Edge>[] graph, int[] dist, int dest, int source) {
//
//        Arrays.fill(dist, -1);
//        dist[source] = 0;
//
//        Queue<Integer> queue = new LinkedList<>();
//        queue.add(source);
//
//        while (!queue.isEmpty()) {
//            int current = queue.poll();
//
//            for (Edge edge : graph[current]) {
//
//                if (dist[edge.from] < 0 && edge.flow < edge.capacity) {
//
//                    dist[edge.from] = dist[current] + 1;
//                    queue.add(edge.from);
//                }
//            }
//        }
//
//        return dist[dest] >= 0;
//    }

    private static int index(int row, int col, int day, boolean offset, int n, int d) {
        return col + row * n + day * (n * n) + (offset ? 1 : 0) * (d + 1) * (n * n);
    }

    private static void addEdge(List<Edge>[] adj, int from, int to, int cap) {
        adj[from].add(new Edge(from, to, cap, 0, adj[to].size()));
        if (from == to) {
            adj[from].get(adj[from].size() - 1).index++;
        }
        adj[to].add(new Edge(to, from, 0, 0, adj[from].size() - 1));
    }

    private static void enqueue(int[] dist, boolean[] active, int[] excess, List<Integer>[] B, int v, int n) {

        if (!active[v] && excess[v] > 0 && dist[v] < n) {
            active[v] = true;
            B[dist[v]].add(v);
            b = Math.max(b, dist[v]);
        }
    }

    static void gap(int k, int[] dist, boolean[] active, int[] excess, int[] count, List<Integer>[] B, int n) {

        for (int v = 0; v < n; v++)
            if (dist[v] >= k) {
                count[dist[v]]--;
                dist[v] = Math.max(dist[v], n);
                count[dist[v]]++;
                enqueue(dist, active, excess, B, v, n);
            }
    }

    static void relabel(List<Edge>[] adj, int v, int[] dist, boolean[] active, int[] excess, int[] count, List<Integer>[] B, int n) {
        count[dist[v]]--;
        dist[v] = n;
        for (Edge e : adj[v]) {
            if (e.capacity - e.flow > 0) {
                dist[v] = Math.min(dist[v], dist[e.to] + 1);
            }
        }
        count[dist[v]]++;
        enqueue(dist, active, excess, B, v, n);
    }

    private static void discharge(List<Edge>[] adj, int[] count, int[] dist, boolean[] active, int[] excess, List<Integer>[] B, int n, int v) {

        for (Edge e : adj[v]) {
            if (excess[v] > 0) {
                push(adj, e, dist, active, excess, B, n);
            } else {
                break;
            }
        }

        if (excess[v] > 0) {
            if (count[dist[v]] == 1) {
                gap(dist[v], dist, active, excess, count, B, n);
            } else {
                relabel(adj, v, dist, active, excess, count, B, n);
            }
        }
    }

    private static void push(List<Edge>[] adj, Edge e, int[] dist, boolean[] active, int[] excess, List<Integer>[] B, int n) {
        int amt = Math.min(excess[e.from], e.capacity - e.flow);

        if (dist[e.from] == dist[e.to] + 1 && amt > 0) {
            e.flow += amt;
            adj[e.to].get(e.index).flow -= amt;
            excess[e.to] += amt;
            excess[e.from] -= amt;
            enqueue(dist, active, excess, B, e.to, n);
        }
    }

}
