import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * In this problem, a tree is an undirected graph that is connected and has no cycles.
 *
 * You are given a graph that started as a tree with n nodes labeled from 1 to n, with one additional edge added. The
 * added edge has two different vertices chosen from 1 to n, and was not an edge that already existed. The graph is
 * represented as an array edges of length n where edges[i] = [ai, bi] indicates that there is an edge between nodes ai
 * and bi in the graph.
 *
 * Return an edge that can be removed so that the resulting graph is a tree of n nodes. If there are multiple answers,
 * return the answer that occurs last in the input.
 * */
public class RedundantConnection {

    // It is a simple disjoint set question
    // The catch is you can't solve this question using Hashset
    // suppose {1,2} , {3,4} belong to 2 different sets. Hashset contains {1,2,3,4}. Now a edge comes (2,3) which according
    // to hashset is a redundant connection coz both 2,3 are already taken but they both are different set so to connect
    // those 2 sets we need this (2,3) edge. That's why it will be solved using Disjoint set only

    public int[] findRedundantConnection(int[][] edges) {

        int n=edges.length;
        Disjoint disjoint = new Disjoint(n+1);

        // traverse all edges
        for (int i = 0; i < n; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            // if ultimate parent of u and v are same means they are in same set, means pehle se hi u and v connected hai
            // and ab ek new edge aa gyi u and v ki so this is redundant connection
            // so return answer
            if(disjoint.findUltimateParent(u)==disjoint.findUltimateParent(v)){
                int ans[] = new int[2];
                ans[0]=u;
                ans[1]=v;
                return ans;
            }

            // add u and v to same set

            disjoint.unionByRank(u,v);
        }

        return new int[2];

    }


    class Disjoint{
        int v;
        List<Integer> parent;
        List<Integer> rank;

        Disjoint(int v){
            this.v=v;
            parent = new ArrayList<>();
            rank = new ArrayList<>();
            for(int i=0;i<=v;i++){
                parent.add(i);
                rank.add(0);
            }
        }

        public int findUltimateParent(int u){
            if(parent.get(u)==u){return u;}

            int tempParent = parent.get(u);
            int tempParent2 = findUltimateParent(tempParent);
            parent.set(u,tempParent2);

            return tempParent2;
        }

        public void unionByRank(int u,int v){
            int ultimateU = findUltimateParent(u);
            int ultimateV = findUltimateParent(v);

            if (ultimateU == ultimateV) return;

            if (rank.get(ultimateU) < rank.get(ultimateV)) {
                parent.set(ultimateU, ultimateV);
            } else if (rank.get(ultimateU) > rank.get(ultimateV)) {
                parent.set(ultimateV, ultimateU);
            } else {
                parent.set(ultimateU, ultimateV);
                rank.set(ultimateV, rank.get(ultimateV) + 1);
            }

        }



    }


}
