import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class D {

    // https://www.geeksforgeeks.org/segment-tree-set-1-range-minimum-query/
    // Program for range minimum query using segment tree
    static class SegmentTreeRMQ {
        int[] st; //array to store segment tree

        // A utility function to get minimum of two numbers
        int minVal(int x, int y) {
            return Math.min(x, y);
        }

        // A utility function to get the middle index from corner
        // indexes.
        int getMid(int s, int e) {
            return s + (e - s) / 2;
        }

        /*  A recursive function to get the minimum value in a given
            range of array indexes. The following are parameters for
            this function.

            st    --> Pointer to segment tree
            index --> Index of current node in the segment tree. Initially
                       0 is passed as root is always at index 0
            ss & se  --> Starting and ending indexes of the segment
                         represented by current node, i.e., st[index]
            qs & qe  --> Starting and ending indexes of query range */
        int RMQUtil(int ss, int se, int qs, int qe, int index) {
            // If segment of this node is a part of given range, then
            // return the min of the segment
            if (qs <= ss && qe >= se)
                return st[index];

            // If segment of this node is outside the given range
            if (se < qs || ss > qe)
                return Integer.MAX_VALUE;

            // If a part of this segment overlaps with the given range
            int mid = getMid(ss, se);

            return minVal(RMQUtil(ss, mid, qs, qe, 2 * index + 1),
                    RMQUtil(mid + 1, se, qs, qe, 2 * index + 2));
        }

        // Return minimum of elements in range from index qs (query
        // start) to qe (query end).  It mainly uses RMQUtil()
        int RMQ(int n, int qs, int qe) {
            // Check for erroneous input values
            if (qs < 0 || qe > n - 1 || qs > qe) {
                System.out.println("Invalid Input");
                return -1;
            }

            return RMQUtil(0, n - 1, qs, qe, 0);
        }

        // A recursive function that constructs Segment Tree for
        // array[ss..se]. si is index of current node in segment tree st
        int constructSTUtil(int[] arr, int ss, int se, int si) {
            // If there is one element in array, store it in current
            //  node of segment tree and return
            if (ss == se) {
                st[si] = arr[ss];
                return arr[ss];
            }

            // If there are more than one elements, then recur for left and
            // right subtrees and store the minimum of two values in this node
            int mid = getMid(ss, se);
            st[si] = minVal(constructSTUtil(arr, ss, mid, si * 2 + 1),
                    constructSTUtil(arr, mid + 1, se, si * 2 + 2));
            return st[si];
        }

        /* Function to construct segment tree from given array. This function
           allocates memory for segment tree and calls constructSTUtil() to
           fill the allocated memory */
        void constructST(int[] arr, int n) {
            // Allocate memory for segment tree

            //Height of segment tree
            int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));

            //Maximum size of segment tree
            int max_size = 2 * (int) Math.pow(2, x) - 1;
            st = new int[max_size]; // allocate memory

            // Fill the allocated memory st
            constructSTUtil(arr, 0, n - 1, 0);
        }
    }

    // Modified version of:
    // https://www.geeksforgeeks.org/euler-tour-tree/
    static class EulerTour {

        int index = 0;
        boolean[] visited;
        int[] depth;
        int[] firstIndex;
        ArrayList<ArrayList<Integer>> adj;

        public EulerTour(int n) {
            visited = new boolean[n];
            depth = new int[2 * n - 1];
            firstIndex = new int[n];

            Arrays.fill(firstIndex, -1);

            adj = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                adj.add(new ArrayList<>());
            }
        }

        public void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        public void eulerTree(int u, int currentDepth) {
            visited[u] = true;

            depth[index] = currentDepth;

            if (firstIndex[u] == -1) {
                firstIndex[u] = index;
            }

            index++;

            for (int it : adj.get(u)) {
                if (!visited[it]) {
                    eulerTree(it, currentDepth + 1);
                    depth[index] = currentDepth;
                    index++;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int te = 0; te < tests; te++) {
            int n = Integer.parseInt(bufferedReader.readLine());

            EulerTour eulerTour = new EulerTour(n);
            for (int i = 0; i < n; i++) {
                int[] branches = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                for (int j = 1; j < branches.length; j++) {
                    eulerTour.addEdge(i, branches[j] - 1);
                }
            }

            eulerTour.eulerTree(0, 0);

            SegmentTreeRMQ segmentTree = new SegmentTreeRMQ();
            segmentTree.constructST(eulerTour.depth, eulerTour.depth.length);

            int[] toVisit = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // empty line between tests
            if (te < tests - 1) {
                bufferedReader.readLine();
            }

            int total = 0;
            int from = 0;

            for (int i = 1; i < toVisit.length; i++) {
                int to = toVisit[i] - 1;

                int indexFrom = Math.min(eulerTour.firstIndex[from], eulerTour.firstIndex[to]);
                int indexTo = Math.max(eulerTour.firstIndex[from], eulerTour.firstIndex[to]);

                int min = segmentTree.RMQ(eulerTour.depth.length, indexFrom, indexTo);

                total += eulerTour.depth[indexFrom] + eulerTour.depth[indexTo] - 2 * min;

                from = to;
            }

            bufferedWriter.write("Case #" + (te + 1) + ": " + total);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
