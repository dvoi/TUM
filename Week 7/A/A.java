import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            String[] lnd = bufferedReader.readLine().split(" ");

            int l = Integer.parseInt(lnd[0]);
            int n = Integer.parseInt(lnd[1]);
            int radius = Integer.parseInt(lnd[2]);

            if (n < 1) {
                bufferedReader.readLine();
                bufferedReader.readLine();

                bufferedWriter.write("Case #" + (t + 1) + ": impossible");
                bufferedWriter.newLine();
                continue;
            }

            List<Integer> positions = Arrays.stream(bufferedReader.readLine().split(" ")).map(Integer::parseInt).sorted().collect(Collectors.toList());

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            int minLights = 0;
            int currentIndex = 0;
            int distance = 0;

            StringBuilder sb = new StringBuilder();

            while (distance < l) {

                boolean lit = false;
                int lightDistance = 0;

                for (int i = currentIndex; i < n; i++, currentIndex++) {

                    // check if area is within the range of a light
                    if (positions.get(i) - radius > distance) {
                        break;
                    }

                    lit = true;
                    lightDistance = positions.get(currentIndex);
                }

                if (!lit) {
                    sb.append("impossible");
                    break;
                }

                distance = lightDistance + radius;
                minLights++;
            }

            if (sb.toString().isEmpty()) {
                sb.append(minLights);
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + sb);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}

