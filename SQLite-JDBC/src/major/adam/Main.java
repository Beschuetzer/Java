package major.adam;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final String DB_NAME = "test.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:/home/adam/github/WebDevelopmentStuff/Java/SQLite-JDBC";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";

    public static final List<String> contacts = Arrays.asList(
            "Tom", "512312", "Tom@email.com",
            "Joes", "332423", "JOe@email.com",
            "Jill", "910190", "jill.j.johson@email.com",
            "Adam", "5410019", "adam@gmail.com",
            "Jackson", "0912121", "jacsksonjjimy@email.com",
            "Jack", "1231231", "jack.j@email.com"
    );


    public static void main(String[] args) {
        try (
                //db is created if it doesn't exist on .getConnection() call
                Connection conn = DriverManager.getConnection(Main.CONNECTION_STRING + "/" + Main.DB_NAME);
                //running a command against the connection requires a Statement obj
                Statement statement = conn.createStatement()
        ) {
//            conn.setAutoCommit(false);
            statement.execute("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS +
                    "(" + COLUMN_NAME + " TEXT, " + COLUMN_PHONE + " INTEGER," + COLUMN_EMAIL + " TEXT)");

            for (int i = 0; i < contacts.size(); i+=3) {
                String name = contacts.get(i);
                Integer phone = Integer.parseInt(contacts.get(i + 1));
                String email = contacts.get(i + 2);
                insertIntoContacts(name, phone, email, statement);
            }

//            //querying and getting ResultSet
//            statement.execute("SELECT * FROM contacts");
//            ResultSet resultSet = statement.getResultSet();
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(COLUMN_NAME) + " " +
//                        resultSet.getInt(COLUMN_PHONE) + " " +
//                        resultSet.getString(COLUMN_EMAIL));
//            }
//            resultSet.close();
//
//            System.out.println("-".repeat(50));

            //using .executeQuery() for simplified code
            ResultSet results = statement.executeQuery("SELECT * FROM contacts");
            while (results.next()) {
                System.out.println(results.getString(COLUMN_NAME) + " " +
                        results.getInt(COLUMN_PHONE) + " " +
                        results.getString(COLUMN_EMAIL));
            }
            results.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoContacts(String name, Integer phone, String email, Statement statement) throws SQLException {
        String SQLString = "INSERT INTO " + TABLE_CONTACTS +
                "(" + COLUMN_NAME + ", " + COLUMN_PHONE + ", " + COLUMN_EMAIL + ") " +
                "VALUES(" +
                "'" + name + "', " +
                phone + ", " +
                "'" + email + "') ";
        String betterSQLString = String.format("INSERT INTO %s (%s, %s, %s) VALUES('%s', %s, '%s')",
            TABLE_CONTACTS,
            COLUMN_NAME,
            COLUMN_PHONE,
            COLUMN_EMAIL,
            name,
            phone,
            email
        );
        statement.execute(betterSQLString);
    }
}
