package fi.muni.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.muni.DTOs.RecipeDTO;
import fi.muni.facade.RecipeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by peter on 30.11.17.
 */
@RequestMapping(value = "/api/main/recipes/")
@RestController
public class RecipeMainPageController {

    @Autowired
    private RecipeFacade recipeFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String readRecipeDetail() {

        List<RecipeDTO> recipeDTOs = recipeFacade.getRecipesMainPage();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode mainRecipes = mapper.createObjectNode();



        String result = null;
        try {
            result = mapper.writeValueAsString(recipeDTOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
