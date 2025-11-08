package ShortestPath;

import java.util.*;

/**
 * You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some
 * intersections. The inputs are generated such that you can reach any intersection from any other intersection and that
 * there is at most one road between any two intersections.
 *
 * You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a road
 * between intersections ui and vi that takes timei minutes to travel. You want to know in how many ways you can travel
 * from intersection 0 to intersection n - 1 in the shortest amount of time.
 *
 * Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer may be
 * large, return it modulo 109 + 7.
 * */
public class NumberOfWaysToArriveAtDestination {

    public int countPaths(int n, int[][] roads) {
        // Create an adjacency list for the graph
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        // Dijkstra's algorithm with counting paths
        int MOD = 1_000_000_007;
        long[] dist = new long[n]; // dist[i] stores the shortest time to reach node i
        long[] count = new long[n]; // count[i] stores the number of ways to reach node i in the shortest time
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        count[0] = 1; // Start from node 0, 0th node se 0th node pr aane ka ek hi way hai so count[0]=1

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1])); // (node, distance)
        pq.offer(new long[]{0, 0}); // Start from node 0 with distance 0

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            int u = (int) curr[0];
            long currDist = curr[1];

            if (currDist > dist[u]) continue; // If we've already found a shorter path, skip(rejection)

            // Process neighbors of node u
            for (int[] neighbor : graph[u]) {
                int v = neighbor[0];
                int weight = neighbor[1];
                long newDist = currDist + weight;

                // If a shorter path to v is found
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    count[v] = count[u]; // Reset count of ways to reach v
                    // suppose pehle v tak aane ka dist=5 and count=2. But ab v tak aane ka dist=4 ho gya toh purana
                    // count toh faltu hai kiuki voh ye bata rha hai ki 5 unit mai v tak pahuchne ke kitne ways hai but
                    // ab apan ko pata krna hai ki 4 unit mai v tak pahuchne ke kitne ways hai.
                    // since u se v pr aaye hai toh jitne way u tak pahuchne utne hi v tak pahuchne ke hoge
                    pq.offer(new long[]{v, newDist});
                } else if (newDist == dist[v]) {
                    // If another shortest path to v is found, since another path is found jisme time utna hi lagega
                    // so add that path
                    count[v] = (count[v] + count[u]) % MOD;
                }
            }
        }

        return (int) count[n - 1]; // Return number of ways to reach the last node (destination)
    }
}
