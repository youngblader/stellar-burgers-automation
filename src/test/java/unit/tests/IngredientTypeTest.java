package unit.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import unit.models.IngredientType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTypeTest {
    @ParameterizedTest
    @EnumSource(IngredientType.class)
    void shouldReturnCorrectIngredientType(IngredientType type) {
        assertEquals(type, IngredientType.valueOf(type.name()));
    }
}