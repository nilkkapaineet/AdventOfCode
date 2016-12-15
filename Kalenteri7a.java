package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;


public class Kalenteri7a {

    public static void main(String[] args) {

        // read file
        List<String> input = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader("cal7.txt"))) {
            //  FileReader fileReader = new FileReader(filename);
            //   BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            while ((line = br.readLine()) != null) {
                input.add(line);
            }
            br.close();
            input.toArray(new String[input.size()]);
        } catch(IOException e) {
            e.printStackTrace();
        }


        // Find text between []
        boolean abbaBetweenBrackets = false;
        String pattern = "\\[(.*?)\\]";
        Pattern p = Pattern.compile(pattern);
        int counter = 0;
        for (String str : input) {
            Matcher match = p.matcher(str);
            while (match.find()) {
                String ptrn = match.group(1);
                // find if there's ABBA
                abbaBetweenBrackets = includesABBA(ptrn);
                ptrn = "\\[" + ptrn + "\\]";
                str = removeRegex(ptrn, str);
            }
            // find if there's ABBA
            // original string should be splitted
            String[] splitArray = str.split("\\.");
            int noOfAbbas = 0;
            for(String allStr : splitArray) {
                if (includesABBA(allStr)) {
                    noOfAbbas++;
                }
            }
            boolean isABBA = false;
            if (noOfAbbas == 1) {
                isABBA = true;
            }
            if (isABBA && !abbaBetweenBrackets) {
                //System.out.println("Perfect! " + str);
                counter++;
            }
        }
        System.out.println("Found no of abbas: " + counter);
    }

    public static boolean includesABBA(String input) {
        // input should have only one abba!
        int numberOfAbbas = 0;
        // increment through input
        for(int i=1;i<input.length()-2;i++) {
            char currentChar = input.charAt(i);
            char nextChar = input.charAt(i+1);
            if (currentChar == nextChar) {
                // found two same characters following each others
                // check if also ABBA
                char a = input.charAt(i-1);
                char b = input.charAt(i+2);
                if (a == b) {
                    if (a != currentChar) {
                        // interior chars must be different from outer chars
                        numberOfAbbas++;
                    }
                }
            }
        }
        if (numberOfAbbas == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static String removeRegex(String regex, String input) {
        input = input.replaceAll(regex, ".");
        return input;
    }

    public static String getTextBetweenRegex(String regex, String input) {
        // Find text between []
        //String pattern = "\\[(.*?)\\]";
        Pattern p = Pattern.compile(regex);
        Matcher match = p.matcher(input);
        if (match.find()) {
            return match.group(1);
        } else {
            return "";
        }
    }
}
