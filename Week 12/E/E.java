import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class E {

    // all methods are based on the slides
    public static double[] rotate(double theta, double[] direction) {

        double[][] M = new double[][]{
                {Math.cos(theta), -Math.sin(theta), 0.0},
                {Math.sin(theta), Math.cos(theta), 0.0},
                {0.0, 0.0, 1.0}
        };

        return apply(M, direction);
    }

    public static double[] translate(double[] position, double[] direction) {

        double tx = direction[0];
        double ty = direction[1];

        double[][] M = new double[][]{
                {1, 0, tx},
                {0, 1, ty},
                {0, 0, 1}
        };

        return apply(M, position);
    }

    public static double[] apply(double[][] M, double[] vector) {

        double[] res = new double[3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i] += vector[j] * M[i][j];
            }
        }

        return res;
    }

    public static double[] normalize(double[] vector) {
        return new double[]{vector[0] / vector[2], vector[1] / vector[2], 1.0};
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            var ndas = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(ndas[0]);
            int d = Integer.parseInt(ndas[1]);
            int a = Integer.parseInt(ndas[2]);

            String s = ndas[3];

            Map<Character, String> map = new HashMap<>();

            for (int i = 0; i < n; i++) {
                var production = bufferedReader.readLine().split("");

                StringBuilder y = new StringBuilder();
                for (int j = 3; j < production.length; j++) {
                    y.append(production[j]);
                }

                map.put(production[0].charAt(0), y.toString());
            }
 
            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < d; i++) {

                for (int j = 0; j < s.length(); j++) {
                    char current = s.charAt(j);

                    if (map.containsKey(current)) {
                        sb.append(map.get(current));
                    } else {
                        sb.append(current); // '+' or '-'
                    }
                }

                s = sb.toString();
                sb = new StringBuilder();
            }

            double[] start = new double[]{0, 0, 1};
            double[] direction = new double[]{1, 0, 1};

            double theta = Math.toRadians(a);

            bufferedWriter.write("Case #" + (t + 1) + ":\n");
            bufferedWriter.write(start[0] + " " + start[1] + "\n");

            for (int i = 0; i < s.length(); i++) {
                char current = s.charAt(i);

                if (!Character.isLetter(current)) {
                    double sign = current == '+' ? 1.0 : -1.0;
                    direction = rotate(sign * theta, direction);
                } else {
                    start = normalize(translate(start, direction));
                    bufferedWriter.write(start[0] + " " + start[1] + "\n");
                }
            }
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}