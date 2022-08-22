import java.io.*;
import java.math.BigInteger;

public class B {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] ny = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(ny[0]);
            BigInteger y = new BigInteger(ny[1]);

            bufferedWriter.write("Case #" + (t + 1) + ": " + y.modInverse(BigInteger.TEN.pow(n)));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
