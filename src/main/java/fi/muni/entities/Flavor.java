package fi.muni.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

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
@Table(name = "flavors")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flavor {

    @Id
    @Column(name="FLAVOR_ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer flavor_id;

    private double piquant;

    private double meaty;

    private double bitter;

    private double sweet;

    private double sour;

    private double salty;
}
