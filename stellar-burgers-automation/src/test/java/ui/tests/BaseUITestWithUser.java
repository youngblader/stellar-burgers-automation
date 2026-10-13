package ui.tests;

import api.tests.BaseApiTest;
import api.utils.UserGenerator;
import clients.UserClient;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseUITestWithUser extends BaseApiTest {
    protected User user;
    protected UserClient userClient;
    protected String accessToken;

    @BeforeEach
    void setupTest() {
        prepareUser();
    }

    private void prepareUser() {
        userClient = new UserClient();
        user = UserGenerator.generateUser();
        userClient.createUser(user);
        accessToken = userClient.loginUser(user).path("accessToken");
    }

    @AfterEach
    void deleteUser() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}