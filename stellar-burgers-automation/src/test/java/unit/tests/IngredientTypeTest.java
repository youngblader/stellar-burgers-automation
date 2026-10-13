package unit;

import models.IngredientType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTypeTest {
    @Test
    void sauceShouldExist() {
        assertEquals(IngredientType.SAUCE, IngredientType.valueOf("SAUCE"));
    }

    @Test
    void fillingShouldExist() {
        assertEquals(IngredientType.FILLING, IngredientType.valueOf("FILLING"));
    }
}