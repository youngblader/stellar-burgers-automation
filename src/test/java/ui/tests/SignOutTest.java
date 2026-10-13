package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ui.pages.HomePage;
import ui.pages.LoginPage;
import ui.pages.ProfilePage;

public class SignOutTest extends BaseUITestWithUser {
    private ProfilePage profilePage;
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeEach
    void setupUITest() {
        initPages();
    }

    private void initPages() {
        profilePage = new ProfilePage();
        homePage = new HomePage();
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Успешный выход из аккаунта через кнопку Выйти в личном кабинете")
    void signOut() {
        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());

        homePage.clickPersonalAccountButton();
        profilePage.clickSignOutButton();
        loginPage.waitForSignInButtonDisplayed();
    }
}
