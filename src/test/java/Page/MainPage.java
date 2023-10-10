package Page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    @Getter
    protected String host = "http://localhost:8080";
    @Getter protected SelenideElement paymentButton = $(byText("Купить")).parent().parent();
    @Getter protected SelenideElement creditButton = $(byText("Купить в кредит")).parent().parent();
    @Getter protected SelenideElement continueButton = $(byText("Продолжить")).parent().parent();
    @Getter protected SelenideElement cardNumberField = $(byText("Номер карты")).parent();
    @Getter protected SelenideElement monthField = $(byText("Месяц")).parent();
    @Getter protected SelenideElement yearField = $(byText("Год")).parent();
    @Getter protected SelenideElement ownerField = $(byText("Владелец")).parent();
    @Getter protected SelenideElement cvcField = $(byText("CVC/CVV")).parent();
    @Getter protected SelenideElement notificationSuccess = $(".notification_status_ok ");
    @Getter protected SelenideElement notificationError = $(".notification_status_error");
}
