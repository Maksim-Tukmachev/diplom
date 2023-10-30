package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataGenerator;
import data.SQL;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import page.MainPage;
import page.PayPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestForPay {
    private MainPage mainPage;
    private PayPage payPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        mainPage = open("http://localhost:8080/", MainPage.class);
    }

    @AfterEach
    void cleanDB() {
        SQL.clearDB();
    }

    @Test
    void shouldAllowPurchaseWithApprovedCard() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForSuccessNotification();
        val expected = DataGenerator.getFirstCardStatus();
        val actual = SQL.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyPurchaseWithEmptyFields() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getEmptyCardNumber();
        val month = DataGenerator.getEmptyMonth();
        val year = DataGenerator.getEmptyYear();
        val cardOwner = DataGenerator.getEmptyCardholderName();
        val code = DataGenerator.getEmptyCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCardNumberField() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getEmptyCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithDeclinedCard() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getSecondCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForFailedNotification();
        val expected = DataGenerator.getSecondCardStatus();
        val actual = SQL.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyPurchaseWithAnotherCard() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.randomCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForFailedNotification();
    }

    @Test
    void shouldDenyPurchaseCardNumberWith15Digits() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getCardNumberWith15Digits();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseCardNumberWithTextAndChars() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getCardNumberWithTextAndChars();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyMonthField() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getEmptyMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithMonthOver12() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getMonthOver12();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithZeroMonth() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getMonthWithZero();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInMonthField() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getMonthWithText();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyYearField() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getEmptyYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithPastYear() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.pastYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForCardExpiredMessage();
    }

    @Test
    void shouldDenyPurchaseWithTooFutureYear() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidFutureYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInYearField() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getYearWithText();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCardOwnerField() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getEmptyCardholderName();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithoutSecondName() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithFirstNameOnly();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithLowercaseCardOwner() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithLowercaseLetters();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithUppercaseCardOwner() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithUppercaseLetters();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithRedundantDataCardOwner() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithRedundantData();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithCyrillicDataCardOwner() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithCyrillicCharacters();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithTwoLanguagesCardOwner() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithTwoAlphabets();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithDigitsCardOwner() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithDigits();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithSpecialCharsCardOwner() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithSpecialCharacters();
        val code = DataGenerator.getCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCodeField() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getEmptyCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInCodeField() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvcWithText();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWith2DigitsInCode() {
        payPage = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getShortCvc();
        payPage.fillForm(cardNumber, month, year, cardOwner, code);
        payPage.waitForWrongFormatMessage();
    }
}
