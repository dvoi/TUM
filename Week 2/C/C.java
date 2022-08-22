import java.io.*;

public class C {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {

            String[] dpuv = bufferedReader.readLine().split(" ");

            int d = Integer.parseInt(dpuv[0]);
            int p = Integer.parseInt(dpuv[1]);
            int u = Integer.parseInt(dpuv[2]);
            int v = Integer.parseInt(dpuv[3]);

            double l = 0;
            double r = d;

            double mid = (l + r) / 2.0;
            double penultimate = -1;

            while (l < r) {

                mid = (l + r) / 2.0;

                if (penultimate == mid) {
                    break;
                }

                int before = (int) (u / mid + 1); // + 1 for correct rounding

                double distBefore = (before - 1) * mid; // - 1 to get the right value
                double firstPart = v;

                if (distBefore + mid > v) {
                    firstPart = distBefore + mid;
                }

                int after = (int) ((d - firstPart) / mid + 1);

                if (before + after >= p) {
                    l = mid;
                } else {
                    r = mid;
                }

                penultimate = mid;

            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + mid);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
