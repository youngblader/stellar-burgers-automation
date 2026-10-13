package pages;

import constants.Routes;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {
    private final WebDriver driver;

    private final By signInButton = By.xpath(".//a[@class='Auth_link__1fOlj' and normalize-space()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу восстановления пароля")
    public void open() {
        String URL = Routes.FORGOT_PASSWORD;
        driver.get(URL);
    }

    @Step("Нажатие на кнопку входа на странице восстановления пароля")
    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }
}
