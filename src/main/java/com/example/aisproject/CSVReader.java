package com.example.aisproject;

import java.io.*;
import java.util.Scanner;

public class CSVReader {

    public String[][] readCSVFile(String fileName) {
        String result[][] = new String[42][12];
        try {



            Scanner sc = new Scanner(new File("src/main/java/com/example/aisproject/NewProduceStats.csv"));
            sc.useDelimiter(",");   //sets the delimiter pattern

            int i = 0;
            int j = 0;
            while (sc.hasNext())  //returns a boolean value
            {
                result[i][j] = sc.next();  //find and returns the next complete token from this scanner

                if(j < Application.NUM_ENTRIES + 1){
                    j++;
                }else{
                    j = 0;
                    i++;
                }

            }



        }catch (Exception e){

        }

        return result;
    }

}
