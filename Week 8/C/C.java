import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class C {

    static class Box {

        private final int x;
        private final int y;
        private final int z;
        private final int area;

        public Box(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.area = x * y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getArea() {
            return area;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] hn = bufferedReader.readLine().split(" ");

            int h = Integer.parseInt(hn[0]);
            int n = Integer.parseInt(hn[1]) * 3;

            Box[] boxes = new Box[n];
            int[] msh = new int[n];

            for (int i = 0; i < n; i += 3) {

                String[] xyz = bufferedReader.readLine().split(" ");

                int x = Integer.parseInt(xyz[0]); // width
                int y = Integer.parseInt(xyz[1]); // length/depth
                int z = Integer.parseInt(xyz[2]); // height

                boxes[i + 0] = new Box(Math.max(x, y), Math.min(x, y), z);
                boxes[i + 1] = new Box(Math.max(y, z), Math.min(y, z), x);
                boxes[i + 2] = new Box(Math.max(x, z), Math.min(x, z), y);
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            // Box Stacking DP:
            // https://www.geeksforgeeks.org/box-stacking-problem-dp-22/
            Arrays.sort(boxes, Comparator.comparing(Box::getArea).reversed());

            for (int i = 0; i < n; i++) {
                msh[i] = boxes[i].getZ();
            }

            int res = -1;

            for (int i = 0; i < n; i++) {
                int max = 0;

                for (int j = 0; j < i; j++) {
                    if (boxes[i].getX() < boxes[j].getX() && boxes[i].getY() < boxes[j].getY()) {
                        max = Math.max(max, msh[j]);
                    }
                }

                int intermediate = max + boxes[i].getZ();
                msh[i] = intermediate;

                res = Math.max(res, intermediate);
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + (res >= h ? "yes" : "no"));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
