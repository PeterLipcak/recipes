package fi.muni.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.muni.enums.Course;
import fi.muni.enums.Cuisine;
import fi.muni.enums.Holiday;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by peter on 04.11.17.
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {

    private Set<Course> course;

    private Set<Cuisine> cuisine;

    private Set<Holiday> holiday;

}
