import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class B {

    static class Dependency {

        int from;
        int to;

        public Dependency(int from, int to) {
            this.from = from;
            this.to = to;
        }

    }

    public static boolean conflict(Set<Integer> toRemove, int[] toKeep, ArrayList<Dependency> dependencies) {

        HashSet<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        for (int pack : toKeep) {

            stack.push(pack);

            while (!stack.isEmpty()) {

                int p = stack.pop();

                if (toRemove.contains(p)) {
                    return true;
                }

                if (!visited.contains(p)) {

                    visited.add(p);

                    for (Dependency dependency : dependencies) {
                        if (dependency.from == p) {
                            if (!visited.contains(dependency.to)) {
                                stack.push(dependency.to);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }


    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int c = 0; c < t; c++) {

            String[] nkrd = bufferedReader.readLine().split(" ");

//            int n = Integer.parseInt(nkrd[0]);
//            int k = Integer.parseInt(nkrd[1]);
//            int r = Integer.parseInt(nkrd[2]);
            int d = Integer.parseInt(nkrd[3]);

            String[] ks = bufferedReader.readLine().split(" ");
            String[] rs = bufferedReader.readLine().split(" ");

            int[] toKeep = Arrays.stream(ks)
                    .filter(a -> !a.equals(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            Set<Integer> toRemove = Arrays.stream(rs)
                    .filter(a -> !a.equals(""))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toSet());

            ArrayList<Dependency> dependencies = new ArrayList<>();

            for (int i = 0; i < d; i++) {
                String[] dep = bufferedReader.readLine().split(" ");

                int[] dependency = Arrays.stream(dep).mapToInt(Integer::parseInt).toArray();
                dependencies.add(new Dependency(dependency[0], dependency[1]));
            }

            // empty line between tests
            if (c < t - 1) {
                bufferedReader.readLine();
            }

            bufferedWriter.write("Case #" + (c + 1) + ": " + (conflict(toRemove, toKeep, dependencies) ? "conflict" : "ok"));
            bufferedWriter.newLine();

        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
