package fi.muni.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fi.muni.DTOs.RecipeDetailDTO;
import fi.muni.entities.Recipe;
import fi.muni.facade.RecipeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by peter on 26.11.17.
 */
@RequestMapping(value = "/api/yummly/recipe/{recipeId}")
@RestController
public class RecipeDetailController {

    @Autowired
    private RecipeFacade recipeFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String readRecipeDetail(@PathVariable String recipeId, @RequestParam Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        ObjectMapper mapper = new ObjectMapper();
        RecipeDetailDTO recipeDetailDTO = null;
        String result = null;
        String recipeDetail = restTemplate.getForObject("http://api.yummly.com/v1/api/recipe/" + recipeId + "?_app_id=668c717d&_app_key=9123fc5b2c2a0742a8aa4ca1ef01bf35", String.class);
        try {

            recipeDetailDTO = mapper.readValue(recipeDetail, RecipeDetailDTO.class);
            recipeDetailDTO.setRecommendedBasedOnIngredients(recipeFacade.getRecipesRecommendedBasedOnIngredient(id));
            recipeDetailDTO.setRecommendedBasedOnFlavors(recipeFacade.getRecipesRecommendedBasedOnFlavor(id));
            System.out.println(mapper.writeValueAsString(recipeDetailDTO));
            result = mapper.writeValueAsString(recipeDetailDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
