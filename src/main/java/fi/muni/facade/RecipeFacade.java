package fi.muni.facade;

import fi.muni.DTOs.RecipeDTO;
import fi.muni.entities.Recipe;
import fi.muni.services.RecipeFlavorRecommender;
import fi.muni.services.RecipeIngredientRecommender;
import fi.muni.services.RecipeMainPageRecommender;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 30.11.17.
 */
@Service
@Transactional
public class RecipeFacade {

    @Autowired
    private RecipeFlavorRecommender recipeFlavorRecommender;

    @Autowired
    private RecipeIngredientRecommender recipeIngredientRecommender;

    @Autowired
    private RecipeMainPageRecommender recipeMainPageRecommender;

    public List<RecipeDTO> getRecipesRecommendedBasedOnIngredient(Integer id){
        return transformToDTO(recipeIngredientRecommender.recommend(id));
    }

    public List<RecipeDTO> getRecipesRecommendedBasedOnFlavor(Integer id){
        return transformToDTO(recipeFlavorRecommender.recommend(id));
    }

    public List<RecipeDTO> getRecipesMainPage(){
        return transformToDTO(recipeMainPageRecommender.recommend());
    }

    private List<RecipeDTO> transformToDTO(List<Recipe> recipes){
        List<RecipeDTO> recipesDTOs = new ArrayList<>();

        for(Recipe recipe : recipes){
            RecipeDTO recipeDTO = RecipeDTO
                    .builder()
                    .recipeName(recipe.getRecipeName())
                    .id(recipe.getId())
                    .imageUrl(recipe.getImageUrl())
                    .rating(recipe.getRating())
                    .yummly_id(recipe.getYummly_id())
                    .build();
            recipesDTOs.add(recipeDTO);
        }

        return recipesDTOs;
    }

}
