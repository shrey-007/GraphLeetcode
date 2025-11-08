import java.util.ArrayList;
import java.util.List;

public class CourseScheduleIV {
    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an
     * array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course ai first if you want
     * to take course bi.
     *
     * For example, the pair [0, 1] indicates that you have to take course 0 before you can take course 1.
     * Prerequisites can also be indirect. If course a is a prerequisite of course b, and course b is a prerequisite of
     * course c, then course a is a prerequisite of course c.
     *
     * You are also given an array queries where queries[j] = [uj, vj]. For the jth query, you should answer whether
     * course uj is a prerequisite of course vj or not.
     *
     * Return a boolean array answer, where answer[j] is the answer to the jth query.
     * */
    // Imagine if the courses are nodes of a graph. We need to build an array isReachable[i][j].
    // Start a bfs from each course i and assign for each course j you visit isReachable[i][j] = True.
    // Answer the queries from the isReachable array.
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        int n = numCourses;
        boolean isPrerequisite[][] = new boolean[n][n];
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // first create the graph of prerequisite
        for (int i = 0; i < prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];
            adj.get(u).add(v);
        }

        // then do dfs through each node
        for(int i=0;i<n;i++){
            int visited[] = new int[n];
            dfs(i,adj,visited,i,isPrerequisite);
        }

        int k = queries.length;
        List<Boolean> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            ans.add(isPrerequisite[queries[i][0]][queries[i][1]]);
        }

        return ans;
    }

    public void dfs(int node,List<List<Integer>> adj,int visited[],int ultimateNode,boolean isPrerequisite[][]){
        visited[node] = 1;
        isPrerequisite[ultimateNode][node] = true;

        int n = adj.get(node).size();
        for (int i = 0; i <n ; i++) {
            int neigbour = adj.get(node).get(i);
            if(visited[neigbour]==0) dfs(neigbour,adj,visited,ultimateNode,isPrerequisite);
        }
    }
}
