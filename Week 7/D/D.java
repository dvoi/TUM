import java.io.*;
import java.util.*;

public class D {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] nm = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nm[0]); // number of trees
            int m = Integer.parseInt(nm[1]); // available disk saws

            ArrayList<Integer> trees = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                trees.add(Integer.parseInt(bufferedReader.readLine()));
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            // job scheduling from the meeting slides
            PriorityQueue<Long> queue = new PriorityQueue<>(Comparator.comparing(Long::longValue));
            for (int i = 0; i < m; i++) {
                queue.add(0L);
            }

            long time = 0L;

            trees.sort(Collections.reverseOrder());

            for (Integer longestTree : trees) {
                // queue provides the saw with the minimal time
                long newSawTime = queue.poll() + longestTree;
                queue.add(newSawTime);
                time = Math.max(newSawTime, time);
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + time);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
