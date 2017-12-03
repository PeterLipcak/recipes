package fi.muni.DAO;

import fi.muni.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 03.11.17.
 */
@Transactional
public interface IRecipeDAO extends JpaRepository<Recipe, Integer> {

    @Query(value = "select * from recipes limit ?1", nativeQuery = true)
    public List<Recipe> findSpecifiedAmountOfRecipes(int amount);

    @Query(value = "select * from recipes order by rand() limit ?1", nativeQuery = true)
    public List<Recipe> findRandomRecipes(int amount);

    @Query(value = "select ingredients, count(ingredients) as count " +
                    "from ingredients " +
                    "group by ingredients " +
                    "order by count DESC", nativeQuery = true)
    public List<Object[]> findIngredientsCount();

    @Query(value = "select count(total_time_in_seconds) as how_many, total_time_in_seconds " +
            "from recipes " +
            "group by total_time_in_seconds " +
            "order by total_time_in_seconds ", nativeQuery = true)
    public List<Object[]> findTimeNeeded();

    @Query(value = "select recipe_id, ingredients, total_time_in_seconds " +
                    "from ingredients " +
                    "join recipes on recipes.id = ingredients.recipe_id " +
                    "where id=?1", nativeQuery = true)
    public  List<Object[]> findRecipeIngredients(int id);

//    @Query(value = "select ingredients" +
//            "from" + "(" +
//                "select ingredients, count(ingredients) as count " +
//                "from ingredients " +
//                "group by ingredients " +
//                "order by count desc" +
//            ")" + " t",
//            nativeQuery = true)
//    public List<String> findIngredientsCount();

}
