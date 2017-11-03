package fi.muni.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by peter on 03.11.17.
 */
public enum Course {
    @JsonProperty("Main Dishes")
    MAIN_DISHES,
    @JsonProperty("Desserts")
    DESSERTS,
    @JsonProperty("Side Dishes")
    SIDE_DISHES,
    @JsonProperty("Lunch and Snacks")
    LUNCH_AND_SNACKS,
    @JsonProperty("Lunch")
    LUNCH,
    @JsonProperty("Appetizers")
    APPETIZERS,
    @JsonProperty("Salads")
    SALADS,
    @JsonProperty("Breads")
    BREADS,
    @JsonProperty("Breakfast and Brunch")
    BREAKFAST_AND_BRUNCH,
    @JsonProperty("Soups")
    SOUPS,
    @JsonProperty("Beverages")
    BEVERAGES,
    @JsonProperty("Condiments and Sauces")
    CONDIMENTS_AND_SAUCES,
    @JsonProperty("Cocktails")
    COCKTAILS,
    @JsonProperty("Afternoon Tea")
    AFTERNOON_TEA,
    @JsonProperty("Snacks")
    SNACKS;
}