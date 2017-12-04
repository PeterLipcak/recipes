package fi.muni.services;

import fi.muni.DAO.IRecipeDAO;
import fi.muni.entities.Recipe;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by peter on 30.11.17.
 */
@Service
public class RecipeIngredientRecommender extends IngredientsRecommender implements IRecipeRecommender {

    private static final int PRECISION = 400; // higher number = better result, but more computational time
    private static final int AMOUNT_TO_DISPLAY = 8;

    public RecipeIngredientRecommender(IRecipeDAO recipeDAO) {
        super(recipeDAO);
    }

    public List<Recipe> recommend(Integer id) {
        Map<Integer, Pair<Integer, Set<String>>> actualRecipesData = new HashMap<>();
        List<Recipe> candidateRecipes;
        //Recipe r = recipeDAO.getOne(id);
        Set<Recipe> resultRecipes = new HashSet<>();
        List result = new ArrayList();

        actualRecipesData.put(id, dataToInsert(id));

        float highestSimilarity;
        int indexHighestSimilarity;
        float tmpSimilarity;

        for (int n = 0; n < AMOUNT_TO_DISPLAY; n++) {
            highestSimilarity = 0;
            indexHighestSimilarity = 0;

            candidateRecipes = getCandidateRecipies(PRECISION);

            for (int i = 0; i < PRECISION; i++) {
                if (resultRecipes.contains(candidateRecipes.get(i))) continue;
                tmpSimilarity = countSimilarity(actualRecipesData.get(id), candidateRecipes.get(i));
                if (tmpSimilarity > 0.99f) continue;
                if (tmpSimilarity > highestSimilarity) {
                    highestSimilarity = tmpSimilarity;
                    indexHighestSimilarity = i;
                }
            }
            actualRecipesData.put(candidateRecipes.get(indexHighestSimilarity).getId(), dataToInsert(candidateRecipes.get(indexHighestSimilarity).getId()));
            resultRecipes.add(candidateRecipes.get(indexHighestSimilarity));
        }
        result.addAll(resultRecipes);
        return result;
    }
}
