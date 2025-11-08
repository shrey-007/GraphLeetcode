package ShortestPath;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * You are given an array start where start = [startX, startY] represents your initial position (startX, startY) in a
 * 2D space. You are also given the array target where target = [targetX, targetY] represents your target position
 * (targetX, targetY).
 *
 * The cost of going from a position (x1, y1) to any other position in the space (x2, y2) is |x2 - x1| + |y2 - y1|.
 *
 * There are also some special roads. You are given a 2D array specialRoads where specialRoads[i] = [x1i, y1i, x2i, y2i, costi]
 * indicates that the ith special road goes in one direction from (x1i, y1i) to (x2i, y2i) with a cost equal to costi.
 * You can use each special road any number of times.
 *
 * Return the minimum cost required to go from (startX, startY) to (targetX, targetY).
 * */
public class MinimumCostOfAPathWithSpecialRoads {

    // ye dekh kr lgta hai ki DP ka question hai, recursion se kro
    // but recursion all possible paths dekhegi toh best hai ki dijkstra se kro coz graph weighted hai toh greedy method
    // se shortest path find krega dijkstra

    // dont do using below method, bahut time complexity hai
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        PriorityQueue<Point> priorityQueue = new PriorityQueue();
        // hume distance array initialise krne ke liye size pata krna hoga, but voh given nhi hai question mai,
        // toh array ki jagah map use kro, Also apan ko (x,y) tak pahuchne ka min dist store krna hai map mai toh voh
        // string->int mai krege where string=x+y
        Map<String,Integer> dist = new HashMap<>();

        priorityQueue.offer(new Point(start[0],start[1],0));
        dist.put(start[0] + "," + start[1], 0);

        while (!priorityQueue.isEmpty()){
            Point point = priorityQueue.poll();
            int currX = point.x;
            int currY = point.y;
            int currDist = point.distance;

            if(currX==target[0] && currY==target[1]){
                return currDist;
            }

            // Direct move to target (Manhattan distance)
            int targetCost = currDist + Math.abs(target[0] - currX) + Math.abs(target[1] - currY);
            String targetKey = target[0] + "," + target[1];
            if (!dist.containsKey(targetKey) || targetCost < dist.get(targetKey)) {
                dist.put(targetKey, targetCost);
                priorityQueue.add(new Point(target[0], target[1], targetCost));
            }

            // Consider all special roads
            for (int[] road : specialRoads) {
                int x1 = road[0], y1 = road[1], x2 = road[2], y2 = road[3], roadCost = road[4];

                // First, move to the start of the special road (x1, y1)
                int toSpecialRoadCost = currDist + Math.abs(x1 - currX) + Math.abs(y1 - currY);
                String specialStartKey = x1 + "," + y1;

                // Then, take the special road to (x2, y2)
                int totalCost = toSpecialRoadCost + roadCost;
                String specialEndKey = x2 + "," + y2;

                // Update cost for reaching (x2, y2) if this path is cheaper
                if (!dist.containsKey(specialEndKey) || totalCost < dist.get(specialEndKey)) {
                    dist.put(specialEndKey, totalCost);
                    priorityQueue.add(new Point(x2, y2, totalCost));
                }
            }


        }

        return -1; // If the target is unreachable (should not happen)

    }

    class Point implements Comparable<Point>{
        int x;
        int y;
        int distance;

        public Point(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public int compareTo(Point o) {
            return this.distance-o.distance;
        }
    }
}
