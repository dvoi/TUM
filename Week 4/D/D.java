import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class D {

    static class Pair {

        int time;
        HashSet<Integer> successors;

        public Pair(int time, HashSet<Integer> successors) {
            this.time = time;
            this.successors = successors;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {

            int n = Integer.parseInt(bufferedReader.readLine());

            ArrayList<Pair> tasks = new ArrayList<>(n);

            for (int i = 0; i < n; i++) {
                String[] psjs = bufferedReader.readLine().split(" ");

                int p = Integer.parseInt(psjs[0]); // time for task i
                int s = Integer.parseInt(psjs[1]); // number of successors

                HashSet<Integer> successors = new HashSet<>();
                for (int k = 2; k < s + 2; k++) {
                    successors.add(Integer.parseInt(psjs[k]) - 1);
                }

                tasks.add(new Pair(p, successors));
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + maxPath(tasks, 0, new HashMap<>()));
            bufferedWriter.newLine();

        }

        bufferedReader.close();
        bufferedWriter.close();

    }

    private static int maxPath(ArrayList<Pair> tasks, int start, HashMap<Integer, Integer> intermediate) {

        int totalTime = 0;

        for (Integer successor : tasks.get(start).successors) {

            if (intermediate.containsKey(successor)) {
                totalTime = Math.max(totalTime, intermediate.get(successor));
            } else {
                totalTime = Math.max(totalTime, maxPath(tasks, successor, intermediate));
            }
        }

        totalTime += tasks.get(start).time;
        intermediate.put(start, totalTime);

        return totalTime;
    }
}
