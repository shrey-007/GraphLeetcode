import java.util.*;

/**
 * You have n gardens, labeled from 1 to n, and an array paths where paths[i] = [xi, yi] describes a bidirectional path
 * between garden xi to garden yi. In each garden, you want to plant one of 4 types of flowers.
 *
 * All gardens have at most 3 paths coming into or leaving it.
 *
 * Your task is to choose a flower type for each garden such that, for any two gardens connected by a path, they have
 * different types of flowers.
 *
 * Return any such a choice as an array answer, where answer[i] is the type of flower planted in the (i+1)th garden.
 * The flower types are denoted 1, 2, 3, or 4. It is guaranteed an answer exists.
 * */
public class FlowerPlantingWithNoAdjacent {
    // suppose ki tumne garden g ko flower 1 diya. Toh ab g ke saare neighbours par iterate kro and unhe 1 mat do
    // suppose g ke 3 neighbours hai. pehle garden ko tumne 2 de diya toh baaki bache valo ko 1,2 nhi de skte toh ye pata kese padega, uske liye ek array bana lena ki ek garden ke adjacent kon kon se flowers lag chuke hai
    // toh har baar us array ko update kro and usko iterate kro te dekhne ke liye ki konsa flower neighbour ko de, it is time consuming
    // toh hum ulta krege, hum dekhege ki neighbours ko konse flowers de diye hai, jo de diye voh current garden mai mt lagao
    public int[] gardenNoAdj(int n, int[][] paths) {

        // create adjacency list
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < n + 1; i++) {
            adj.add(new ArrayList<>());
        }

        int n1 = paths.length;
        for (int i = 0; i < n1; i++) {
            int u = paths[i][0];
            int v = paths[i][1];

            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int visited[] = new int[n+1]; // 0 means no flower, 1,2,3,4 is type of flower

        // start filling from garden1
        for (int garden = 1; garden < n + 1; garden++) {
            // to put  flower in current garden, first find which flower you can't put, measn find tpe of flower in
            // neighbour garden
            boolean[] flowerWeCanNotPut = new boolean[5];

            for (int neighbourGarden: adj.get(garden)){
                int flowerOfNeighbourGarden = visited[neighbourGarden];
                if(flowerOfNeighbourGarden!=0){
                    // means ki is neighbour mai already ek flower hai toh ye flower apan current garden mai nhi daal skte
                    flowerWeCanNotPut[flowerOfNeighbourGarden] = true;
                }
            }

            // now flowerWeCanNotPut[] mai jo flower false hai voh apan current garden mai put kr skte hai
            for (int i = 1; i < 5; i++) {
                if(!flowerWeCanNotPut[i]){
                    // means ki ye flower current garden ke kisi neighbour ke paas nhi hai toh ise current garden mai daal do
                    visited[garden] = i;
                }
            }

        }

        return Arrays.copyOfRange(visited,1,n+1);
    }
}
