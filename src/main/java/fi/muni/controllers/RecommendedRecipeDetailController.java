package fi.muni.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.muni.DAO.IClickLoggerDAO;
import fi.muni.DTOs.RecipeDetailDTO;
import fi.muni.entities.ClickLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by peter on 30.11.17.
 */
@RequestMapping(value = "/api/recipe/recommended/{recipeId}")
@RestController
public class RecommendedRecipeDetailController {

    @Autowired
    private IClickLoggerDAO clickLoggerDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String readRecipeDetail(@PathVariable String recipeId, @RequestParam String recommenderType, HttpServletRequest request) {

        System.out.println(recommenderType);
        System.out.println(request.getRemoteAddr());

        ClickLogger clickLogger = ClickLogger.builder()
                .ipAddress(request.getRemoteAddr())
                .recommenderType(recommenderType)
                .build();

        clickLoggerDAO.save(clickLogger);

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
            System.out.println(mapper.writeValueAsString(recipeDetailDTO));
            result = mapper.writeValueAsString(recipeDetailDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
