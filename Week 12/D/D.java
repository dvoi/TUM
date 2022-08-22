import java.io.*;
import java.util.Arrays;

// idea from:
// https://math.stackexchange.com/questions/296794/finding-the-transform-matrix-from-4-projected-points-with-javascript
public class D {

    public static int N = 3;

    public static double[][] findMatrix(double[][] matrix, double[] vector) {

        double[][] inverse = inverse(matrix);

        // finding lambda, mu and tau from applying inverse matrix, instead of Gaussian elimination
        double[] solution = translate(inverse, vector);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[j][i] = matrix[j][i] * solution[i];
            }
        }

        return matrix;
    }

    // Vector Translation from:
    // https://stackoverflow.com/questions/1500455/translate-vector-by-matrix
    public static double[] translate(double[][] M, double[] vector) {
        var res = new double[N];

        for (int i = 0; i < N; i++) {
            double accumulator = 0.0;
            for (int j = 0; j < N; j++) {
                accumulator += vector[j] * M[i][j]; // matrix is stored by rows
            }
            res[i] = accumulator;
        }

        return res;
    }

    // from the slides
    public static double[] normalize(double[] vector) {
        return new double[]{vector[0] / vector[2], vector[1] / vector[2]};
    }

    // Matrix Inverse from:
    // https://github.com/rchen8/Algorithms/blob/master/Matrix.java
    private static double[][] inverse(double[][] matrix) {
        double[][] inverse = new double[matrix.length][matrix.length];

        // minors and cofactors
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                inverse[i][j] = Math.pow(-1, i + j)
                        * determinant(minor(matrix, i, j));

        // adjugate and determinant
        double det = 1.0 / determinant(matrix);
        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j <= i; j++) {
                double temp = inverse[i][j];
                inverse[i][j] = inverse[j][i] * det;
                inverse[j][i] = temp * det;
            }
        }

        return inverse;
    }

    private static double determinant(double[][] matrix) {
        if (matrix.length != matrix[0].length)
            throw new IllegalStateException("invalid dimensions");

        if (matrix.length == 2)
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        double det = 0;
        for (int i = 0; i < matrix[0].length; i++)
            det += Math.pow(-1, i) * matrix[0][i]
                    * determinant(minor(matrix, 0, i));
        return det;
    }

    private static double[][] minor(double[][] matrix, int row, int column) {
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; i != row && j < matrix[i].length; j++)
                if (j != column)
                    minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
        return minor;
    }

    private static double[][] multiply(double[][] a, double[][] b) {
        if (a[0].length != b.length)
            throw new IllegalStateException("invalid dimensions");

        double[][] matrix = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < a[i].length; k++)
                    sum += a[i][k] * b[k][j];
                matrix[i][j] = sum;
            }
        }

        return matrix;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            var line = Arrays.stream(bufferedReader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();

            double ax = line[0];
            double ay = line[1];

            double bx = line[2];
            double by = line[3];

            double cx = line[4];
            double cy = line[5];

            double dx = line[6];
            double dy = line[7];

            double ex = line[8];
            double ey = line[9];

            double fx = line[10];
            double fy = line[11];

            double[][] original = {
                    {0.0, 1.0, 1.0},
                    {0.0, 0.0, 1.0},
                    {1.0, 1.0, 1.0}
            };

            double[][] projected = {
                    {ax, bx, cx},
                    {ay, by, cy},
                    {1.0, 1.0, 1.0}
            };

            var A = findMatrix(original, new double[]{0.0, 1.0, 1.0});
            var B = findMatrix(projected, new double[]{dx, dy, 1.0});

            var BInv = inverse(B);

            System.out.println("A");
            for (double[] doubles : A) {
                System.out.println(Arrays.toString(doubles));
            }
            System.out.println();


            System.out.println("B");
            for (double[] doubles : B) {
                System.out.println(Arrays.toString(doubles));
            }
            System.out.println();


            System.out.println("BInv");
            for (double[] doubles : BInv) {
                System.out.println(Arrays.toString(doubles));
            }
            System.out.println();

            var M = multiply(A, BInv);

            var fOrig = translate(M, new double[]{fx, fy, 1});
            var eOrig = translate(M, new double[]{ex, ey, 1});

            fOrig = normalize(fOrig);
            eOrig = normalize(eOrig);

            var originalHeight = eOrig[1] / fOrig[1];

            bufferedWriter.write("Case #" + (t + 1) + ": " + originalHeight);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
