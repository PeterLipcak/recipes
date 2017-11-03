package fi.muni.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @Column(name="RECIPE_ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer recipe_id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ingredients")
    private List<String> ingredients;

    private String recipeName;

    private int totalTimeInSeconds;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="ATTRIBUTE_ID")
    private Attributes attributes;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="FLAVOR_ID")
    private Flavor flavors;

    private short rating;

}
