import java.io.*;
import java.util.Arrays;

// all methods are takes from the slides
public class B {

    public static class Node {
        int l = 0;
        int r = 0;
        int v = 0;
        int lazy = 0;
    }

    public static int build(int[] a, Node[] t, int p, int l, int r) {

        t[p].l = l;
        t[p].r = r;

        if (l == r) {
            t[p].v = a[l];
            return t[p].v;
        }

        int mid = (l + r) / 2;

        t[p].v = build(a, t, 2 * p + 1, l, mid) + build(a, t, 2 * p + 2, mid + 1, r);

        return t[p].v;
    }

    public static void propagate(Node[] t, int p) {
        t[p].v = t[p].v + (t[p].r - t[p].l + 1) * t[p].lazy;
        if (t[p].l != t[p].r) {
            t[2 * p + 1].lazy += t[p].lazy;
            t[2 * p + 2].lazy += t[p].lazy;
        }
        t[p].lazy = 0;
    }

    public static int sum(Node[] t, int p, int l, int r) {

        if (l > t[p].r || r < t[p].l) {
            return 0;
        }

        propagate(t, p);

        if (l <= t[p].l && t[p].r <= r) {
            return t[p].v;
        }

        return sum(t, 2 * p + 1, l, r) + sum(t, 2 * p + 2, l, r);
    }

    public static void rangeAdd(Node[] t, int p, int l, int r, int v) {
        propagate(t, p);

        if (l > t[p].r || r < t[p].l) {
            return;
        }

        if (l <= t[p].l && t[p].r <= r) {
            t[p].lazy += v;
            propagate(t, p);

        } else if (t[p].l != t[p].r) {
            rangeAdd(t, 2 * p + 1, l, r, v);
            rangeAdd(t, 2 * p + 2, l, r, v);
            t[p].v = t[2 * p + 1].v + t[2 * p + 2].v;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int te = 0; te < tests; te++) {
            var nk = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int n = nk[0];
            int k = nk[1];

            int[] arr = new int[n];
            int size = (int) Math.pow(2, Math.ceil((Math.log(n) / Math.log(2))) + 1);

            Node[] t = new Node[size];
            for (int i = 0; i < t.length; i++) {
                t[i] = new Node();
            }

            build(arr, t, 0, 0, n - 1);

            long res = 0;
            for (int i = 0; i < k; i++) {
                var query = bufferedReader.readLine().split(" ");

                if (query[0].equals("q")) {
                    int a = Integer.parseInt(query[1]) - 1;
                    res += sum(t, 0, a, a);
                    res %= 1000000007;
                } else {
                    int l = Integer.parseInt(query[1]) - 1;
                    int r = Integer.parseInt(query[2]) - 1;
                    int v = Integer.parseInt(query[3]);
                    rangeAdd(t, 0, l, r, v);
                }
            }

            // empty line between tests
            if (te < tests - 1) {
                bufferedReader.readLine();
            }

            bufferedWriter.write("Case #" + (te + 1) + ": " + res);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}