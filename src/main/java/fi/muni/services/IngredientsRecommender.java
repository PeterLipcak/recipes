package fi.muni.services;

import fi.muni.DAO.IRecipeDAO;
import fi.muni.entities.Recipe;
//import javafx.util.Pair;
import org.springframework.data.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.*;

import static org.springframework.data.util.Pair.of;

public abstract class IngredientsRecommender{

    @Autowired
    IRecipeDAO recipeDAO;
    protected static Map<Recipe, Set<String>> joinTable = new HashMap<>();
    private static Map<String, Float> recipesWeights = new HashMap<>();
    private static Map<Integer, Float> cumulativeTimeDistribution = new HashMap<>();
    private static final int NUMBER_OF_INGREDIENTS = 3559;
    private static final float WEIGHT_OF_INGREDIENTS = 0.8f;
    private static final float WEIGHT_OF_TIME = 0.2f;

    public IngredientsRecommender(IRecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
        calculateWeights();
        timeNeededIndex();
        findAllRecipes();
    }

    List<Recipe> getCandidateRecipies(int amount) {
        List<Recipe> resultList = new ArrayList<>();
        Random generator = new Random();
        Recipe[] keys = joinTable.keySet().toArray(new Recipe[amount]);
        Recipe recipe;
        for (int i = 0; i < amount; i++){
            recipe = keys[generator.nextInt(keys.length)];
            resultList.add(recipe);
        }
        return  resultList;
    }

    private void findAllRecipes(){
        List<Recipe> recipes = recipeDAO.findAll();
        Set<String> ingredientsSet;
        for (Recipe r : recipes) {
            List<Object[]> data = recipeDAO.findRecipeIngredients(r.getId());
            ingredientsSet = new HashSet<>();
            for (int j = 0; j < data.size(); j++) {
                ingredientsSet.add((String)data.get(j)[1]);
            }
            joinTable.put(r, ingredientsSet);
        }
    }

    private void calculateWeights(){
        List<Object[]> tmpIngredients = recipeDAO.findIngredientsCount();
        float weight;

        for (int i=0; i < tmpIngredients.size(); i++) {
            if (i+2 >= NUMBER_OF_INGREDIENTS) weight = 1.0f;
            else weight = (float)Math.pow(Math.log((float) i+2) / Math.log((float) NUMBER_OF_INGREDIENTS),2);
            recipesWeights.put(tmpIngredients.get(i)[0].toString(), weight);
        }
    }

    float countSimilarity(Pair<Integer, Set<String>> data1, Recipe recipe2) {
    Set<String> ingredients2 = joinTable.get(recipe2);

    float jIndex = calculateIndex(data1.getSecond(), ingredients2);
    float timeNeeded = (float)Math.abs(cumulativeTimeDistribution.get(data1.getFirst()) - cumulativeTimeDistribution.get(recipe2.getTotalTimeInSeconds()));
    return WEIGHT_OF_INGREDIENTS * jIndex + WEIGHT_OF_TIME * timeNeeded;
    }

    // Integer is "time needed", Set is "ingredients" set
    Pair<Integer,Set<String>> dataToInsert(int id){
        List<Object[]> data = recipeDAO.findRecipeIngredients(id);
        Set<String> resultSet = new HashSet<>();
        for (int i=0; i < data.size(); i++){
            resultSet.add((String)data.get(i)[1]);
        }
        //int b = (Integer)data.get(0)[2];
        return of((Integer)data.get(0)[2], resultSet);
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

    private void timeNeededIndex() {
        List<Object[]> tmpTimeNeeded = recipeDAO.findTimeNeeded();
        Integer lessTime = 0;
        for (int i = 0; i < tmpTimeNeeded.size(); i++) {
            BigInteger bi = (BigInteger) tmpTimeNeeded.get(i)[0];
            lessTime += bi.intValue();
            cumulativeTimeDistribution.put((Integer)tmpTimeNeeded.get(i)[1], (float) lessTime / 13083);
        }
    }
}
