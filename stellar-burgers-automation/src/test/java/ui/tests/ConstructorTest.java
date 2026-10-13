package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import utils.BrowserFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorTest {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeEach
    void setUp() {
        driver = BrowserFactory.createDriver();
        homePage = new HomePage(driver);

        homePage.open();
        homePage.waitForConstructorPage();
    }

    @Test
    @DisplayName("Переключение на вкладку Соусы")
    @Description("Проверяет успешное переключение на вкладку Соусы в конструкторе")
    void shouldSwitchToSaucesTab() {
        homePage.clickSaucesTab();
        homePage.waitForActiveTab("Соусы");

        assertEquals("Соусы", homePage.getActiveTabText());
        assertTrue(homePage.isSaucesHeaderDisplayed());
    }

    @Test
    @DisplayName("Переключение на вкладку Начинки")
    @Description("Проверяет успешное переключение на вкладку Начинки в конструкторе")
    void shouldSwitchToFillingsTab() {
        homePage.clickFillingsTab();
        homePage.waitForActiveTab("Начинки");

        assertEquals("Начинки", homePage.getActiveTabText());
        assertTrue(homePage.isFillingsHeaderDisplayed());
    }

    @Test
    @DisplayName("Переключение на вкладку Булки")
    @Description("Проверяет успешное переключение обратно на вкладку Булки")
    void shouldSwitchToBunsTab() {
        homePage.clickSaucesTab();
        homePage.waitForActiveTab("Соусы");

        homePage.clickBunsTab();
        homePage.waitForActiveTab("Булки");

        assertEquals("Булки", homePage.getActiveTabText());
        assertTrue(homePage.isBunsHeaderDisplayed());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
