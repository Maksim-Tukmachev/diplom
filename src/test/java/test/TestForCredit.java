package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataGenerator;
import data.SQL;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestForCredit {

    private MainPage mainPage;
    private CreditPage creditPage;

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
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForSuccessNotification();
        val expected = DataGenerator.getFirstCardStatus();
        val actual = SQL.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyPurchaseWithEmptyFields() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getEmptyCardNumber();
        val month = DataGenerator.getEmptyMonth();
        val year = DataGenerator.getEmptyYear();
        val cardOwner = DataGenerator.getEmptyCardholderName();
        val code = DataGenerator.getEmptyCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCardNumberField() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getEmptyCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithDeclinedCard() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getSecondCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForFailedNotification();
        val expected = DataGenerator.getSecondCardStatus();
        val actual = SQL.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyPurchaseWithAnotherCard() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.randomCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForFailedNotification();
    }

    @Test
    void shouldDenyPurchaseCardNumberWith15Digits() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getCardNumberWith15Digits();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseCardNumberWithTextAndChars() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getCardNumberWithTextAndChars();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyMonthField() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getEmptyMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithMonthOver12() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getMonthOver12();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithZeroMonth() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getMonthWithZero();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInMonthField() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getMonthWithText();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyYearField() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getEmptyYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithPastYear() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.pastYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForCardExpiredMessage();
    }

    @Test
    void shouldDenyPurchaseWithTooFutureYear() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidFutureYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInYearField() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getYearWithText();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCardOwnerField() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getEmptyCardholderName();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithoutSecondName() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithFirstNameOnly();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithLowercaseCardOwner() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithLowercaseLetters();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithUppercaseCardOwner() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithUppercaseLetters();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithRedundantDataCardOwner() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithRedundantData();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithCyrillicDataCardOwner() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithCyrillicCharacters();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithTwoLanguagesCardOwner() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithTwoAlphabets();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithDigitsCardOwner() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithDigits();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithSpecialCharsCardOwner() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getCardholderNameWithSpecialCharacters();
        val code = DataGenerator.getCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCodeField() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getEmptyCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInCodeField() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getCvcWithText();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWith2DigitsInCode() {
        creditPage = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = DataGenerator.getFirstCardNumber();
        val month = DataGenerator.getValidMonth();
        val year = DataGenerator.getValidYear();
        val cardOwner = DataGenerator.getValidName();
        val code = DataGenerator.getShortCvc();
        creditPage.fillForm(cardNumber, month, year, cardOwner, code);
        creditPage.waitForInvalidCharactersMessage();
    }

}
