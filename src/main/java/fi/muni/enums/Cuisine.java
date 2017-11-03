package fi.muni.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by peter on 03.11.17.
 */
public enum Cuisine {
    @JsonProperty("American")
    AMERICAN,
    @JsonProperty("Italian")
    ITALIAN,
    @JsonProperty("Asian")
    ASIAN,
    @JsonProperty("Mexican")
    MEXICAN,
    @JsonProperty("Southern & Soul Food")
    SOUTHERN_AND_SOULD_FOOD,
    @JsonProperty("Southern")
    SOUTHERN,
    @JsonProperty("Soul Food")
    SOULD_FOOD,
    @JsonProperty("French")
    FRENCH,
    @JsonProperty("Southwestern")
    SOUTHWESTERN,
    @JsonProperty("Barbecue")
    BARBECUE,
    @JsonProperty("Indian")
    INDIAN,
    @JsonProperty("Chinese")
    CHINESE,
    @JsonProperty("Cajun & Creole")
    CAJUN_AND_CREOLE,
    @JsonProperty("Cajun")
    CAJUN,
    @JsonProperty("Creole")
    CREOLE,
    @JsonProperty("English")
    ENGLISH,
    @JsonProperty("Mediterranean")
    MEDITERRANEAN,
    @JsonProperty("Greek")
    GREEK,
    @JsonProperty("Spanish")
    SPANISH,
    @JsonProperty("German")
    GERMAN,
    @JsonProperty("Thai")
    THAI,
    @JsonProperty("Moroccan")
    MOROCCAN,
    @JsonProperty("Irish")
    IRISH,
    @JsonProperty("Japanese")
    JAPANESE,
    @JsonProperty("Cuban")
    CUBAN,
    @JsonProperty("Hawaiian")
    HAWAIIAN,
    @JsonProperty("Swedish")
    SWEDISH,
    @JsonProperty("Hungarian")
    HUNGARIAN,
    @JsonProperty("Portugese")
    PORTUGESE,
    @JsonProperty("Korean")
    KOREAN,
    @JsonProperty("Kid-Friendly")
    KID_FRIENDLY,
    @JsonProperty("Jamaican")
    JAMAICAN,
    @JsonProperty("Filipino")
    FILIPINO;
}