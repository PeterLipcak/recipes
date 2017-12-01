package fi.muni.services;

import com.google.common.collect.Lists;
import fi.muni.DAO.IRecipeDAO;
import fi.muni.DTOs.RecipeDTO;
import fi.muni.entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by peter on 30.11.17.
 */
@Service
public class RecipeMainPageRecommender {

    @Autowired
    IRecipeDAO recipeDAO;

    public List<Recipe> recommend() {
        return Lists.newArrayList(recipeDAO.findSpecifiedAmountOfRecipes(50));
    }
}
