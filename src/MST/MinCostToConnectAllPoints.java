package MST;

import java.util.*;

/**
 * You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [xi, yi].
 *
 * The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them: |xi - xj| + |yi - yj|,
 * where |val| denotes the absolute value of val.
 *
 * Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path
 * between any two points.
 * */
public class MinCostToConnectAllPoints {
    public int minCostConnectPoints(int[][] points) {

        // to create MST, we first need weight of all edges. Here weight is Manhattan Distance
        // first find weights of all edges and put them in priorityQueue
        List<List<List<Integer>>> edges = new ArrayList<>();

        int n = points.length;

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        // isme edges toh di nhi hai, but we can connect any point to any other point toh saare edges hai.
        // there are n points, toh har i ka j se undirected adjacency list mai daalo
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int x1 = points[i][0];
                int y1 = points[i][1];
                int x2 = points[j][0];
                int y2 = points[j][1];
                int distanceBetweenThem = manhattanDistance(x1,x2,y1,y2);
                // i to j
                edges.get(i).add(List.of(j,distanceBetweenThem));
                // j to i
                edges.get(j).add(List.of(i,distanceBetweenThem));
            }
        }

        PriorityQueue<PointWithWeight> priorityQueue = new PriorityQueue<>();
        // priorityQueue mai point ka index and uska weight jaaega bas, like 0th point daala with weight 0
        priorityQueue.offer(new PointWithWeight(0,0));

        int visited[] = new int[n]; // to find whether point at index i in points[] array is added in MST or not

        int costToConnectAllPoints = 0;
        // agar saare points connect ho gye toh iteration jldi band krne ke liye isko bhi rakh liya
        int numberOfConnections = 0;

        while (!priorityQueue.isEmpty()){
            PointWithWeight pointWithWeight = priorityQueue.poll();
            int currentPoint = pointWithWeight.point;
            int currentWeight = pointWithWeight.weight;

            // if current point is already included in MST, then skip this iteration
            if(visited[currentPoint]==1){
                continue;
            }

            // if all points are connected, break the loop
            if(numberOfConnections==n){
                break;
            }

            // means current point is not included in MST, but it is in the top of PQ, so include it
            // add the cost
            costToConnectAllPoints = costToConnectAllPoints + currentWeight;
            // add the point to MST
            visited[currentPoint]=1;
            // increase count of connections
            numberOfConnections++;

            // now visit all neighbours which are not added to MST yet
            for (List<Integer> neighbour: edges.get(currentPoint)){
                int neighbourNode = neighbour.get(0);
                int neighbourWeight = neighbour.get(1);

                if(visited[neighbourNode]==0){
                    priorityQueue.offer(new PointWithWeight(neighbourNode,neighbourWeight));
                }
            }

        }

        return costToConnectAllPoints;

    }
    public int manhattanDistance(int x1,int x2,int y1,int y2){
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }

    class PointWithWeight implements Comparable<PointWithWeight>{
        int point;  // this is actually index of the point in points[] array
        int weight;

        public PointWithWeight(int point, int weight) {
            this.point = point;
            this.weight = weight;
        }

        @Override
        public int compareTo(PointWithWeight o) {
            return this.weight-o.weight;
        }
    }

}
