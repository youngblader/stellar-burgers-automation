package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage {
    private final SelenideElement singInButton = $x(".//button[text()=\"Войти в аккаунт\"]");
    private final SelenideElement personalAccountButton = $x(".//a[@class='AppHeader_header__link__3D_hX' and normalize-space()='Личный Кабинет']");
    private final SelenideElement orderButton = $x(".//button[text()='Оформить заказ']");

    private final SelenideElement constructorHeader = $x("//h1[text()='Соберите бургер']");
    private final SelenideElement bunsTab = $x("//span[text()='Булки']");
    private final SelenideElement saucesTab = $x("//span[text()='Соусы']");
    private final SelenideElement fillingsTab = $x("//span[text()='Начинки']");
    private final SelenideElement activeTabSpan = $x("//div[contains(@class, 'tab_tab_type_current')]//span");

    private final SelenideElement bunsHeader = $x("//h2[text()='Булки']");
    private final SelenideElement saucesHeader = $x("//h2[text()='Соусы']");
    private final SelenideElement fillingsHeader = $x("//h2[text()='Начинки']");

    @Step("Открыть главную страницу")
    public void open() {
        Selenide.open();
    }

    @Step("Ожидание появление кнопки оформления заказа")
    public void waitSuccessOrderButtonDisplayed() {
        orderButton.shouldBe(visible);
    }

    @Step("Нажатие на кнопку войти в аккаунт")
    public void clickLoginButton() {
        singInButton.click();
    }

    @Step("Нажатие на кнопку Личный кабинет")
    public void clickPersonalAccountButton() {
        personalAccountButton.click();
    }

    public void waitForConstructorPage() {
        constructorHeader.shouldBe(visible);
    }

    public void clickBunsTab() {
        bunsTab.shouldBe(clickable).click();
    }

    public void clickSaucesTab() {
        saucesTab.shouldBe(clickable).click();
    }

    public void clickFillingsTab() {
        fillingsTab.shouldBe(clickable).click();
    }

    public void waitForActiveTab(String tabName) {
        activeTabSpan.shouldHave(exactText(tabName));
    }

    public String getActiveTabText() {
        return activeTabSpan.getText();
    }

    public boolean isBunsHeaderDisplayed() {
        return bunsHeader.isDisplayed();
    }

    public boolean isSaucesHeaderDisplayed() {
        return saucesHeader.isDisplayed();
    }

    public boolean isFillingsHeaderDisplayed() {
        return fillingsHeader.isDisplayed();
    }
}
