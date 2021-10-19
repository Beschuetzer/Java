package major.adam;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try (
                //db is created if it doesn't exist on .getConnection() call
                Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/adam/github/WebDevelopmentStuff/Java/SQLite-JDBC/test.db");
                //running a command against the connection requires a Statement obj
                Statement statement = conn.createStatement();
        ) {
//            conn.setAutoCommit(false);
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "contacts (name TEXT, phone INTEGER, email TEXT)");
            statement.execute("INSERT INTO contacts " +
                    "(name, phone, email) " +
                    "VALUES('Joes', 332423, 'JOe@email.com')");
            statement.execute("INSERT INTO contacts " +
                    "(name, phone, email) " +
                    "VALUES('Jane', 3528910, 'jane.j.johnson@email.com')");


            //querying and getting ResultSet
            statement.execute("SELECT * FROM contacts");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " " +
                        resultSet.getInt("phone") + " " +
                        resultSet.getString("email"));
            }
            resultSet.close();

            System.out.println("-".repeat(50));

            //using .executeQuery() for simplified code
            ResultSet results = statement.executeQuery("SELECT * FROM contacts");
            while (results.next()) {
                System.out.println(results.getString("name") + " " +
                        results.getInt("phone") + " " +
                        results.getString("email"));
            }
            results.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
