package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CreditPage {
    public CreditPage() {}

    private SelenideElement cardNumberInput = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInput = $("[placeholder='08']");
    private SelenideElement yearInput = $("[placeholder='22']");
    private SelenideElement cardOwnerInput = $$("[class='input__control']").get(3);
    private SelenideElement codeInput = $("[placeholder='999']");

    private SelenideElement continueButton = $(byText("Продолжить"));

    private SelenideElement failedNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement mandatoryFieldMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));
    private SelenideElement invalidCharactersMessage = $(byText("Поле содержит недопустимые символы"));
    private SelenideElement wrongCardExpirationMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredMessage = $(byText("Истёк срок действия карты"));


    public void fillForm(String cardNumber, String month, String year, String cardOwner, String code) {
        cardNumberInput.sendKeys(cardNumber);
        monthInput.sendKeys(month);
        yearInput.sendKeys(year);
        cardOwnerInput.sendKeys(cardOwner);
        codeInput.sendKeys(code);
        continueButton.click();
    }

    public CreditPage clear() {
        clearFields();
        return new CreditPage();
    }

    public void clearFields() {
        cardNumberInput.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthInput.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearInput.doubleClick().sendKeys(Keys.BACK_SPACE);
        cardOwnerInput.doubleClick().sendKeys(Keys.BACK_SPACE);
        codeInput.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public void waitForFailedNotification() {
        failedNotification.waitUntil(Condition.visible, 11000);
    }

    public void waitForSuccessNotification() {
        successNotification.waitUntil(Condition.visible, 11000);
    }

    public void waitForMandatoryFieldMessage() {
        mandatoryFieldMessage.waitUntil(Condition.visible, 11000);
    }

    public void waitForWrongFormatMessage() {
        wrongFormatMessage.waitUntil(Condition.visible, 11000);
    }

    public void waitForInvalidCharactersMessage() {
        invalidCharactersMessage.waitUntil(Condition.visible, 11000);
    }

    public void waitForWrongCardExpirationMessage() {
        wrongCardExpirationMessage.waitUntil(Condition.visible, 11000);
    }

    public void waitForCardExpiredMessage() {
        cardExpiredMessage.waitUntil(Condition.visible, 11000);
    }

}