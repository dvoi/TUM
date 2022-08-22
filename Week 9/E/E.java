import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class E {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            String[] line = bufferedReader.readLine().split(" ");

            BigInteger s1 = new BigInteger(line[0]);
            BigInteger c1 = new BigInteger(line[1]);
            BigInteger s2 = new BigInteger(line[2]);
            BigInteger c2 = new BigInteger(line[3]);
            BigInteger n = new BigInteger(line[4]);

            BigInteger a = c1;
            BigInteger b = c2;
            BigInteger c = s2.subtract(s1);
            BigInteger gcd = a.gcd(b);

            if (c.mod(gcd).compareTo(BigInteger.ZERO) != 0) {
                bufferedWriter.write("Case #" + (t + 1) + ": impossible");
                bufferedWriter.newLine();
                continue;
            }

            var rs = egcd(a.divide(gcd), b.divide(gcd).negate());

            BigInteger r = rs.get(0);
            BigInteger s = rs.get(1);

            if (r.compareTo(BigInteger.ONE) != 0) {
                s = s.multiply(BigInteger.ONE.negate());
            }

            BigInteger x = s.multiply(c.divide(gcd));
            x = x.mod(c2.divide(gcd));

            BigInteger k = BigInteger.ZERO;
            BigInteger res = c1.multiply(c2.divide(gcd).multiply(k).add(x)).add(s1);

            while (!(res.compareTo(n) >= 0 && res.compareTo(s1.compareTo(s2) > 0 ? s1 : s2) >= 0)) {
                k = k.add(BigInteger.ONE);
                res = c1.multiply(c2.divide(gcd).multiply(k).add(x)).add(s1);
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + res);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static ArrayList<BigInteger> egcd(BigInteger a, BigInteger b) {

        BigInteger s = BigInteger.ZERO;
        BigInteger s_ = BigInteger.ONE;
        BigInteger t = BigInteger.ONE;
        BigInteger t_ = BigInteger.ZERO;
        BigInteger r = b;
        BigInteger r_ = a;

        while (r.compareTo(BigInteger.ZERO) != 0) {

            BigInteger q = r_.divide(r);

            BigInteger rOld = r;
            r = r_.subtract(q.multiply(r));
            r_ = rOld;

            BigInteger sOld = s;
            s = s_.subtract(q.multiply(s));
            s_ = sOld;

            BigInteger tOld = t;
            t = t_.subtract(q.multiply(t));
            t_ = tOld;
        }

        return new ArrayList<>(List.of(r_, s_));
    }
}