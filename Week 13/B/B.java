import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class B {

    public static class Farmer {

        int iq;
        int w;

        public Farmer(int iq, int w) {
            this.iq = iq;
            this.w = w;
        }

        public int getIq() {
            return iq;
        }

        public int getW() {
            return w;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            ArrayList<Farmer> farmers = new ArrayList<>();

            int n = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < n; i++) {
                var iqw = bufferedReader.readLine().split(" ");

                int iq = Integer.parseInt(iqw[0]);
                int w = Integer.parseInt(iqw[1]);

                farmers.add(new Farmer(iq, w));
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            farmers.sort(Comparator.comparing(Farmer::getIq).thenComparing(Farmer::getW, Comparator.reverseOrder()));

            var iqs = farmers.stream().map(Farmer::getW).mapToInt(Integer::intValue).toArray();

            int res = lds(iqs, iqs.length);

            bufferedWriter.write("Case #" + (t + 1) + ": " + res);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    // https://www.geeksforgeeks.org/longest-decreasing-subsequence/
    static int lds(int arr[], int n) {
        int lds[] = new int[n];
        int i, j, max = 0;

        // Initialize LDS with 1
        // for all index. The minimum
        // LDS starting with any
        // element is always 1
        for (i = 0; i < n; i++)
            lds[i] = 1;

        // Compute LDS from every
        // index in bottom up manner
        for (i = 1; i < n; i++)
            for (j = 0; j < i; j++)
                if (arr[i] < arr[j] &&
                        lds[i] < lds[j] + 1)
                    lds[i] = lds[j] + 1;

        // Select the maximum
        // of all the LDS values
        for (i = 0; i < n; i++)
            if (max < lds[i])
                max = lds[i];

        // returns the length
        // of the LDS
        return max;
    }
}
