package fi.muni.DAO;

import fi.muni.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by peter on 03.11.17.
 */
@Transactional
public interface IRecipeDAO extends JpaRepository<Recipe, Integer> {

    @Query(value = "select * from recipes limit ?1", nativeQuery = true)
    public List<Recipe> findSpecifiedAmountOfRecipes(int amount);

}
