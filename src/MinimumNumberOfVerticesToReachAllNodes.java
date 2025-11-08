import java.util.ArrayList;
import java.util.List;

/**
 * Given a directed acyclic graph, with n vertices numbered from 0 to n-1, and an array edges where edges[i] =
 * [fromi, toi] represents a directed edge from node fromi to node toi.
 *
 * Find the smallest set of vertices from which all nodes in the graph are reachable. It's guaranteed that a unique
 * solution exists.
 *
 * Notice that you can return the vertices in any order.
 * */
public class MinimumNumberOfVerticesToReachAllNodes {
    // node having indegree unko include mt kro ans mai
    // if indegree==0 then we have to include it in ans
    // reason is simple ki suppose a->b hai toh kya tum b ko ans mai include kroge? ans is no, coz a se b jaa skte hai toh a ko include kro b ko mat kro
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {

        int inDegree[] = new int[n];

        int n1 = edges.size();

        for (int i = 0; i < n1; i++) {
            int v = edges.get(i).get(1);
            inDegree[v]++;
        }

        List<Integer> ans =new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if(inDegree[i]==0){
                ans.add(i);
            }
        }

        return ans;
    }
}
