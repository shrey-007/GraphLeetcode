import java.util.*;

class FindAllPossibleRecipesFromGivenSupplies {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        int n = recipes.length;

        // graph: ingredient -> list of recipes that depend on this ingredient
        Map<String, List<String>> graph = new HashMap<>();

        // indegree: recipe -> number of ingredients still needed
        Map<String, Integer> indegree = new HashMap<>();

        // Step 1: Build graph and indegree map
        for (int i = 0; i < n; i++) {
            // initially, each recipe needs all its ingredients
            indegree.put(recipes[i], ingredients.get(i).size());

            // for each ingredient, mark that it is used by this recipe
            for (String ing : ingredients.get(i)) {
                graph.computeIfAbsent(ing, k -> new ArrayList<>()).add(recipes[i]);
            }
        }

        // Step 2: Add all available supplies to the queue
        Queue<String> queue = new LinkedList<>();
        Set<String> available = new HashSet<>(Arrays.asList(supplies));

        for (String s : supplies) {
            queue.add(s);
        }

        // result list to store recipes that can be made
        List<String> result = new ArrayList<>();

        // Step 3: Process items we currently have
        while (!queue.isEmpty()) {
            String item = queue.poll();

            // if no recipe depends on this item, skip it
            if (!graph.containsKey(item)) continue;

            // check all recipes that need this item
            for (String recipe : graph.get(item)) {
                // one ingredient for this recipe is now fulfilled
                indegree.put(recipe, indegree.get(recipe) - 1);

                // if indegree becomes 0 → all ingredients are ready → recipe can be made
                if (indegree.get(recipe) == 0) {
                    result.add(recipe);      // add to result list
                    queue.add(recipe);       // recipe becomes a new supply
                    available.add(recipe);   // mark as available for others
                }
            }
        }

        // Step 4: return all recipes that can be made
        return result;
    }
}
