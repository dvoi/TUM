import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// all methods are taken from the slides
public class C {

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
        if (t[p].lazy != 0) {
            t[p].v = t[p].lazy;
        }

        if (t[p].l != t[p].r && t[p].lazy != 0) {
            t[2 * p + 1].lazy = t[2 * p + 1].v = t[p].lazy;
            t[2 * p + 2].lazy = t[2 * p + 2].v = t[p].lazy;
        }

        t[p].lazy = 0;
    }

    // modified sum
    public static int max(Node[] t, int p, int l, int r) {

        if (l > t[p].r || r < t[p].l) {
            return 0;
        }

        propagate(t, p);

        if (l <= t[p].l && t[p].r <= r) {
            return t[p].v;
        }

        return Math.max(max(t, 2 * p + 1, l, r), max(t, 2 * p + 2, l, r));
    }

    public static void rangeMax(Node[] t, int p, int l, int r, int v) {
        propagate(t, p);

        if (l > t[p].r || r < t[p].l) {
            return;
        }

        if (l <= t[p].l && t[p].r <= r) {
            t[p].lazy += v;
            propagate(t, p);

        } else if (t[p].l != t[p].r) {
            rangeMax(t, 2 * p + 1, l, r, v);
            rangeMax(t, 2 * p + 2, l, r, v);
            t[p].v = Math.max(t[2 * p + 1].v, t[2 * p + 2].v);
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

            var res = new ArrayList<String>();
            for (int i = 0; i < k; i++) {
                int[] whp = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                int w = whp[0];
                int h = whp[1];
                int p = whp[2];

                rangeMax(t, 0, p, p + w - 1, max(t, 0, p, p + w - 1) + h);
                res.add(String.valueOf(max(t, 0, 0, n - 1)));
            }

            // empty line between tests
            if (te < tests - 1) {
                bufferedReader.readLine();
            }

            bufferedWriter.write("Case #" + (te + 1) + ": " + String.join(" ", res));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}