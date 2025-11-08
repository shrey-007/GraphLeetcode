//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//public class FindAllPossibleRecipesFromGivenSupplies {
//    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
//        HashMap<String,Node> hashMap = new HashMap<>();
//
//        int s = supplies.length;
//        for (int i = 0; i < s; i++) {
//            hashMap.put(supplies[i],new Node(supplies[i]));
//        }
//
//        int r = recipes.length;
//        for (int i = 0; i < r; i++) {
//            hashMap.put(recipes[i],new Node(recipes[i]));
//        }
//
//        System.out.println(hashMap);
//
//        for (int i = 0; i < r; i++) {
//            String recipe = recipes[i];
//            List<String> ingredientsReq = ingredients.get(i);
//
//            Node recipeNode = hashMap.get(recipe);
//            int k = ingredientsReq.size();
//
//            for (int j = 0; j < k; j++) {
//                Node incredientNode = hashMap.get(ingredientsReq.get(j));
//                recipeNode.require.add(incredientNode);
//            }
//        }
//
//        boolean isPrepared[] = new boolean[r];
//    }
//
//    public void func(Node recipe,boolean isPrepared[],HashMap<String,Node> hashMap){
//        // prepare current recipe
//        List<Node> incredients = recipe.require;
//        int n = incredients.size();
//        for (int i = 0; i <n ; i++) {
//            if(isPrepared[incredients.get(i).])
//        }
//
//    }
//    class Node{
//        String item;
//        List<Node> require;
//
//        public Node(String item, List<Node> require) {
//            this.item = item;
//            this.require = require;
//        }
//
//        public Node(String item) {
//            this.item = item;
//        }
//
//        @Override
//        public String toString() {
//            return "Node{" +
//                    "item='" + item + '\'' +
//                    ", require=" + require +
//                    '}';
//        }
//    }
//}
