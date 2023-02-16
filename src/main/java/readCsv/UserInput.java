package readCsv;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
public class UserInput {
    Scanner scanner = new Scanner(System.in);
    private String colur;
    private String size;
    private String gender;
    private String price;
    private String rating;
    private String bothpref;

    public UserInput() {
    }

    public void setParameters() {
        System.out.println(Util.readProperties("colorInput"));
        setColur(scanner.nextLine().trim());
        System.out.println(Util.readProperties("sizeInput"));
        setSize(scanner.nextLine().trim());
        System.out.println(Util.readProperties("genderInput"));
        setGender(scanner.nextLine().trim());
        System.out.println(Util.readProperties("price"));
        setPrice(scanner.nextLine().trim());
        while (!(getPrice().equalsIgnoreCase("yes") || getPrice().equalsIgnoreCase("no"))) {
            System.out.println("\n " + Util.readProperties("userInputPriceVald") + " \n");
            System.out.println(Util.readProperties("price"));
            setPrice(scanner.nextLine().trim());
        }
        String rating = "";
        String both = "";
        if (getPrice().equalsIgnoreCase("Yes")) {
            System.out.println(" \n " + Util.readProperties("userInputPriceAck") + " \n ");
        } else if (getPrice().equalsIgnoreCase("No")) {
            System.out.println(Util.readProperties("rating"));
            setRating(scanner.nextLine());
            while (!(getRating().equalsIgnoreCase("yes") || getRating().equalsIgnoreCase("no"))) {
                System.out.println(" \n" + Util.readProperties("userInputRatingVald") + " \n ");
                System.out.println(Util.readProperties("rating"));
                setRating(scanner.nextLine().trim());
            }
            if (getRating().equalsIgnoreCase("yes")) {
                System.out.println(" \n " + Util.readProperties("userInputRatingAck"));
            } else if (getRating().equalsIgnoreCase("No")) {
                System.out.println(Util.readProperties("bothPref"));
                setBothpref(scanner.nextLine());
                while (!(getBothpref().equalsIgnoreCase("yes") || getBothpref().equalsIgnoreCase("no"))) {
                    System.out.println(" \n " + Util.readProperties("userInputBothprefVald"));
                    System.out.println(Util.readProperties("bothPref"));
                    setBothpref(scanner.nextLine().trim());
                }
                if (getBothpref().equalsIgnoreCase("Yes")) {
                    System.out.println(" \n " + Util.readProperties("userInputbothPrefAck") + "\n ");
                } else {
                    System.out.println(" \n " + Util.readProperties("defaultpreference") + " \n ");
                }
            }
        }
        System.out.println(" \n" + Util.readProperties("defaultMsg") + "\n ");
    }
}