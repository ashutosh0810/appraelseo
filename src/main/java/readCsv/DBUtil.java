package readCsv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
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
            log.info(e.getLocalizedMessage());
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
                log.info(e.getLocalizedMessage());
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            log.info(e.getLocalizedMessage());
        }
    }

    public void fetchQueries(String var1, String var2, String var3, String var4, String var5, String var6) {
        createtable();
        Util util = new Util(var1, var2, var3, var4, var5, var6);
        try {

            //log.info("\n " + " Querry is " + util.setQuery() + "\n ");
            resultSet = dbConfig().executeQuery(util.setQuery());
            ResultSetMetaData rsmd = resultSet.getMetaData();
            List <String> list = new ArrayList <>();
            int columnsNumber = rsmd.getColumnCount();
            String colName[] = new String[columnsNumber];
            for (int i = 1; i <= columnsNumber; i++) {
                colName[i - 1] = rsmd.getColumnName(i);
            }
            log.info(" \t " + " \t " + " \t " + " \t " + Util.readProperties("datasuccessFetch") + "\n");
            if (!resultSet.isBeforeFirst() && resultSet.getRow() == 0) {
                System.out.println(" \t" + Util.readProperties("unavailabeMsg"));
            }
            for (int i = 1; i <= columnsNumber; i++) {
                colName[i - 1] = rsmd.getColumnName(i);
            }
            System.out.println();
            for (int i=1 ;i<columnsNumber;i++) {
                System.out.printf("%-15s", colName[i]);
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 2; i <= columnsNumber; i++) {
                    list.add(resultSet.getString(i));
                }
                System.out.printf("%15s %n ", list + "\n");
                list.removeAll(list);
            }
        } catch (SQLException e) {
            log.info(e.getLocalizedMessage());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%25s %n " ,Util.readProperties("userInputReEnter"));
        String temp=scanner.nextLine();
        if(temp.equalsIgnoreCase("NO"))
        {
            System.out.printf("%25s %n " , Util.readProperties("exitMessage"));
            ApparelSEO.flag=false;
        }

        //System.out.println("\t" + "\t" + "\t" + "\t " + Util.readProperties("userInputReEnter") + "\n");
    }

    public void dropTable() {
        try {
            String query = Util.readProperties("dropTable");
            dbConfig().executeUpdate(query);
        } catch (SQLException e) {
            log.info(e.getLocalizedMessage());
        }
    }

    public Statement dbConfig() {
        try {
            connection = DriverManager.getConnection(Util.readProperties("url"), Util.readProperties("dbUsername"), Util.readProperties("dbPassword"));
            statement = connection.createStatement();
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
        }
        return statement;
    }
}



