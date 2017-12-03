package fi.muni.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import fi.muni.enums.Course;
import fi.muni.enums.Cuisine;
import fi.muni.enums.Holiday;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by peter on 02.11.17.
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
@Table(name = "recipes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    @Id
    @JsonProperty("recipe_id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @JsonProperty("id")
    private String yummly_id;

    //@JsonIgnore
    private String imageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ingredients")
    private List<String> ingredients;

    private String recipeName;

    private int totalTimeInSeconds;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="FLAVOR_ID")
    private Flavor flavors;

    private short rating;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "courses")
    @Enumerated(EnumType.STRING)
    private Set<Course> course;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cuisines")
    @Enumerated(EnumType.STRING)
    private Set<Cuisine> cuisine;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "holidays")
    @Enumerated(EnumType.STRING)
    private Set<Holiday> holiday;

    @JsonProperty("attributes")
    private void unpackAttributesFromNestedObject(Attributes attributes){
        course = attributes.getCourse();
        cuisine = attributes.getCuisine();
        holiday = attributes.getHoliday();
        if(course != null) course.remove(null);
        if(cuisine != null) cuisine.remove(null);
        if(holiday != null) holiday.remove(null);
    }

    @JsonProperty("imageUrlsBySize")
    private void unpackImageUrlFromNestedObject(Map<String, String> imageUrlsBySize){
        imageUrl = imageUrlsBySize.get("90");
    }

    public Integer getId(){
        return id;
    }

    public int getTotalTimeInSeconds(){
        return totalTimeInSeconds;
    }

}
