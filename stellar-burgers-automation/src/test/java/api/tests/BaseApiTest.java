package api.utils;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseApiTest {
    public static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
}
