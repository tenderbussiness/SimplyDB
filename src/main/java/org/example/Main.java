package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DbManager dbManager = new DbManager();
        dbManager.getConnection();
       dbManager.listTables();
        System.out.println("Choose table : ");
        int table = scanner.nextInt();
        switch (table) {
            case 1:
                System.out.print(
                        "1. Add contact \n" +
                        "2. Show all contacts \n" +
                        "3. Delete contact \n\n" +
                                "Choose option :  ");

                int operation = scanner.nextInt();
                switch (operation) {
                    case 1:
                        dbManager.addContact();
                        break;

                        case 2:
                            dbManager.showContacts();

                }

        }


    }
}
