package fi.muni.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by peter on 03.11.17.
 */
public enum Holiday {
    @JsonProperty("Christmas")
    CHRISTMAS,
    @JsonProperty("Summer")
    SUMMER,
    @JsonProperty("Thanksgiving")
    THANKSGIVING,
    @JsonProperty("New Year")
    NEW_YEAR,
    @JsonProperty("Super Bowl / Game Day")
    SUPER_BOWL_OR_GAME_DAY,
    @JsonProperty("Halloween")
    HALLOWEEN,
    @JsonProperty("Hanukkah")
    HANUKKAH,
    @JsonProperty("4th of July")
    FOURTH_OF_JULY,
    @JsonProperty("Fall")
    FALL,
    @JsonProperty("Game Day")
    GAME_DAY,
    @JsonProperty("Super Bowl")
    SUPER_BOWL,
    @JsonProperty("Easter")
    EASTER,
    @JsonProperty("Spring")
    SPRING,
    @JsonProperty("Winter")
    WINTER,
    @JsonProperty("St. Patrick's Day")
    ST_PATRICKS_DAY,
    @JsonProperty("Sunday Lunch")
    SUNDAY_LUNCH;
}