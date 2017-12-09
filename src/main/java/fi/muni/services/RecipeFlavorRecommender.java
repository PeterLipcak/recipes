package fi.muni.services;

import com.google.common.base.Stopwatch;
import fi.muni.DAO.IRecipeDAO;
import fi.muni.entities.Flavor;
import fi.muni.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by peter on 30.11.17.
 */
@Service
public class RecipeFlavorRecommender {

    @Autowired
    IRecipeDAO recipeDAO;

    public static final int NUMBER_OF_RECIPES = 4;

    List<Recipe> randomRecipes;

    public RecipeFlavorRecommender(IRecipeDAO recipeDAO)
    {
        this.recipeDAO = recipeDAO;
        randomRecipes = recipeDAO.findAll();
    }

    public List<Recipe> recommend(Integer id, Set<Integer> ingredientRecipesIDs) {
        long startTime = System.currentTimeMillis();
        Collections.shuffle(randomRecipes);
        Recipe chosenRecipe = recipeDAO.findOne(id);
        List<Recipe> recommendedRecipes = randomRecipes.stream()
                .filter(recipe -> (!recipe.getRecipeName().equals(chosenRecipe.getRecipeName()) && !ingredientRecipesIDs.contains(recipe.getId())))
                .sorted((r1, r2) -> Double.compare(recipesDifference(r1, chosenRecipe), recipesDifference(r2, chosenRecipe)))
                .limit(NUMBER_OF_RECIPES)
                .collect(Collectors.toList());
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("RecipeFlavorRecommender -> " + elapsedTime);
        return recommendedRecipes;
    }

    private double recipesDifference(Recipe r1, Recipe r2) {
        Flavor recipeFlavor = r1.getFlavors();
        Flavor currFlavor = r2.getFlavors();
        double diff = Math.abs(recipeFlavor.getPiquant() - currFlavor.getPiquant());
        diff += Math.abs(recipeFlavor.getBitter() - currFlavor.getBitter());
        diff += Math.abs(recipeFlavor.getMeaty() - currFlavor.getMeaty());
        diff += Math.abs(recipeFlavor.getSalty() - currFlavor.getSalty());
        diff += Math.abs(recipeFlavor.getSour() - currFlavor.getSour());
        diff += Math.abs(recipeFlavor.getSweet() - currFlavor.getSweet());
        return diff;
    }

}
