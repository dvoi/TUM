import java.io.*;
import java.util.*;

public class C {

    private static class Road {

        int source;
        int dest;

        Road(int source, int dest) {
            this.source = source;
            this.dest = dest;
        }
    }


    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int k = 0; k < t; k++) {

            String[] nml = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nml[0]);
            int m = Integer.parseInt(nml[1]);
            int l = Integer.parseInt(nml[2]);

            HashMap<Integer, ArrayList<Integer>> oneWayMap = new HashMap<>();
            ArrayList<Road> twoWayRoads = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                String[] ab = bufferedReader.readLine().split(" ");

                int from = Integer.parseInt(ab[0]);
                int to = Integer.parseInt(ab[1]);

                if (!oneWayMap.containsKey(from)) {
                    oneWayMap.put(from, new ArrayList<>());
                }
                oneWayMap.get(from).add(to);

            }

            for (int i = 0; i < l; i++) {
                String[] ab = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(ab[0]);
                int b = Integer.parseInt(ab[1]);

                twoWayRoads.add(a > b ? new Road(b, a) : new Road(a, b));
            }

            // empty line between tests
            if (k < t - 1) {
                bufferedReader.readLine();
            }

            StringBuilder sb = new StringBuilder();
            ArrayList<Integer> topologicalOrder;

            try {
                topologicalOrder = topologicalSort(oneWayMap, n);
            } catch (Exception e) {
                bufferedWriter.write("Case #" + (k + 1) + ": no");
                bufferedWriter.newLine();
                continue;
            }

            int[] position = new int[n + 1];

            for (int i = 0; i < topologicalOrder.size(); i++) {
                Integer current = topologicalOrder.get(i);
                position[current] = i;
            }

            ArrayList<Road> newRoads = new ArrayList<>();

            for (Road road : twoWayRoads) {

                Road newRoad;
                if (position[road.source] > position[road.dest]) {
                    newRoad = road;
                } else {
                    newRoad = new Road(road.dest, road.source);
                }
                newRoads.add(newRoad);
            }

            for (Road p : newRoads) {
                sb.append("\n").append(p.source).append(" ").append(p.dest);
            }

            bufferedWriter.write("Case #" + (k + 1) + ": yes");
            bufferedWriter.write(sb.toString());
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static ArrayList<Integer> topologicalSort(HashMap<Integer, ArrayList<Integer>> edges, int n) throws Exception {

        int[] colors = new int[n + 1];
        ArrayList<Integer> result = new ArrayList<>();

        for (int start = 1; start <= n; start++) {
            if (!dfs(edges, start, result, colors)) {
                throw new Exception("e");
            }
        }

        return result;
    }

    private static boolean dfs(HashMap<Integer, ArrayList<Integer>> edges, int start, ArrayList<Integer> result, int[] colors) {


        if (colors[start] == 2) {
            return true;
        }

        if (!edges.containsKey(start)) {

            colors[start] = 2;
            result.add(start);

            return true;
        }

        for (Integer dest : edges.get(start)) {
            int color = colors[dest];

            if (color == 1) {
                return false;
            }

            if (color == 0) {

                colors[dest] = 1;

                boolean res = dfs(edges, dest, result, colors);
                if (!res) {
                    return false;
                }
            }
        }

        colors[start] = 2;
        result.add(start);

        return true;
    }

}
