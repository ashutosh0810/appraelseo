package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class Querries {
    static Scanner input;
    String query1="";
    String query2="";
    String query3="";;
    String query4="";

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
        System.out.println(colr);
        System.out.println(size);
        System.out.println(gen);
        System.out.println(pref);


    }

}