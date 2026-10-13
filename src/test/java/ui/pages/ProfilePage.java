package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {
    private final SelenideElement signOutButton = $x(".//button[text()='Выход']");

    @Step("Нажатие на кнопку выхода")
    public void clickSignOutButton() {
        signOutButton.shouldBe(visible).click();
    }

    @Step("Ожидание отображение кнопки выхода")
    public void waitSignOutButtonDisplayed() {
        signOutButton.shouldBe(visible);
    }
}
