package api.tests;

import api.config.TestConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {
    private static TestConfig config;

    @BeforeAll
    static void setup() {
        config = ConfigFactory.create(TestConfig.class);

        RestAssured.baseURI = config.baseUrl();
        RestAssured.useRelaxedHTTPSValidation();
    }
}
