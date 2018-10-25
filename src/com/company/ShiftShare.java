package com.company;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class ShiftShare {
    // Declare Class Variables //
    // 2 Multi-dimensional Arrays for storing the text data
    public static double tableOneData [][] = new double[5][12];
    public static double tableTwoData [][] = new double[5][12];
    // 2 Traditional Arrays for storing summed columns
    public static double nDotK1 [] = new double [12];
    public static double nDotK2 [] = new double [12];
    // Two ints for storing summed tables
    public static double nDotDot1 = 0;
    public static double nDotDot2 = 0;

    public static void main(String[] args) {
        // Import Data File and populate table 1 and table 2
        try {
            File file = new File("C:\\Users\\Emily\\Downloads\\data.txt");
            Scanner scanner = new Scanner(file);
            // Setup variables from external data sheet
            // Use counter to determine when to switch to the other array.
            int counter = 0;
            int j = 0;
            int k = 0;
            while (scanner.hasNextLine()) {
                int i = scanner.nextInt();
                // the next 2 if's prevent us from trying to write to an array index that does not exist
                if (k > 11) {
                    k = 0;
                    j++;
                }
                // prep for the new table
                if (j > 4) {
                    j = 0;
                }
                // index 0 -> 59, table 1
                if (counter < 60) {
                    // Populate n..1
                    nDotDot1 += i;
                    tableOneData[j][k] = i;
                }
                // index 60 -> 119, table 2
                else {
                    // Populate n..2
                    nDotDot2 += i;
                    tableTwoData[j][k] = i;
                }
                k++;
                counter++;
            }
            scanner.close();
        } catch (Exception ex) { }
        // Populate the n.k1 and n.k2 arrays
        for (int x = 0; x <= 11; x++) {
            nDotK1[x] = AddUpColumn(1, x);
            nDotK2[x] = AddUpColumn(2, x);
        }
        // write to file
        try {
            FileWriter fw = new FileWriter("c:\\temp\\componentsOut.txt", false);
            PrintWriter outputFile = new PrintWriter(fw);
            for (int i = 0; i <= 4; i++) {
                outputFile.println(i);
                outputFile.println((int) solveForComponent1(i));
                outputFile.println((int) solveForComponent2(i));
                outputFile.println((int) solveForComponent3(i));
            }
            outputFile.close();
        } catch (Exception ex) {
        }

    }
        // Add up column, for use in n.k1 and n.k2 arrays and components
        public static double AddUpColumn ( int tableNum, int index){
            double[][] arrayOfData;
            if (tableNum == 1) {
                arrayOfData = tableOneData;
            } else {
                arrayOfData = tableTwoData;
            }
            double total = arrayOfData[0][index];
            total += arrayOfData[1][index];
            total += arrayOfData[2][index];
            total += arrayOfData[3][index];
            total += arrayOfData[4][index];
            return total;
        }
        public static double solveForComponent1 ( int j){
            double total = 0.0;
            for (int k = 0; k <= 11; k++) {
                total += tableOneData[j][k] * ((nDotK2[k] / nDotK1[k]) - (nDotDot2 / nDotDot1));
            }
            return total;

        }
        public static double solveForComponent2 ( int j){
            double total = 0.0;
            for (int k = 0; k <= 11; k++) {
                total += tableOneData[j][k] * ((tableTwoData[j][k] / tableOneData[j][k]) - (nDotK2[k] / nDotK1[k]));
            }
            return total;
        }
        public static double solveForComponent3 ( int j){
            double total = 0.0;
            for (int k = 0; k <= 11; k++) {
                total += tableOneData[j][k] * ((nDotDot2 / nDotDot1) - 1);
            }
            return total;
        }
    }
