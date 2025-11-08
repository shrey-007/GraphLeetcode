import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * You are given a list of bombs. The range of a bomb is defined as the area where its effect can be felt. This area is
 * in the shape of a circle with the center as the location of the bomb.
 *
 * The bombs are represented by a 0-indexed 2D integer array bombs where bombs[i] = [xi, yi, ri]. xi and yi denote the
 * X-coordinate and Y-coordinate of the location of the ith bomb, whereas ri denotes the radius of its range.
 *
 * You may choose to detonate a single bomb. When a bomb is detonated, it will detonate all bombs that lie in its range.
 * These bombs will further detonate the bombs that lie in their ranges.
 *
 * Given the list of bombs, return the maximum number of bombs that can be detonated if you are allowed to detonate only
 * one bomb.
 * */
public class DetonateTheMaximumBombs {
    public int maximumDetonation(int[][] bombs) {

        int n = bombs.length;

        // create adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            int currentBomb = i;
            int currentX = bombs[i][0];
            int currentY = bombs[i][1];
            int currentRadius = bombs[i][2];
            for (int j = i+1; j < n; j++) {
                int nextBomb = j;
                int nextX = bombs[j][0];
                int nextY = bombs[j][1];
                int nextRadius = bombs[j][2];

                double distanceBetweenBombs = calculateDistance(currentX,currentY,nextX,nextY);
                if(currentRadius>=distanceBetweenBombs){
                    adj.get(currentBomb).add(nextBomb);
                }
                if(nextRadius>=distanceBetweenBombs){
                    adj.get(nextBomb).add(currentBomb);
                }
            }
        }

        System.out.println(adj);
        // now since adjacency list is prepared, we have to check ki kis node se start krne se hum max nodes tak pahuch skte hai
        int ans = 1;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans,bfs(i,adj,n));
        }

        return ans;
    }

    public int bfs(int node, List<List<Integer>> adj,int n){
        int visited[] = new int[n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(node);
        visited[node]=1;
        int count = 1;

        while (!queue.isEmpty()){
            int currNode = queue.poll();

            for(int neighbour: adj.get(currNode)){
                if(visited[neighbour]==0){
                    count++;
                    queue.offer(neighbour);
                    visited[neighbour]=1;
                }
            }
        }

        return count;
    }

    public double calculateDistance(int x1, int y1, int x2, int y2) {
        // Using the distance formula: sqrt((x2 - x1)^2 + (y2 - y1)^2)
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
