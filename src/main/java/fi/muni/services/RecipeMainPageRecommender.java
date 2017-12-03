package fi.muni.services;

import fi.muni.DAO.IRecipeDAO;
import fi.muni.entities.Recipe;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by peter on 30.11.17.
 */
@Service
public class RecipeMainPageRecommender {

    @Autowired
    IRecipeDAO recipeDAO;

    private static Map<String, Float> recipesWeights = new HashMap<>();
    private static Map<Integer, Float> cumulativeTimeDistribution = new HashMap<>();
    private static final int NUMBER_OF_INGREDIENTS = 3559;
    private static final float WEIGHT_OF_INGREDIENTS = 0.8f;
    private static final float WEIGHT_OF_TIME = 0.2f;
    private static final int PRECISION = 10; // higher number = better result, but more computational time
    private static final int AMOUNT = 30;

    public List<Recipe> recommend() {
        //compute needed data information
        calculateWeights();
        timeNeededIndex();

        Map<Integer, Pair<Integer, Set<String>>> actualRecipesData = new HashMap<>();
        List<Recipe> candidateRecipes;
        List<Recipe> resultRecipes = recipeDAO.findRandomRecipes(1);

        actualRecipesData.put(resultRecipes.get(0).getId(), dataToInsert(resultRecipes.get(0).getId()));

        float lowestSimilarity;
        int indexLowestSimilarity;
        float tmpSimilarity;

        for (int n = 0; n < AMOUNT -1 ; n++) {
            lowestSimilarity = 1;
            indexLowestSimilarity = 0;
            candidateRecipes = recipeDAO.findRandomRecipes(PRECISION);

            for (int i = 0; i < PRECISION; i++) {
                tmpSimilarity = 0;
                for (Integer j: actualRecipesData.keySet()){
                    tmpSimilarity += countSimilarity(actualRecipesData.get(j), candidateRecipes.get(i).getId());
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



    private void calculateWeights(){
        List<Object[]> tmpIngredients = recipeDAO.findIngredientsCount();
        float weight;

        for (int i=0; i < tmpIngredients.size(); i++) {
            if (i+2 >= NUMBER_OF_INGREDIENTS) weight = 1.0f;
            else weight = (float)Math.pow(Math.log((float) i+2) / Math.log((float) NUMBER_OF_INGREDIENTS),2);
            recipesWeights.put((String)tmpIngredients.get(i)[0], weight);
        }
    }

    private float countSimilarity(Pair<Integer,Set<String>> data1, int id2) {
        List<Object[]> recipe2data = recipeDAO.findRecipeIngredients(id2);

        Set<String> ingredients2 = new HashSet<>();
        for (int i=0; i < recipe2data.size(); i++){
            ingredients2.add((String)recipe2data.get(i)[1]);
        }
        float jIndex = calculateIndex(data1.getValue(), ingredients2);
        float timeNeeded = (float)Math.abs(cumulativeTimeDistribution.get(data1.getKey()) - cumulativeTimeDistribution.get(recipe2data.get(0)[2]));
        return WEIGHT_OF_INGREDIENTS * jIndex + WEIGHT_OF_TIME * timeNeeded;
    }

    // Integer is "time needed", Set is "ingredients" set
    private Pair<Integer,Set<String>> dataToInsert(int id){
        List<Object[]> data = recipeDAO.findRecipeIngredients(id);
        Set<String> resultSet = new HashSet<>();
        for (int i=0; i < data.size(); i++){
            resultSet.add((String)data.get(i)[1]);
        }
        int b = (Integer)data.get(0)[2];
        return new Pair<>((Integer)data.get(0)[2], resultSet);
    }

    // Jaccard index
    private float calculateIndex(Set<String> i1, Set<String> i2){
        float intersection = 0;
        float union = 0;
        for (String s: i1){
            if (i2.contains(s)) intersection += recipesWeights.get(s);
            union += recipesWeights.get(s);
        }
        for (String s: i2){
            union += recipesWeights.get(s);
        }
        return (intersection/(union-intersection));
    }

   public void timeNeededIndex() {
        List<Object[]> tmpTimeNeeded = recipeDAO.findTimeNeeded();
        Integer lessTime = 0;
        for (int i = 0; i < tmpTimeNeeded.size(); i++) {
            BigInteger bi = (BigInteger) tmpTimeNeeded.get(i)[0];
            lessTime += bi.intValue();
            cumulativeTimeDistribution.put((Integer)tmpTimeNeeded.get(i)[1], (float) lessTime / 13083);
        }
    }
}
