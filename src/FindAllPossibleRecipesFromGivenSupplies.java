import java.util.*;

class FindAllPossibleRecipesFromGivenSupplies {
    /**
     * Finds all possible recipes that can be made using the given supplies and recipe dependencies.
     * <p>
     * Each recipe has a list of ingredients. You are initially given a list of available supplies.
     * A recipe can be made if all of its ingredients are either in the initial supplies or are other
     * recipes that can be produced. Once a recipe becomes makeable, it is treated as a new supply
     * that can help in making further recipes.
     * </p>
     *
     * <h2>Algorithm Overview</h2>
     * <ul>
     *   <li>Build a dependency graph from ingredient → recipes that depend on that ingredient.</li>
     *   <li>Maintain an indegree map (recipe → number of ingredients still required).</li>
     *   <li>Initialize a queue with all available supplies.</li>
     *   <li>Process the queue (BFS): for each available item, decrement the indegree of all dependent recipes.
     *       When a recipe’s indegree reaches zero, it means all its ingredients are available, so the recipe
     *       can be made and added to the queue as a new supply.</li>
     *   <li>Continue until all reachable recipes have been processed, then return the list of makeable recipes.</li>
     * </ul>
     *
     * <h2>Data Structures Used</h2>
     * <ul>
     *   <li><b>Map&lt;String, List&lt;String&gt;&gt; graph</b> —
     *       Maps each ingredient to the list of recipes that depend on it.
     *       This allows fast lookup of affected recipes when an ingredient becomes available (O(1) expected access).</li>
     *
     *   <li><b>Map&lt;String, Integer&gt; indegree</b> —
     *       Stores, for each recipe, how many ingredients are still needed before it can be made.
     *       Acts as the indegree counter in Kahn’s topological sort algorithm.</li>
     *
     *   <li><b>Queue&lt;String&gt; queue</b> —
     *       A BFS queue (implemented as a {@link LinkedList}) that stores all currently available items,
     *       including both supplies and recipes that have become producible.</li>
     *
     *   <li><b>Set&lt;String&gt; available</b> —
     *       Tracks all items that are available or have been processed.
     *       Allows O(1) membership checks to prevent redundant queue insertions (though not strictly required here).</li>
     *
     *   <li><b>List&lt;String&gt; result</b> —
     *       Stores all recipes that can ultimately be made. Returned as the final output.</li>
     * </ul>
     *
     * <h2>Algorithm Steps</h2>
     * <ol>
     *   <li><b>Build Graph and Indegree:</b><br>
     *       For each recipe <code>r</code>, set <code>indegree[r]</code> equal to the number of its ingredients.
     *       For each ingredient, append <code>r</code> to <code>graph[ingredient]</code> to record the dependency.</li>
     *
     *   <li><b>Seed Queue with Initial Supplies:</b><br>
     *       Add all supplies to the queue and mark them as available.</li>
     *
     *   <li><b>BFS Propagation:</b><br>
     *       While the queue is not empty:
     *       <ul>
     *         <li>Poll an available item.</li>
     *         <li>For each recipe dependent on that item, decrement its indegree.</li>
     *         <li>If a recipe’s indegree becomes 0, it can now be made — add it to the result and queue.</li>
     *       </ul>
     *   </li>
     *
     *   <li><b>Return:</b><br>
     *       Return all recipes that were marked as makeable during the process.</li>
     * </ol>
     *
     * <h2>Time Complexity</h2>
     * O(R + E + S), where R = number of recipes, E = total number of ingredient dependencies,
     * and S = number of initial supplies.
     *
     * <h2>Space Complexity</h2>
     * O(R + E + U), where U = total number of unique ingredients and recipes.
     *
     * @param recipes     Array of recipe names.
     * @param ingredients List of ingredient lists, where each sublist corresponds to the ingredients for a recipe.
     * @param supplies    Array of initial supplies available.
     * @return List of all recipes that can be made directly or indirectly using the given supplies.
     */

    // no need for Set<String> available
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
            for (String ingredient : ingredients.get(i)) {
                graph.computeIfAbsent(ingredient, k -> new ArrayList<>()).add(recipes[i]);
            }
        }

        // Step 2: Add all available supplies to the queue
        Queue<String> queue = new LinkedList<>();

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
                }
            }
        }

        // Step 4: return all recipes that can be made
        return result;
    }
}
