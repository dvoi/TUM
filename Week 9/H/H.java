import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class H {

    // https://codippa.com/how-to-work-with-fractions-in-java/
    static class Fraction {

        static Fraction ONE = new Fraction(BigInteger.ONE, BigInteger.ONE);
        static Fraction ZERO = new Fraction(BigInteger.ZERO, BigInteger.ONE);

        BigInteger numerator;
        BigInteger denominator;

        public Fraction(BigInteger numerator, BigInteger denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction add(Fraction fraction) {

            BigInteger newNumerator = numerator.multiply(fraction.denominator).add(fraction.numerator.multiply(denominator));
            BigInteger newDenominator = denominator.multiply(fraction.denominator);

            return reduce(newNumerator, newDenominator);
        }

        public Fraction subtract(Fraction fraction) {

            BigInteger newNumerator = numerator.multiply(fraction.denominator).subtract(fraction.numerator.multiply(denominator));
            BigInteger newDenominator = denominator.multiply(fraction.denominator);

            return reduce(newNumerator, newDenominator);
        }

        public Fraction multiply(Fraction fraction) {

            BigInteger newNumerator = numerator.multiply(fraction.numerator);
            BigInteger newDenominator = denominator.multiply(fraction.denominator);

            return reduce(newNumerator, newDenominator);
        }

        public Fraction divide(Fraction fraction) {

            BigInteger newNumerator = numerator.multiply(fraction.denominator);
            BigInteger newDenominator = denominator.multiply(fraction.numerator);

            return reduce(newNumerator, newDenominator);
        }

        public Fraction reduce(BigInteger numerator, BigInteger denominator) {

            BigInteger gcd = numerator.gcd(denominator);
            numerator = numerator.divide(gcd);
            denominator = denominator.divide(gcd);

            return new Fraction(numerator, denominator);
        }

        @Override
        public String toString() {
            return numerator + "/" + denominator;
        }
    }

    static class Set {

        int a;
        String b;

        public Set(String set) {
            String[] ab = set.split("d");

            this.a = Integer.parseInt(ab[0]);
            this.b = ab[1];
        }

        public int getA() {
            return a;
        }

        public String getB() {
            return b;
        }
    }

    static final int maxSize = 50 * 20;

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            String[] line = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(line[0]);
            List<Set> sets = Arrays.stream(line[1].split("\\+")).map(Set::new).collect(Collectors.toList());

            Fraction[][] dp = new Fraction[maxSize][maxSize];
            for (int i = 0; i < maxSize; i++) {
                Arrays.fill(dp[i], Fraction.ZERO);
            }

            dp[0][0] = Fraction.ONE;
            Fraction product = Fraction.ONE;

            int current = 1;
            for (Set set : sets) {

                Fraction size = new Fraction(new BigInteger(set.getB()), BigInteger.ONE);
                int maxSide = Integer.parseInt(set.getB());

                product.multiply(size);

                for (int dice = 0; dice < set.getA(); dice++) {

                    for (int side = 1; side <= maxSide; side++) {

                        for (int i = 0; i < n; i++) {
                            dp[side + i][current] = dp[side + i][current].add(dp[i][current - 1].divide(size));
                        }
                    }

                    current++;
                }
            }

            Fraction ans = Fraction.ONE;
            for (int i = 0; i < n; i++) {
                ans = ans.subtract(dp[i][current - 1]);
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + ans);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}




