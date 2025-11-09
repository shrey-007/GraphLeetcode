import java.util.*;

/**
 * We want to split a group of n people (labeled from 1 to n) into two groups of any size. Each person may dislike some
 * other people, and they should not go into the same group.
 *
 * Given the integer n and the array dislikes where dislikes[i] = [ai, bi] indicates that the person labeled ai does not
 * like the person labeled bi, return true if it is possible to split everyone into two groups in this way.
 * */
public class PossibleBipartition {
    // it is a simple BFS question just like bipartite graph, adjacent nodes should not be in same group.
    // jo log ek doosre ko hate krte hai unke beech edge daaldo, toh adjacent ho jaaege, fir same group m nhi aaege
    public boolean possibleBipartition(int n, int[][] dislikes) {

        // first create adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <=n ; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < dislikes.length; i++) {
            int u = dislikes[i][0];
            int v = dislikes[i][1];
            // u and v can not in the same group, so create undirected edge
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int visited[]=new int[n+1]; // 0 means not included in any grp, 1 means in 1st grp, 2 means in second grp

        // there could be components in graph , har component ko true return krna chaiye, agar ek ne bhi false return
        // kra toh ans is false
        for (int i = 0; i < n + 1; i++) {
            if(visited[i]==0){
                boolean flag = func(i,adj,visited);
                if(!flag){return false;}
            }
        }

        return true;

    }

    public boolean func(int node,List<List<Integer>> adj,int visited[]){
        // now do a bfs traversal
        Queue<NodeWithGroup> queue = new ArrayDeque<>();
        queue.offer(new NodeWithGroup(node,1)); // first node ko kisi bhi gr mai daal do maine 1st grp mai daal diya
        visited[node]=1;

        while (!queue.isEmpty()){
            NodeWithGroup nodeWithGroup = queue.poll();
            int currentNode = nodeWithGroup.node;
            int currentGroup = nodeWithGroup.group;


            // visit all its neighbours
            for(int neighbour : adj.get(currentNode)){
                // if the neighbour is already in grp, check whether currentNode and neighbour are not in same grp
                if(visited[neighbour]!=0){
                    if(visited[neighbour]==visited[currentNode]){return false;}
                }
                else{
                    // if it is not in any group then include it in opposite grp of currentNode
                    // if currentNode is 1 toh use 2 mai daalo
                    // if currentNode is 2 toh use 1 mi daalo
                    queue.offer(new NodeWithGroup(neighbour,3-currentGroup));
                    visited[neighbour]=3-currentGroup;
                }
            }
        }

        return true;
    }

}

class NodeWithGroup{
    int node;
    int group;

    public NodeWithGroup(int node, int group) {
        this.node = node;
        this.group = group;
    }
}
