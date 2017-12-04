package fi.muni.DAO;

import fi.muni.entities.ClickLogger;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by peter on 04.12.17.
 */
@Transactional
public interface IClickLoggerDAO extends JpaRepository<ClickLogger, Integer> {
}
