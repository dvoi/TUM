import java.io.*;
import java.util.*;

public class B {

    public static class Facility {
        private final int id;
        private final int cost;

        public Facility(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] nm = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nm[0]); // number of facilities
            int m = Integer.parseInt(nm[1]); // number of customers

            int[] maintenanceCosts = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            HashMap<Integer, TreeSet<Facility>> delivery = new HashMap<>();
            int[][] deliveryCosts = new int[n][m];

            for (int i = 0; i < n; i++) {
                deliveryCosts[i] = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                for (int j = 0; j < deliveryCosts[i].length; j++) {
                    delivery.putIfAbsent(j, new TreeSet<>(Comparator.comparing(Facility::getCost)));
                    delivery.get(j).add(new Facility(i, deliveryCosts[i][j]));
                }
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            // Local Search:
            // https://people.du.ac.in/~ngupta/thesis_manisha.pdf
            HashSet<Integer> facilities = new HashSet<>();
            for (int i = 0; i < n; i++) {
                facilities.add(i);
            }

            HashSet<Integer> currentFacilities = new HashSet<>();
            for (int customer = 0; customer < m; customer++) {
                Facility cheapest = delivery.get(customer).first();
                currentFacilities.add(cheapest.id);
            }

            boolean improved = true;

            while (improved) {

                HashSet<Integer> complementCurrentFacilities = new HashSet<>(facilities);
                complementCurrentFacilities.removeAll(currentFacilities);

                long cost = total(currentFacilities, deliveryCosts, maintenanceCosts);

                improved = false;

                // add
                for (int facility : complementCurrentFacilities) {

                    HashSet<Integer> temp = new HashSet<>(currentFacilities);
                    temp.add(facility);

                    long newCost = total(temp, deliveryCosts, maintenanceCosts);

                    if (newCost < cost) {
                        currentFacilities.add(facility);
                        improved = true;
                        break;
                    }
                }

                // remove
                if (!improved) {

                    for (int facility : currentFacilities) {

                        HashSet<Integer> temp = new HashSet<>(currentFacilities);
                        temp.remove(facility);

                        long newCost = total(temp, deliveryCosts, maintenanceCosts);

                        if (newCost < cost) {
                            currentFacilities.remove(facility);
                            improved = true;
                            break;
                        }
                    }
                }

                // swap
                if (!improved) {

                    HashSet<Integer> safe = new HashSet<>(currentFacilities);

                    for (int out : safe) {

                        for (int in : complementCurrentFacilities) {

                            HashSet<Integer> temp = new HashSet<>(currentFacilities);
                            temp.remove(out);
                            temp.add(in);

                            long newCost = total(temp, deliveryCosts, maintenanceCosts);

                            if (newCost < cost) {
                                currentFacilities.remove(out);
                                currentFacilities.add(in);
                                improved = true;
                                break;
                            }
                        }
                    }
                }
            }

            HashMap<Integer, HashSet<Integer>> customersPerFacility = new HashMap<>();

            for (int facility : currentFacilities) {
                customersPerFacility.put(facility, new HashSet<>());
            }

            for (int customer = 0; customer < m; customer++) {

                int cheapestFacility = -1;
                int cheapestDelivery = Integer.MAX_VALUE;

                for (int facility : currentFacilities) {

                    int deliveryCost = deliveryCosts[facility][customer];

                    if (deliveryCost < cheapestDelivery) {
                        cheapestDelivery = deliveryCost;
                        cheapestFacility = facility;
                    }
                }

                if (customersPerFacility.containsKey(cheapestFacility)) {
                    customersPerFacility.get(cheapestFacility).add(customer);
                }
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + total(currentFacilities, deliveryCosts, maintenanceCosts));
            bufferedWriter.newLine();

            StringBuilder sb = new StringBuilder();

            for (int facility : currentFacilities) {
                if (!customersPerFacility.get(facility).isEmpty()) {
                    sb.append(facility + 1).append(" ");
                    for (Integer customer : customersPerFacility.get(facility)) {
                        sb.append(customer + 1).append(" ");
                    }
                    sb.append("\n");
                }
            }

            bufferedWriter.write(sb.toString());
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }


    public static long total(HashSet<Integer> facilities, int[][] deliveryCosts, int[] maintenanceCosts) {

        long cost = 0;

        for (int customer = 0; customer < deliveryCosts[0].length; customer++) {

            int cheapestDelivery = Integer.MAX_VALUE;

            for (int facility : facilities) {
                cheapestDelivery = Math.min(cheapestDelivery, deliveryCosts[facility][customer]);
            }

            cost += cheapestDelivery;
        }

        for (int facility : facilities) {
            cost += maintenanceCosts[facility];
        }

        return cost;
    }
}