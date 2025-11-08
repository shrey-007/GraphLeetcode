package ShortestPath;

import java.util.Arrays;

/**
 * You are given n cities numbered from 0 to n-1 and an array edges where each edges[i] = [from_i, to_i, weight_i]
 * represents a bidirectional and weighted road between city from_i and city to_i with a distance of weight_i.
 *
 * You are also given an integer distanceThreshold, which represents the maximum distance you can travel from any city.
 *
 * Your task is to return the city that has the smallest number of other cities reachable within a distance of at most
 * distanceThreshold. If there are multiple cities with the same number of reachable cities, return the city with the
 * greatest index.
 *
 * Note: The distance between two cities i and j is the sum of the weights of the edges along the shortest path between
 * them.
 * */
public class FindTheCityWithTheSmallestNumberOfNeighborsAtThresholdDistance {
    // suppose tum ek city se chalo toh sirf Threshold distance tak hi jaa skte ho
    // toh tum pata kro ki ith city se kitni cities tak jaa skte ho within Threshold distance
    // fir tum dekho esi konsi city hai jisse minimum cities pr jaa skte hai return it

    /**
     * Toh basically tumhe har city(i) ka har city(j) se min distance nikaalna hai tabhi tum pata kr paaoge ki i to j and
     * j to i threshold distance ke under travel kr skte hai ki nhi.
     * So to do this we use Floyd Warshall Algorithm
     * */

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // Step 1: Initialize distance matrix
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE / 2); // Use large value, not Integer.MAX_VALUE to avoid overflow
            dist[i][i] = 0; // Distance to self is 0
        }

        // Fill initial distances with the given edges
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            dist[u][v] = w;
            dist[v][u] = w;
        }

        // Step 2: Apply Floyd-Warshall to compute all-pairs shortest paths
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        // Step 3: Count reachable cities within the distance threshold for each city
        int resultCity = -1;
        int minReachableCities = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int reachableCities = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) {
                    reachableCities++;
                }
            }

            // Step 4: Find the city with the smallest number of reachable cities
            // If there's a tie, return the city with the greatest index
            if (reachableCities < minReachableCities || (reachableCities == minReachableCities && i > resultCity)) {
                minReachableCities = reachableCities;
                resultCity = i;
            }
        }

        return resultCity;
    }

}
