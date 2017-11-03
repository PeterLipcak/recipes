package fi.muni.DAO;

import fi.muni.entities.Recipe;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by peter on 03.11.17.
 */
@Transactional
public interface IRecipeDAO extends CrudRepository<Recipe, Long> {
}
