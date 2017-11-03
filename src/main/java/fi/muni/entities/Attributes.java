package fi.muni.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.muni.enums.Course;
import fi.muni.enums.Cuisine;
import fi.muni.enums.Holiday;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by peter on 03.11.17.
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
@Table(name = "attributes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {

    @Id
    @Column(name="ATTRIBUTE_ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer attribute_id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "courses")
    @Enumerated(EnumType.STRING)
    private Set<Course> course;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cuisines")
    @Enumerated(EnumType.STRING)
    private Set<Cuisine> cuisine;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "holidays")
    @Enumerated(EnumType.STRING)
    private Set<Holiday> holiday;
}
