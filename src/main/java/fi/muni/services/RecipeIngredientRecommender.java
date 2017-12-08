package fi.muni.services;

import fi.muni.DAO.IRecipeDAO;
import fi.muni.entities.Recipe;
//import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by peter on 30.11.17.
 */
@Service
public class RecipeIngredientRecommender extends IngredientsRecommender implements IRecipeRecommender {

    private static final int AMOUNT_TO_DISPLAY = 8;

    @Autowired
    public RecipeIngredientRecommender(IRecipeDAO recipeDAO) {
        super(recipeDAO);
    }

    public List<Recipe> recommend(Integer id) {
        long startTime = System.currentTimeMillis();
        Map<Integer, Pair<Integer, Set<String>>> initialRecipeData = new HashMap<>();
        initialRecipeData.put(id, dataToInsert(id));
        List<Recipe> result = new ArrayList<>();

        Map<Recipe, Float> recipesSimilarity = new HashMap<>();

        for(Recipe recipe: joinTable.keySet()){
            if (recipe.getId() == id) continue;
            else recipesSimilarity.put(recipe, countSimilarity(initialRecipeData.get(id), recipe));
        }

        Set<Map.Entry<Recipe, Float>> set = recipesSimilarity.entrySet();
        List<Map.Entry<Recipe, Float>> list = new ArrayList<>(set);
        Collections.sort( list, new Comparator<Map.Entry<Recipe, Float>>()
        {
            public int compare( Map.Entry<Recipe, Float> o1, Map.Entry<Recipe, Float> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );

        for (int n = 2; n < AMOUNT_TO_DISPLAY*2 + 2; n++) {
            result.add(list.get(n).getKey());
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("RecipeIngredientRecommender -> " + elapsedTime);
        return  result;
    }
}
