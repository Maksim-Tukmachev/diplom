package page;

import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private SelenideElement heading = $("[class='heading heading_size_l heading_theme_alfa-on-white']");
    private SelenideElement paymentFormHeading = $("[class='heading heading_size_m heading_theme_alfa-on-white']");
    private SelenideElement buyByDebitCardButton = $(byText("Купить"));
    private SelenideElement buyByCreditCardButton = $(byText("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible);
    }

    public PayPage payWithDebitCard() {
        buyByDebitCardButton.click();
        paymentFormHeading.shouldHave(exactText("Оплата по карте"));
        return new PayPage();
    }

    public CreditPage payWithCreditCard() {
        buyByCreditCardButton.click();
        paymentFormHeading.shouldHave(exactText("Кредит по данным карты"));
        return new CreditPage();
    }
}

