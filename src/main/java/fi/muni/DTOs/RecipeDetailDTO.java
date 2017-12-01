package fi.muni.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by peter on 30.11.17.
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDetailDTO {

    private String prepTime;

    private String cookTime;

    private List<String> ingredientLines;

    private String sourceRecipe;

    private List<RecipeDTO> recommendedBasedOnIngredients;

    private List<RecipeDTO> recommendedBasedOnFlavors;

    @JsonProperty("source")
    private void unpackSourceFromNestedObject(Map<String, String> source){
        sourceRecipe = source.get("sourceRecipeUrl");
    }

}
