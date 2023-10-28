package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;


public class SQL {

    private static final String DB_URL = System.getProperty("db.url");

    private SQL() {
    }

    @SneakyThrows
    public static Connection conn() {
        return DriverManager.getConnection(DB_URL, "app", "pass");
    }

    @SneakyThrows
    public static void clearDB() {
        QueryRunner runner = new QueryRunner();
            runner.execute(conn(), "DELETE FROM credit_request_entity");
            runner.execute(conn(), "DELETE FROM order_entity");
            runner.execute(conn(), "DELETE FROM payment_entity");
    }

    @SneakyThrows
    public static String getDebitPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
            String result = runner.query(conn(), SqlStatus, new ScalarHandler<>());
            return result;
    }

    @SneakyThrows
    public static String getCreditPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
            String result = runner.query(conn(), SqlStatus, new ScalarHandler<>());
            return result;
    }
}