package fi.muni.services;

import com.google.common.collect.Lists;
import fi.muni.DAO.IRecipeDAO;
import fi.muni.DTOs.RecipeDTO;
import fi.muni.entities.Flavor;
import fi.muni.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by peter on 30.11.17.
 */
@Service
public class RecipeFlavorRecommender implements IRecipeRecommender {

    @Autowired
    IRecipeDAO recipeDAO;

    public static final int NUMBER_OF_RECIPES = 8;

    @Override
    public List<Recipe> recommend(Integer id) {

        List<Recipe> randomRecipes = recipeDAO.findRandomRecipes(2000);
        Recipe chosenRecipe = recipeDAO.findOne(id);
        List<Recipe> recommendedRecipes = randomRecipes.stream()
                .sorted((r1, r2) -> Double.compare(recipesDifference(r1, chosenRecipe), recipesDifference(r2, chosenRecipe)))
                .limit(NUMBER_OF_RECIPES)
                .collect(Collectors.toList());
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
