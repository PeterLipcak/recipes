package fi.muni.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by peter on 30.11.17.
 */
@Data
@Builder
public class RecipeDTO {

    @JsonProperty("recipe_id")
    private Integer id;

    @JsonProperty("id")
    private String yummly_id;

    private String imageUrl;

    private String recipeName;

    private short rating;


}
