import java.io.*;
import java.text.DecimalFormat;

public class B {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {

            String[] nb = bufferedReader.readLine().split(" ");
            String[] costs = bufferedReader.readLine().split(" ");

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            int n = Integer.parseInt(nb[0]);
            double b = Integer.parseInt(nb[1]);

            double l = 0.0;
            double r = 1.0;

            double mid = (l + r) / 2.0;

            double result;
            double penultimate = -1;
            double a;
            double x;

            while (l <= r) {

                mid = (l + r) / 2;

                result = 0;
                x = 1;

                for (int i = 0; i < n; i++) {
                    a = Integer.parseInt(costs[i]);
                    x = x * mid;
                    result += a * x;
                }

                if (result - b == 0 || penultimate == result) {
                    break;
                }

                if (result < b) {
                    l = mid;
                } else {
                    r = mid;
                }

                penultimate = result;

            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + mid);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
