package unit;

import models.Bun;
import models.Burger;
import models.Ingredient;
import models.IngredientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BurgerTest {
    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient beef;

    private Burger burger;

    @BeforeEach
    void setUpTest() {
        burger = new Burger();
        burger.setBuns(bun);
    }

    @Test
    void shouldReturnBunPriceWithIngredients() {
        when(bun.getPrice()).thenReturn(100f);
        when(sauce.getPrice()).thenReturn(10f);
        when(beef.getPrice()).thenReturn(50f);

        burger.addIngredient(sauce);
        burger.addIngredient(beef);

        assertEquals(260f, burger.getPrice(), 0.001f);
    }

    @Test
    void shouldAddIngredientToList() {
        burger.addIngredient(sauce);
        assertEquals(1, burger.ingredients.size());
        assertSame(sauce, burger.ingredients.get(0));
    }

    @Test
    void shouldMoveIngredient() {
        burger.addIngredient(sauce);
        burger.addIngredient(beef);

        burger.moveIngredient(0, 1);

        assertSame(beef, burger.ingredients.get(0));
        assertSame(sauce, burger.ingredients.get(1));
    }

    @Test
    void shouldRemoveIngredientByIndex() {
        burger.addIngredient(sauce);
        burger.addIngredient(beef);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
        assertSame(beef, burger.ingredients.get(0));
    }

    @Test
    void shouldCreateCorrectReceiptWithIngredients() {
        when(bun.getName()).thenReturn("white bun");
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("ketchup");
        when(bun.getPrice()).thenReturn(100f);

        burger.addIngredient(sauce);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== white bun ====)"));
        assertTrue(receipt.contains("= sauce ketchup ="));
        assertTrue(receipt.contains("Price: 200,000000"));
    }
}
