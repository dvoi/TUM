import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class D {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {

            String[] sf = bufferedReader.readLine().split(" ");

            int s = Integer.parseInt(sf[0]); // number of stations
            int f = Integer.parseInt(sf[1]); // number of friends to visit

            int[][] rooms = new int[s][2]; // rooms: [u..v] for each station
            HashMap<Integer, Integer> map = new HashMap<>();

            int roomLower = Integer.MAX_VALUE;
            int roomUpper = 0;

            for (int i = 0; i < s; i++) {

                String[] uv = bufferedReader.readLine().split(" ");

                int u = Integer.parseInt(uv[0]);
                int v = Integer.parseInt(uv[1]) + 1;

                rooms[i] = new int[]{u, v};

                if (u < roomLower) {
                    roomLower = u;
                }

                if (v > roomUpper) {
                    roomUpper = v;
                }

                if (!map.containsKey(u)) {
                    map.put(u, 0);
                }

                if (!map.containsKey(v)) {
                    map.put(v, 0);
                }

            }

            for (Integer key : map.keySet()) {
                for (int[] room : rooms) {
                    if (room[0] <= key && key < room[1]) {
                        map.put(key, map.getOrDefault(key, 0) + 1);
                    }
                }
            }

            HashMap<Integer, Integer> newMap = new HashMap<>();

            int pastKey = roomLower;
            int acc = 0; //map.get(roomLower);
            newMap.put(roomLower, 0);

            int distance = -1;

            for (Integer key : map.keySet()) {
                distance = key - pastKey;
                acc += map.get(pastKey) * distance;
                newMap.put(key, acc);
                pastKey = key;
            }

            acc += map.get(roomUpper) * distance;
            newMap.put(roomUpper, acc);

            ArrayList<Integer> vals = new ArrayList<>(newMap.values());

            bufferedWriter.write("Case #" + (j + 1) + ": ");
            bufferedWriter.newLine();

            int[] arr = map.keySet().stream().mapToInt(Integer::intValue).toArray();

            for (int i = 0; i < f; i++) {

                int friend = Integer.parseInt(bufferedReader.readLine()) - 1;

                // binary search approach: https://leetcode.com/explore/learn/card/binary-search/125/template-i/938/
                int l = 0;
                int r = arr.length - 1;
                int mid = -1;

                while (l <= r) {

                    mid = l + (r - l) / 2;
//                    mid = (r + l + 1) / 2;

                    if (vals.get(mid) < friend) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }

                }

                int beginning = arr[mid];
                int height = map.get(beginning);
                int before = newMap.get(beginning);

                int value = beginning + (friend - before) / height;

                bufferedWriter.write(String.valueOf(value));
                bufferedWriter.newLine();
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

        }

        bufferedReader.close();
        bufferedWriter.close();

    }

}
