package readCsv;

import lombok.ToString;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@ToString
public class Util {
    String inputColour;
    String inputSize;
    String inputGender;
    String price;
    String rating;
    String both;

    public Util(String inputColour, String inputSize, String inputGender, String price, String rating, String both) {
        this.inputColour = inputColour;
        this.inputSize = inputSize;
        this.inputGender = inputGender;
        this.price = price;
        this.rating = rating;
        this.both = both;
    }

    public String setQuery() {
        if (price.equalsIgnoreCase("yes")) {
            return "Select * from apparel.dress where colour in (\"" + inputColour + "\")" +
                    " AND SIZE IN (\"" + inputSize + "\")" + " AND   GENDER_RECOMMENDATION IN (\""
                    + inputGender + "\" , \"U\")"
                    + " ORDER BY PRICE ; ";
        } else if (rating.equalsIgnoreCase("yes")) {
            return "Select * from apparel.dress where colour in (\"" + inputColour + "\")" +
                    " AND SIZE IN (\"" + inputSize + "\")" + " AND   GENDER_RECOMMENDATION IN (\""
                    + inputGender + "\" , \"U\")"
                    + " ORDER BY RATING DESC ; ";
        } else if (both.equalsIgnoreCase("yes")) {
            return "Select * from apparel.dress where colour in (\"" + inputColour + "\")" +
                    " AND SIZE IN (\"" + inputSize + "\")" + " AND   GENDER_RECOMMENDATION IN (\""
                    + inputGender + "\" , \"U\")"
                    + " ORDER BY PRICE, RATING DESC ; ";
        } else {
            return "Select * from apparel.dress where colour in (\"" + inputColour + "\")" +
                    " AND SIZE IN (\"" + inputSize + "\")" + " AND  GENDER_RECOMMENDATION IN (\""
                    + inputGender + "\" , \"U\") ;";
        }
    }

    public static String readProperties(String key) {
        Properties prop = new Properties();
        String value = "";
        try (InputStream input = new FileInputStream("src/main/resources/data.properties")) {
            prop.load(input);
            value = prop.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String[] countTheFiles() {
        String filePath = Util.readProperties("fileRootFolder");
        File folder = new File(filePath);
        String[] nameofFiles = new String[folder.listFiles().length];
        for (int i = 0; i < folder.listFiles().length; i++) {
            nameofFiles[i] = folder.listFiles()[i].toString();
        }
        return nameofFiles;
    }
}


