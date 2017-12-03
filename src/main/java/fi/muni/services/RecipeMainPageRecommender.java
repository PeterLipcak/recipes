package fi.muni.services;

import fi.muni.DAO.IRecipeDAO;
import fi.muni.entities.Recipe;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by peter on 30.11.17.
 */
@Service
public class RecipeMainPageRecommender extends IngredientsRecommender{

    private static final int AMOUNT_TO_DISPLAY = 48;
    private static final int PRECISION =100;

    public RecipeMainPageRecommender(IRecipeDAO recipeDAO) {
        super(recipeDAO);
    }

    public List<Recipe> recommend() {
        Map<Integer, Pair<Integer, Set<String>>> actualRecipesData = new HashMap<>();
        List<Recipe> candidateRecipes;
        List<Recipe> resultRecipes = recipeDAO.findRandomRecipes(1);

        actualRecipesData.put(resultRecipes.get(0).getId(), dataToInsert(resultRecipes.get(0).getId()));

        float lowestSimilarity;
        int indexLowestSimilarity;
        float tmpSimilarity;

        for (int n = 0; n < AMOUNT_TO_DISPLAY -1 ; n++) {
            lowestSimilarity = 1;
            indexLowestSimilarity = 0;

            candidateRecipes = getCandidateRecipies(PRECISION);

            for (int i = 0; i < PRECISION; i++) {
                tmpSimilarity = 0;
                for (Integer j: actualRecipesData.keySet()){
                    tmpSimilarity += countSimilarity(actualRecipesData.get(j), candidateRecipes.get(i));
                }
                tmpSimilarity /= actualRecipesData.size();
                if (tmpSimilarity < lowestSimilarity) {
                    lowestSimilarity = tmpSimilarity;
                    indexLowestSimilarity = i;
                }
            }
            actualRecipesData.put(candidateRecipes.get(indexLowestSimilarity).getId(), dataToInsert(candidateRecipes.get(indexLowestSimilarity).getId()));
            resultRecipes.add(candidateRecipes.get(indexLowestSimilarity));
        }
        return resultRecipes;
    }
}
