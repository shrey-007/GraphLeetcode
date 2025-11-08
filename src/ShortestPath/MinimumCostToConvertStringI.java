package ShortestPath;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * You are given two 0-indexed strings source and target, both of length n and consisting of lowercase English letters.
 * You are also given two 0-indexed character arrays original and changed, and an integer array cost, where cost[i]
 * represents the cost of changing the character original[i] to the character changed[i].
 *
 * You start with the string source. In one operation, you can pick a character x from the string and change it to the
 * character y at a cost of z if there exists any index j such that cost[j] == z, original[j] == x, and changed[j] == y.
 *
 * Return the minimum cost to convert the string source to the string target using any number of operations. If it is
 * impossible to convert source to target, return -1.
 *
 * Note that there may exist indices i, j such that original[j] == original[i] and changed[j] == changed[i].
 * */
public class MinimumCostToConvertStringI {
    // b can be changed to c and e also toh original mai b do baar ho skta hai and alag character se map ho skta hai
    // also b can change to e but that does not mean ki e can change to b, so create directed graph
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {

        // create directed adjacency list, it is of no use
        List<List<List<Integer>>> adj = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            adj.add(new ArrayList<>());
        }

        int n = original.length;
        for (int i = 0; i < n; i++) {
            int u = original[i]-'a';
            int v = changed[i]-'a';
            int price = cost[i];

            adj.get(u).add(List.of(v,price));
        }

        // toh a ke neighbours 0th index pr store hai , b ke neighbours 1 pr and so on
        // now change the string
        long totalCost = 0;
        // read this line later
        long dist[][] = floydWarshall(original,changed,cost);

        int n1 = source.length();
        for (int i = 0; i < n1; i++) {
            char sourceChar = source.charAt(i);
            char targetChar = target.charAt(i);

            // if both are equal then we don't have to do anything
            if(sourceChar==targetChar){continue;}
            // if they are not equal toh hum sourceChar to targetChar jaana hai min cost mai , so apply dijkstra
            // but agar millions of characters changed hai toh millions baar dijsktra run hoga toh isse acha toh
            // floyd warshall use kro , toh har character se char character ki cost pehle se ho calculate krlo
            // also since apan floyd warshall use kr rhe hai toh dijkstra use krne ki need nhi hai, toh adjacency list is of no use
            else{
                long costToConvertSourceToTarget = dist[sourceChar-'a'][targetChar-'a'];
                if(costToConvertSourceToTarget==Long.MAX_VALUE){
                    // means source to target convert nhi ho paaya
                    return -1;
                }
                totalCost = totalCost + costToConvertSourceToTarget;
            }
        }

        return totalCost;
    }

    public long[][] floydWarshall(char[] original,char[] changed,int cost[]){
        long dist[][] = new long[26][26];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dist[i],Long.MAX_VALUE);
            dist[i][i] = 0;
        }

        // Fill the cost matrix with the given conversions
        int n = original.length;
        for (int i = 0; i < n; i++) {
            int u = original[i] - 'a';
            int v = changed[i] - 'a';
            dist[u][v] = Math.min(dist[u][v], cost[i]); // Take the minimum cost if multiple conversions exist
        }

        // Apply Floyd-Warshall to find the minimum cost between every pair of characters
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    if (dist[i][k] != Long.MAX_VALUE && dist[k][j] != Long.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        return dist;
    }
}
