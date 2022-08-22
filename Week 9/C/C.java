import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class C {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        outer:
        for (int t = 0; t < tests; t++) {
            String[] nk = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nk[0]); // 0<=n<=15
            BigInteger k = new BigInteger(nk[1]);

            HashMap<BigInteger, BigInteger> map = new HashMap<>();

            ArrayList<BigInteger> num = new ArrayList<>();
            ArrayList<BigInteger> rem = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String[] sr = bufferedReader.readLine().split(" ");

                BigInteger s = new BigInteger(sr[0]);
                BigInteger r = new BigInteger(sr[1]);

                if (map.containsKey(s)) {
                    if (!map.get(s).equals(r)) {

                        bufferedWriter.write("Case #" + (t + 1) + ": " + "impossible");
                        bufferedWriter.newLine();

                        bufferedReader.readLine();

                        continue outer;
                    }
                } else {
                    num.add(s);
                    rem.add(r);

                    map.put(s, r);
                }
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            BigInteger ans = findMinX(num, rem, n, k);

            StringBuilder sb = new StringBuilder();
            // cannot be greater than k and smaller than 0
            if (ans.compareTo(k) > 0 || ans.compareTo(BigInteger.ZERO) < 0) {
                sb.append("impossible");
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + (sb.toString().isEmpty() ? ans : sb));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    // Chinese Remainder Theorem:
    // https://www.geeksforgeeks.org/chinese-remainder-theorem-set-2-implementation/
    public static BigInteger findMinX(ArrayList<BigInteger> num, ArrayList<BigInteger> rem, int n, BigInteger k) {

        BigInteger prod = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            prod = prod.multiply(num.get(i));
        }

        BigInteger res = BigInteger.ZERO;
        for (int i = 0; i < n; i++) {
            BigInteger pp = prod.divide(num.get(i));
            BigInteger inv = pp.modInverse(num.get(i));

            res = res.add(rem.get(i).multiply(inv).multiply(pp));
        }

        res = res.mod(prod);

        return res.add(k.subtract(res).divide(prod).multiply(prod));
    }

}
