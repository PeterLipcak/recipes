package fi.muni.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.persistence.*;

/**
 * Created by peter on 04.12.17.
 */
@Entity
@Builder
@Table(name = "clicks")
public class ClickLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String ipAddress;

    private String recommenderType;

}
