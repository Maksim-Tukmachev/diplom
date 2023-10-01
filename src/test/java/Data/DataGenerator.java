package Data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {

    public static String name(){
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    public static String currentYear(){
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear() - 2000;
        return Integer.toString(year);
    }

    public static String validFutureYear() {
        Random random = new Random();
        int i = random.nextInt(5) + 1;

        LocalDate futureDate = LocalDate.now().plusYears(i);
        int year = futureDate.getYear() - 2000;

        return Integer.toString(year);
    }

    public static String pastYear() {
        Random random = new Random();
        int i = random.nextInt(18) + 1;
        return String.format("%02d", i);
    }

    public static String currentMonth() {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        return String.format("%02d", month);
    }


}
