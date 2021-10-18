package major.adam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            //db is created if it doesn't exist on .getConnection() call
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/adam/github/WebDevelopmentStuff/Java/SQLite-JDBC/test.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
