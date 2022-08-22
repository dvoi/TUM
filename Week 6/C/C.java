import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] mn = bufferedReader.readLine().split(" ");

            int backers = Integer.parseInt(mn[0]);
            int judges = Integer.parseInt(mn[1]);

            List<Integer>[] preferences = new List[judges];

            for (int i = 0; i < judges; i++) {
                String[] line = bufferedReader.readLine().split(" ");

                preferences[i] = new ArrayList<>();

                for (int j = 0; j < line.length - 1; j++) {
                    int p = Integer.parseInt(line[j]);
                    preferences[i].add(p);
                }
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            // SAT Problem
            // https://www.borealisai.com/en/blog/tutorial-10-sat-solvers-ii-algorithms/
            boolean[] sat = new boolean[backers];
            Arrays.fill(sat, true);

            bufferedWriter.write("Case #" + (t + 1) + ": " + (dfs(preferences, sat, 0, judges, backers) ? "yes" : "no"));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static boolean dfs(List<Integer>[] preferences, boolean[] sat, int i, int judges, int bakers) {

        if (i == bakers) {

            // go through each clause (AND)
            for (int j = 0; j < judges; j++) {
                boolean clause = false;

                // go through each element of the clause (OR)
                for (int p : preferences[j]) {

                    // if at least one element is true -> all true
                    if (sat[p > 0 ? p - 1 : Math.abs(p) - 1] == (p > 0)) {
                        clause = true;
                        break;
                    }
                }

                // if at least one clause is false -> all false
                if (!clause) {
                    return false;
                }
            }

            return true;
        }

        // backer's preference is the first muffin
        sat[i] = true;
        if (dfs(preferences, sat, i + 1, judges, bakers)) {
            return true;
        }

        // backer's preference is the second muffin
        sat[i] = false;
        return dfs(preferences, sat, i + 1, judges, bakers);
    }

}
