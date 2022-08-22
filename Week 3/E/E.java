import java.io.*;
import java.util.*;

public class E {

    public static class Hallway {

        private final int room;
        private final int minWaterLevel;

        Hallway(int destination, int minWaterLevel) {
            this.room = destination;
            this.minWaterLevel = minWaterLevel;
        }

        int getMinWaterLevel() {
            return minWaterLevel;
        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int c = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < c; t++) {

            String[] nmkl = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nmkl[0]);
            int m = Integer.parseInt(nmkl[1]);
            int k = Integer.parseInt(nmkl[2]);

            int currentWaterLevel = Integer.parseInt(nmkl[3]);

            HashMap<Integer, ArrayList<Hallway>> hallways = new HashMap<>();
            HashMap<Integer, Integer> controlRooms = new HashMap<>();

            for (int i = 0; i < m; i++) {
                String[] abl = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(abl[0]);
                int b = Integer.parseInt(abl[1]);
                int requiredLevel = Integer.parseInt(abl[2]);

                if (a != b) {
                    if (!hallways.containsKey(a)) {
                        hallways.put(a, new ArrayList<>());
                    }
                    hallways.get(a).add(new Hallway(b, requiredLevel));

                    if (!hallways.containsKey(b)) {
                        hallways.put(b, new ArrayList<>());
                    }
                    hallways.get(b).add(new Hallway(a, requiredLevel));
                }
            }

            for (int i = 0; i < k; i++) {
                String[] ad = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(ad[0]);
                int minWaterLevelDrop = Integer.parseInt(ad[1]);

                controlRooms.put(a, minWaterLevelDrop);
            }

            TreeSet<Integer> reachableControlRooms = new TreeSet<>(Comparator.comparing(Integer::intValue));
            PriorityQueue<Hallway> queue = new PriorityQueue<>(Comparator.comparing(Hallway::getMinWaterLevel).reversed());

            HashSet<Integer> visited = new HashSet<>();

            if (hallways.containsKey(1)) {
                for (Hallway hallway : hallways.get(1)) {
                    queue.add(hallway);
                }
            }

            if (controlRooms.containsKey(1)) {
                reachableControlRooms.add(controlRooms.get(1));
            }

            visited.add(1);

            while (!queue.isEmpty()) {

                Hallway currentHallway = queue.peek();

                if (!visited.contains(currentHallway.room)) {

                    if (currentHallway.minWaterLevel >= currentWaterLevel) {

                        queue.poll();

                        visited.add(currentHallway.room);

                        if (controlRooms.containsKey(currentHallway.room)) {
                            reachableControlRooms.add(controlRooms.get(currentHallway.room));
                        }

                        for (Hallway hallway : hallways.get(currentHallway.room)) {
                            if (!visited.contains(hallway.room)) {
                                queue.add(hallway);
                            }
                        }

                    } else {

                        if (reachableControlRooms.isEmpty() || currentHallway.minWaterLevel < reachableControlRooms.first()) {
                            break;
                        }

                        currentWaterLevel = currentHallway.minWaterLevel; // try maintain the maximum water level
                    }

                } else {
                    queue.poll();
                }
            }

            // empty line between tests
            if (t < c - 1) {
                bufferedReader.readLine();
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + (visited.size() == n ? currentWaterLevel : "impossible"));
            bufferedWriter.newLine();
        }


        bufferedReader.close();
        bufferedWriter.close();
    }
}
