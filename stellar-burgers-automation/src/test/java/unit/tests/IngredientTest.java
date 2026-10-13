package unit;

import models.Ingredient;
import models.IngredientType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {
    @Test
    void shouldCreateIngredientWithCorrectValues() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "Томатная паста", 100);

        assertEquals(IngredientType.SAUCE, ingredient.getType());
        assertEquals("Томатная паста", ingredient.getName());
        assertEquals(100, ingredient.getPrice());
    }

    @ParameterizedTest
    @EnumSource(IngredientType.class)
    void shouldReturnCorrectIngredientType(IngredientType type) {
        Ingredient ingredient = new Ingredient(type, "Томатная паста", 100);

        assertEquals(type, ingredient.getType());
    }

    @Test
    void shouldReturnCorrectIngredientName() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "Томатная паста", 100);

        assertEquals("Томатная паста", ingredient.getName());
    }

    @Test
    void shouldReturnCorrectIngredientPrice() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "Томатная паста", 200);

        assertEquals(200, ingredient.getPrice());
    }
}
