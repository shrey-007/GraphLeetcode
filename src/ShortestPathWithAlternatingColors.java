//import java.util.*;
//
///**
// * You are given an integer n, the number of nodes in a directed graph where the nodes are labeled from 0 to n - 1.
// * Each edge is red or blue in this graph, and there could be self-edges and parallel edges.
// *
// * You are given two arrays redEdges and blueEdges where:
// *
// * redEdges[i] = [ai, bi] indicates that there is a directed red edge from node ai to node bi in the graph, and
// *
// * blueEdges[j] = [uj, vj] indicates that there is a directed blue edge from node uj to node vj in the graph.
// *
// * Return an array answer of length n, where each answer[x] is the length of the shortest path from node 0 to node x
// * such that the edge colors alternate along the path, or -1 if such a path does not exist.
// *
// * */
//public class ShortestPathWithAlternatingColors {
//    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
//        // red-1, blue-0
//        List<List<NodeWithColor>> adj = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            adj.add(new ArrayList<>());
//        }
//
//        for (int i = 0; i < redEdges.length; i++) {
//            int u = redEdges[i][0];
//            int v = redEdges[i][1];
//            adj.get(u).add(new NodeWithColor(v,1));
//        }
//
//        for (int i = 0; i < blueEdges.length; i++) {
//            int u = blueEdges[i][0];
//            int v = blueEdges[i][1];
//            adj.get(u).add(new NodeWithColor(v,0));
//        }
//
//        Queue<NodeWithColor> queue = new ArrayDeque<>();
//        queue.offer(new NodeWithColor(0,-1));
//        int visited[][] = new int[n][2];
//
//        while (queue.isEmpty()){
//            NodeWithColor nodeWithColor = queue.poll();
//            int currentNode = nodeWithColor.node;
//            int color = nodeWithColor.color;
//
//            for(NodeWithColor neighbour: adj.get(currentNode)){
//                int neighbourNode = neighbour.node;
//                int edgeColor = neighbour.color;
//
//                if()
//            }
//
//        }
//
//    }
//    class NodeWithColor{
//        int node;
//        int color;
//
//        public NodeWithColor(int node, int color) {
//            this.node = node;
//            this.color = color;
//        }
//    }
//}
