package fi.muni.services;

import fi.muni.DAO.IRecipeDAO;
import fi.muni.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by peter on 30.11.17.
 */
@Service
public class RecipeMainPageRecommender extends IngredientsRecommender{

    private static final int AMOUNT_TO_DISPLAY = 100;
    private static final int PRECISION =20;

    @Autowired
    public RecipeMainPageRecommender(IRecipeDAO recipeDAO) {
        super(recipeDAO);
    }

    public List<Recipe> recommend() {
        long startTime = System.currentTimeMillis();
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
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("RecipeMainPageRecommender -> " + elapsedTime);
        return resultRecipes;
    }
}
