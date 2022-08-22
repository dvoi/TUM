import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class D {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] nm = bufferedReader.readLine().split(" ");

            BigInteger n = new BigInteger(nm[0]);
            BigInteger m = new BigInteger(nm[1]);

            // Adapted from:
            // https://fishi.devtail.io/weblog/2015/06/25/computing-large-binomial-coefficients-modulo-prime-non-prime/
            List<BigInteger> modFacts = List.of("2", "3", "5", "7", "11", "13", "17", "19", "23").stream().map(BigInteger::new).collect(Collectors.toList());

            bufferedWriter.write("Case #" + (t + 1) + ": " + binom(n, m, modFacts));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static BigInteger binom(BigInteger n, BigInteger k, List<BigInteger> modFacts) {

        ArrayList<ArrayList<BigInteger>> congruences = new ArrayList<>();

        for (BigInteger p : modFacts) {
            congruences.add(new ArrayList<>(List.of(lucasBinom(n, k, p), p)));
        }

        return crt(congruences);
    }

    private static BigInteger lucasBinom(BigInteger n, BigInteger k, BigInteger p) {

        ArrayList<BigInteger> np = getBaseDigits(n, p);
        ArrayList<BigInteger> kp = getBaseDigits(k, p);

        BigInteger binom = BigInteger.ONE;
        for (int i = np.size() - 1; i > -1; i--) {

            BigInteger ni = np.get(i);
            BigInteger ki = BigInteger.ZERO;

            if (i < kp.size()) {
                ki = kp.get(i);
            }

            binom = binom.multiply(fermatBinomAdvanced(ni, ki, p)).mod(p);
        }

        return binom;
    }

    private static BigInteger crt(ArrayList<ArrayList<BigInteger>> congruences) {

        BigInteger m = BigInteger.ONE;
        for (ArrayList<BigInteger> congruence : congruences) {
            m = m.multiply(congruence.get(1));
        }

        BigInteger result = BigInteger.ZERO;
        for (ArrayList<BigInteger> congruence : congruences) {
            BigInteger s = egcd(m.divide(congruence.get(1)), congruence.get(1));
            result = result.add(congruence.get(0).multiply(s).multiply(m).divide(congruence.get(1)));
        }

        return result.mod(m);
    }

    private static BigInteger egcd(BigInteger a, BigInteger b) {

        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;
        BigInteger u = BigInteger.ONE;
        BigInteger v = BigInteger.ZERO;

        while (b.compareTo(BigInteger.ZERO) != 0) {
            BigInteger q = a.divide(b);

            BigInteger bDash = b;
            b = a.subtract(q.multiply(b));
            a = bDash;

            BigInteger xDash = x;
            x = y.subtract(q.multiply(x));
            y = xDash;

            BigInteger uDash = u;
            u = v.subtract(q.multiply(u));
            v = uDash;
        }

        return y;
    }

    private static BigInteger fermatBinomAdvanced(BigInteger n, BigInteger k, BigInteger p) {

        BigInteger numDegree = factExp(n, p).subtract(factExp(n.subtract(k), p));
        BigInteger denDegree = factExp(k, p);

        if (numDegree.compareTo(denDegree) > 0) {
            return BigInteger.ZERO;
        }

        if (k.compareTo(n) > 0) {
            return BigInteger.ZERO;
        }

        BigInteger num = BigInteger.ONE;
        for (BigInteger i = n; i.compareTo(n.subtract(k)) > 0; i = i.subtract(BigInteger.ONE)) {

            BigInteger cur = i;

            while (cur.mod(p).compareTo(BigInteger.ZERO) == 0) {
                cur = cur.divide(p);
            }

            num = num.multiply(cur).mod(p);
        }

        BigInteger denom = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; i.compareTo(k.add(BigInteger.ONE)) < 0; i = i.add(BigInteger.ONE)) {

            BigInteger cur = i;

            while (cur.mod(p).compareTo(BigInteger.ZERO) == 0) {
                cur = cur.divide(p);
            }

            denom = denom.multiply(cur).mod(p);
        }

        return num.multiply(denom.modPow(p.subtract(BigInteger.TWO), p)).mod(p);
    }

    private static ArrayList<BigInteger> getBaseDigits(BigInteger n, BigInteger b) {

        ArrayList<BigInteger> d = new ArrayList<>();

        while (n.compareTo(BigInteger.ZERO) > 0) {
            d.add(n.mod(b));
            n = n.divide(b);
        }

        return d;
    }

    private static BigInteger factExp(BigInteger n, BigInteger p) {

        BigInteger e = BigInteger.ZERO;
        BigInteger u = p;

        while (u.compareTo(n) <= 0) {
            e = e.add(n.divide(u));
            u = u.multiply(p);
        }

        return e;
    }
}
