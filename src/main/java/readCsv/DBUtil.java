package readCsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    Connection connection;
    Statement statement;
    StringBuilder insertSQL;
    CSVParser csvParser;
    Reader reader;
    ResultSet resultSet;
    String[] headers = {"ID", "NAME", "COLOUR", "GENDER_RECOMMENDATION", "SIZE", "PRICE", "RATING", "AVAILABILITY"};
    StringBuilder createTableSQL = new StringBuilder(Util.readProperties("createTable"));

    public void createtable() {
        dropTable();
        for (int i = 0; i < Util.countTheFiles().length; i++) {
            setupTable(Util.countTheFiles()[i]);
        }
    }

    public void setupTable(String fileName) {
        try {
            reader = Files.newBufferedReader(Paths.get(fileName));
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter('|')
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            dbConfig().executeUpdate(createTableSQL.toString());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        // Insert the data from the CSV file into the MySQL table
        for (CSVRecord csvRecord : csvParser) {
            insertSQL = new StringBuilder("INSERT INTO dress (");
            for (int i = 0; i < headers.length; i++) {
                insertSQL.append(headers[i]).append(", ");
            }
            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
            insertSQL.append(") VALUES (");
            for (int i = 0; i < headers.length; i++) {
                insertSQL.append("\"").append(csvRecord.get(headers[i])).append("\", ");
            }
            insertSQL.delete(insertSQL.length() - 2, insertSQL.length());
            insertSQL.append(")");
            try {
                dbConfig().executeUpdate(insertSQL.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void fetchQueries(String var1, String var2, String var3, String var4, String var5, String var6) {
        createtable();
        Util util = new Util(var1, var2, var3, var4, var5, var6);
        try {
            System.out.println("\n " + util.setQuery() + "\n ");
            resultSet = dbConfig().executeQuery(util.setQuery());
            ResultSetMetaData rsmd = resultSet.getMetaData();
            List <String> list = new ArrayList <>();
            int columnsNumber = rsmd.getColumnCount();
            if (!resultSet.isBeforeFirst() && resultSet.getRow() == 0) {
                System.out.println(" \n  We apologize for the inconvenience, but the collection you're looking for is not currently in stock. Please try again with new preferences  ");
            }
            System.out.println(" \n Here is what we have in store for you !!! \n ");
            while (resultSet.next()) {
                for (int i = 2; i <= columnsNumber; i++) {
                    list.add(resultSet.getString(i));
                }
                System.out.println(list + "\n");
                list.removeAll(list);
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void dropTable() {
        try {
            String query = Util.readProperties("dropTable");
            dbConfig().executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public Statement dbConfig() {
        try {
            connection = DriverManager.getConnection(Util.readProperties("url"), Util.readProperties("dbUsername"), Util.readProperties("dbPassword"));
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return statement;
    }
}