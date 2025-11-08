package ShortestPath;

/**
 *
 * You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed
 * edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a
 * signal to travel from source to target.
 *
 * We will send a signal from a given node k. Return the minimum time it takes for all the n nodes to receive the signal.
 * If it is impossible for all the n nodes to receive the signal, return -1.
 *
 * */
public class NetworkDelayTime {
    // since the graph is weighted graph so we will use Dijkstra. It is same as rotten oranges, but usme time 1 second
    // badta tha, yaha weight ke according badega do don't use BFS, use Dijkstra
    public int networkDelayTime(int[][] times, int n, int k) {
        return 0;
    }

    /**
     * Steps-:
     * 1. Create adjacency list
     * 2. Start with source node(k). and perform normal dijkstra
     * 3. After performing, distance array mai apne paas timing hai ki k se kisi bhi node i jaane mai dist[i] time laga signal ko
     * 4. Now poore distance array ka jo maximum time hai use return krdo.
     * 5. Also since you are using dijkstra, try to apply some rejections.
     * */

}
