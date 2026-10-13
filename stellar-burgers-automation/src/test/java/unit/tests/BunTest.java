package unit.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import unit.models.Bun;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BunTest {
    @ParameterizedTest
    @CsvSource({
            "black bun, 100",
            "white bun, 200",
            "brown bun, 300"
    })
    void shouldReturnCorrectBunValues(String name, float price ) {
        Bun bun = new Bun(name, price);

        assertEquals(name, bun.getName());
        assertEquals(price, bun.getPrice());
    }

    @Test
    void shouldReturnCorrectBunName() {
        Bun bun = new Bun("black bun", 100);
        assertEquals("black bun", bun.getName());
    }

    @Test
    void shouldReturnCorrectBunPrice() {
        Bun bun = new Bun("black bun", 200);
        assertEquals(200, bun.getPrice());
    }
}
