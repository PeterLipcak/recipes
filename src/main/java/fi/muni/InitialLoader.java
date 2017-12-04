package fi.muni;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.muni.DAO.IRecipeDAO;
import fi.muni.entities.Recipe;
import fi.muni.services.IRecipeRecommender;
import fi.muni.services.RecipeMainPageRecommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by peter on 02.11.17.
 */
@Component
public class InitialLoader implements CommandLineRunner {

    @Autowired
    private IRecipeDAO recipeDAO;

    @Override
    public void run(String... strings) throws Exception {

        if(strings[0].split("=")[1].compareTo("yes") == 0){
            RestTemplate restTemplate = new RestTemplate();

            System.out.println("Downloading recipes...");
            String json;

            int i = 0;
            while( i < 80)
            {
                System.out.println("Receiving json " + i);
                try {
                    json = restTemplate.getForObject("http://api.yummly.com/v1/api/recipes?_app_id=668c717d&_app_key=9123fc5b2c2a0742a8aa4ca1ef01bf35&maxResult=500&start=" + i * 500, String.class);
                }catch (Exception e)
                {
                    continue;
                }
                System.out.println("Mapping recipes to object..." + i);
                JsonNode arrNode = new ObjectMapper().readTree(json).get("matches");
                List<Recipe> recipes = new ObjectMapper().enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                        .readValue(arrNode.toString(), new TypeReference<List<Recipe>>(){});
                System.out.println("Jackson mapping was successful!");
                System.out.println("Inserting to database...");
                try {
                    recipeDAO.save(recipes);
                }
                catch (Exception e)
                {
                    System.out.println("Could not persist downloaded data because of unsupported character occurence.");
                }
                i++;
            }
        }
        //System.out.println(recipeDAO.findOne(2));
    }
}
