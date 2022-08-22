import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class B {

    static class Dependency {

        private final int c;
        private final int p;
        private final int d;
        private final int q;

        public Dependency(int c, int p, int d, int q) {
            this.c = c;
            this.p = p;
            this.d = d;
            this.q = q;
        }

        public int getC() {
            return c;
        }

        public int getP() {
            return p;
        }

        public int getD() {
            return d;
        }

        public int getQ() {
            return q;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] nm = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nm[0]); // number of characters
            int m = Integer.parseInt(nm[1]); // number of dependencies

            // approach similar to C and queens problems
            int[] chapters = new int[n];
            Dependency[] dependencies = new Dependency[m];
            int chaptersTotal = 0;

            for (int i = 0; i < n; i++) {
                int a = Integer.parseInt(bufferedReader.readLine());

                chapters[i] = a;
                chaptersTotal += a;
            }

            for (int j = 0; j < m; j++) {
                String[] cpdq = bufferedReader.readLine().split(" ");

                // first this
                int c = Integer.parseInt(cpdq[0]) - 1; // character of p
                int p = Integer.parseInt(cpdq[1]); // chapter of c

                // then this
                int d = Integer.parseInt(cpdq[2]) - 1; // character of q
                int q = Integer.parseInt(cpdq[3]); // chapter of d

                dependencies[j] = new Dependency(c, p, d, q);
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }



            Arrays.sort(dependencies, Comparator.comparing(Dependency::getP)
                    .thenComparing(Dependency::getQ)
                    .thenComparing(Dependency::getC)
                    .thenComparing(Dependency::getD));



            bufferedWriter.write("Case #" + (t + 1) + ": " + findTotal(new int[chaptersTotal], chapters, dependencies, chaptersTotal, n, 0));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static int findTotal(int[] chapters, int[] chaptersLeft, Dependency[] dependencies, int chaptersTotal, int numberOfCharacters, int i) {

        if (i == chaptersTotal) {
            for (Dependency dependency : dependencies) {



//                System.out.println("dependency = " + dependency);



                int cs = 0;
                int ds = 0;

                for (int c = 0; c < chaptersTotal; c++) {
                    int character = chapters[c];

                    if (character == dependency.getC()) {
                        cs++;
                    }

                    if (character == dependency.getD()) {
                        ds++;
                    }


//                    System.out.println("cs = " + cs);
//
//
//                    System.out.println("dependency.getP() = " + dependency.getP());
//                    System.out.println("dependency.getQ() = " + dependency.getQ());

                    if (cs == dependency.getP()) {

                        if (ds >= dependency.getQ()) {
                            return 0;
                        }

                        // return true;
                        break;
                    }
                }
            }

            return 1;
        }

        int res = 0;

        for (int c = 0; c < numberOfCharacters; c++) {

//            System.out.println("chaptersLeft = " + Arrays.toString(chaptersLeft));

            if ((i > 0 && chapters[i - 1] == c) || chaptersLeft[c] <= 0) {
                continue;
            }

//            System.out.println("i = " + i);
//            System.out.println("chapters[i] = " + chapters[i]);
            chapters[i] = c;

            // branch out
            chaptersLeft[c] -= 1;

            res += findTotal(chapters, chaptersLeft, dependencies, chaptersTotal, numberOfCharacters, i + 1);

            // undo and proceed
            chaptersLeft[c] += 1;
        }

        return res;
    }

    private static boolean isValid(int[] chapters, Dependency[] dependencies, int chaptersTotal) {

        for (Dependency dependency : dependencies) {

            int cs = 0;
            int ds = 0;

            for (int i = 0; i < chaptersTotal; ++i) {
                int character = chapters[i];

                if (character == dependency.getC()) {
                    cs++;
                }

                if (character == dependency.getD()) {
                    ds++;
                }

                if (cs == dependency.getP()) {
                    if (ds >= dependency.getQ()) {
                        return false;
                    }
                    break;
                }
            }
        }

        return true;
    }
}
