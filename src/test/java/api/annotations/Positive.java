package api.annotations;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Tag("Positive")
@Story("Positive scenario")
public @interface Positive {
}