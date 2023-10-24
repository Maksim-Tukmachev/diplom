package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    public static String getValidName() {
        return fakerEn.name().firstName() + " " + fakerEn.name().lastName();
    }

    public static String getEmptyCardholderName() {
        return "";
    }

    public static String getCardholderNameWithFirstNameOnly() {
        return fakerEn.name().firstName();
    }

    public static String getCardholderNameWithLowercaseLetters() {
        return fakerEn.name().firstName().toLowerCase(Locale.ROOT) + " " + fakerEn.name().lastName().toLowerCase(Locale.ROOT);
    }

    public static String getCardholderNameWithUppercaseLetters() {
        return fakerEn.name().firstName().toUpperCase(Locale.ROOT) + " " + fakerEn.name().lastName().toUpperCase(Locale.ROOT);
    }

    public static String getCardholderNameWithRedundantData() {
        return "Ivan Petrov Ivanovich";
    }

    public static String getCardholderNameWithCyrillicCharacters() {
        return fakerRu.name().fullName();
    }

    public static String getCardholderNameWithTwoAlphabets() {
        return fakerRu.name().firstName() + " " + fakerEn.name().lastName();
    }

    public static String getCardholderNameWithDigits() {
        return fakerEn.number().digits(5);
    }

    public static String getCardholderNameWithSpecialCharacters() {
        return "!№%?*";
    }

    public static String getValidYear() {
        LocalDate currentDate = LocalDate.now();
        return String.valueOf(currentDate.format(DateTimeFormatter.ofPattern("yy")));
    }

    public static String getValidFutureYear() {
        String futureYear = LocalDate.now().plusYears(10).format(DateTimeFormatter.ofPattern("yy"));
        return futureYear;
    }

    public static String pastYear() {
        String pastYear = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return pastYear;
    }

    public static String getEmptyYear() {
        return "";
    }

    public static String getYearWithText() {
        return "двадцать третий";
    }

    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }

    public static String getEmptyMonth() {
        return "";
    }

    public static String getMonthOver12() {
        return "13";
    }

    public static String getMonthWithZero() {
        return "00";
    }
    public static String getMonthWithText() {
        return "октябрь";
    }

    public static String getCvc() {
        return fakerEn.number().digits(3);
    }

    public static String getShortCvc() {
        return fakerEn.number().digits(2);
    }

    public static String getCvcWithText() {
        return "код";
    }

    public static String getEmptyCvc() {
        return "";
    }

    public static String getCardNumberWith15Digits() {
        return "4444 4444 4444 444";
    }

    public static String randomCardNumber() {
        return fakerEn.business().creditCardNumber();
    }

    public static String getCardNumberWithTextAndChars() {
        return "номер карты!";
    }
    public static String getEmptyCardNumber() {
        return "";
    }

    public static String getFirstCardNumber() {
        return "4444444444444441";
    }

    public static String getSecondCardNumber() {
        return "4444444444444442";
    }

    public static String getFirstCardStatus() {
        return "APPROVED";
    }

    public static String getSecondCardStatus() {
        return "DECLINED";
    }

}
