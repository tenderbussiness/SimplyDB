package org.example;

import java.sql.*;
import java.util.Scanner;

public class DbManager {
    private String url = "jdbc:postgresql://ep-calm-mountain-a5hw6645.us-east-2.aws.neon.tech:5432/testDb";
    private  String user = "testDb_owner";
    private String password = "wf9mQC8xMqVy";


    public DbManager() {
    }
    public Connection  getConnection() {


            Connection connection = null; // Ініціалізуємо змінну
            try {
                connection = DriverManager.getConnection(url, user, password); // Створюємо підключення
                System.out.println("Connected to the database!");
            } catch (SQLException e) {
                System.out.println("Connection failure.");
                // e.printStackTrace();
            }
            return connection;
    }

    public void createTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the table: ");
        String tblname = scanner.nextLine();
        String query = "CREATE TABLE IF NOT EXISTS " + tblname + " (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "phone VARCHAR(255) NOT NULL" +
                ");";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.execute();
            System.out.println("Table already created created or exist!");
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void addContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name : ");
        String name = scanner.nextLine();
        System.out.print("Phone : ");
        String phone = scanner.nextLine();

        String query = "INSERT INTO contacts (name, phone) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, phone);



            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Контакт успішно додано!");
            }

        } catch (SQLException e) {
            System.out.println("Помилка під час додавання контакту.");
            //e.printStackTrace();
        }




    }

    public void listTables() {
        String query = """
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = 'public';
    """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("All tables:");
            while (resultSet.next()) {
                String tableName = resultSet.getString("table_name");
                System.out.println("- " + tableName);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void showContacts() {
        String query = "SELECT * FROM contacts";
       try(Connection connection = getConnection();
       PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery())
       {
           while(resultSet.next())
           {
               int id = resultSet.getInt("id");
               String name = resultSet.getString("name");
               String phone = resultSet.getString("phone");
               System.out.printf("ID: %d, Name: %s, Phone: %s%n", id, name, phone);
           }

       }catch (SQLException e) {
           System.out.println("Error: " + e.getMessage());
       }
    }


}
