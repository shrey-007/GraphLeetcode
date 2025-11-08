import java.util.*;

public class RemoveMethodsFromProject {
    public static List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        Set<Integer> suspiciousNode = getSuspisiousNodes(n,k,invocations);
        System.out.println(suspiciousNode);

        // we want to remove these suspicious method
        Set<Integer> suspiciousNodeWeCanNotDelete = getSuspiciousNodeWeCanNotDelete(n,k,invocations,suspiciousNode);
        System.out.println(suspiciousNodeWeCanNotDelete);

        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if(!suspiciousNode.contains(i)){
                ans.add(i);
            }
            else if(suspiciousNodeWeCanNotDelete.contains(i)){
                ans.add(i);
            }
        }

        System.out.println(ans);

        return ans;
    }

    public static Set<Integer> getSuspisiousNodes(int n,int k,int[][] invocations){

        Set<Integer> suspiciousNode = new HashSet<>();
        int visited[]=new int[n];

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(k);
        visited[k]=1;
        suspiciousNode.add(k);

        while (!queue.isEmpty()){
            int curr = queue.poll();

            for (int i = 0; i < invocations.length; i++) {
                if(invocations[i][0]==curr && visited[invocations[i][1]]==0){
                    queue.offer(invocations[i][1]);
                    visited[invocations[i][1]]=1;
                    suspiciousNode.add(invocations[i][1]);
                }
            }
        }

        return suspiciousNode;
    }

    public static Set<Integer> getSuspiciousNodeWeCanNotDelete(int n,int k,int[][] invocations,Set<Integer> suspiciousNode){

        Set<Integer> suspiciousNodeWeCanNotDelete = new HashSet<>();

        for (int i = 0; i < invocations.length; i++) {
            if(!suspiciousNode.contains(invocations[i][0]) && suspiciousNode.contains(invocations[i][1])){
                // means it is a suspicious Node invocations[i][1] which is called by invocations[i][0], so we can't delete it
                suspiciousNodeWeCanNotDelete.add(invocations[i][1]);
            }
        }

        return suspiciousNodeWeCanNotDelete;
    }

    public static void main(String[] args) {
        int arr[][]={{1,2},{0,1},{3,2}};
        remainingMethods(4,1,arr);
    }

}
