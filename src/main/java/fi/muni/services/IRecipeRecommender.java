package fi.muni.services;

import fi.muni.DTOs.RecipeDTO;
import fi.muni.entities.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by peter on 30.11.17.
 */
public interface IRecipeRecommender {

    List<Recipe> recommend(Integer id);

}
