package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.Stack;

public class Util {

    static String filetoRead;

    static Scanner input;
    String url = "jdbc:mysql://localhost:3306/apparel";
    String username = "root";
    String password = "password";
    Connection connection;
    Statement statement;
    FileReader fileReader;
    Iterable<CSVRecord> records;
    PreparedStatement preparedStatement;
    StringBuilder insertSQL;


    public static String[] countTheFiles() {
        File folder = new File("src/main/resources/datafile");
        File[] listofFiles = folder.listFiles();
        String nameofFiles[] = new String[listofFiles.length];
        for (int i = 0; i < listofFiles.length; i++) {
            nameofFiles[i] = listofFiles[i].toString();
        }
        return nameofFiles;


    }

    public void dbconnection() {

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void tableSetup(String fileName) {
        dbconnection();
        try {
            /*fileReader = new FileReader("src/main/resources/datafile/Nike.csv");*/
            fileReader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            records = CSVFormat.DEFAULT.withDelimiter('|').withHeader().parse(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Create the table based on the CSV headers
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS dress (srNo INT AUTO_INCREMENT PRIMARY KEY, ");
        String[] headers = records.iterator().next().toMap().keySet().toArray(new String[0]);
        for (int i = 0; i < headers.length; i++) {
            createTableSQL.append(headers[i]).append(" VARCHAR(255), ");
        }

        createTableSQL.delete(createTableSQL.length() - 2, createTableSQL.length());
        createTableSQL.append(")");
        try {
            statement.executeUpdate(createTableSQL.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Insert the data from the CSV file into the MySQL table
        for (CSVRecord record : records) {
            insertSQL = new StringBuilder("INSERT INTO dress (");
            for (int i = 0; i < headers.length; i++) {
                insertSQL.append(headers[i]).append(", ");
            }
            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
            insertSQL.append(") VALUES (");
            for (int i = 0; i < headers.length; i++) {
                String temp = record.get(headers[i]);
                if (temp.contains("'")) {
                    insertSQL.append("'").append(record.get(headers[i]).replace("'", "")).append("', ");

                } else {
                    insertSQL.append("'").append(record.get(headers[i])).append("', ");
                }
            }
            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
            insertSQL.append(")");
            System.out.println(insertSQL.toString().replace("'", ""));
            try {
                statement.executeUpdate(insertSQL.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void createtable() {
        // Loop till all the files
        int len = Util.countTheFiles().length;
        for (int i = 0; i < len; i++) {
            System.out.println(countTheFiles()[i]);
            tableSetup(countTheFiles()[i]);
        }
    }

    public static void fetchDatafromTable(String Color, String Size, String Gender, String OutputPreference) {
        input = new Scanner(System.in);
        System.out.print(" Hello Customer , Please enter the desire colour ");
        String colr = input.nextLine();
        System.out.print(" Size");
        String size = input.nextLine();
        System.out.print(" Gender");
        String gen = input.nextLine();
        System.out.print(" OutputPreference");
        String pref = input.nextLine();
        System.out.println( colr);
        System.out.println( size);
        System.out.println( gen);
        System.out.println( pref);





    }

}
