package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kalenteri8a {

    public static void main(String[] args) {
	// write your code here
        String[][] display = new String[6][50];

        for(int i=0;i<display.length; i++) {
            for(int j=0;j<display[1].length; j++) {
                display[i][j] = ".";
            }
        }

        // read file
        List<String> input = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader("cal8.txt"))) {
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

        // operations

        /*
        display = rect("rect 1x12", display);
        display = rect("rect 3x4", display);
        //System.out.println(rectSize[0] + "x" + rectSize[1]);
        System.out.println("1--------------------");
        printDisplay(display);
        display = rotateRow("rotate row y=2 by 8", display);
        System.out.println("2--------------------");
        printDisplay(display);
        display = rotateColumn("rotate column x=2 by 3", display);
        System.out.println("3--------------------");
        printDisplay(display);
        */
        // find either rect, row or column and make the operation
        // loop through list array
        for (String str : input) {
            String isRect = str.substring(0,4);
            if (isRect.equals("rect")) {
                display = doRect(str, display);
            } else {
                String RorC = str.substring(0,8);
                if (RorC.equals("rotate c")) {
                    display = rotateColumn(str, display);
                } else {
                    display = rotateRow(str, display);
                }
            }
        }
        printDisplay(display);
        System.out.println("Number of pixels lit: " + howManyLit(display));

    }

    public static int howManyLit(String[][] display) {
        // if # count one
        int counter = 0;
        for(int i=0;i<display.length; i++) {
            for(int j=0;j<display[1].length; j++) {
                if (display[i][j].equals("#")) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public static String[][] rotateRow(String input, String[][] display) {
        int[] rectangle = getNumbers(input);

        // increment every row no rectangle[0] pos by rectangle[1]

        // copy matrix
        String[][] sDisp2 = cloneArray(display);

        for(int i=0;i<rectangle[1];i++) {
            for (int j = 0; j < display[1].length; j++) {
                // increment string char by char, last char will be the new first
                if (j == 0) {
                    // first index, take last index to first
                    sDisp2[rectangle[0]][0] = display[rectangle[0]][display[1].length - 1];
                } else {
                    sDisp2[rectangle[0]][j] = display[rectangle[0]][j - 1];
                }
            }
            display = cloneArray(sDisp2);
        }
        return sDisp2;
        // works fine
    }

    public static String[][] cloneArray(String[][] src) {
        int length = src.length;
        String[][] target = new String[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public static int[] getNumbers(String input) {
        // input type: rotate column x=7 by 2
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(input);
        boolean found = false;
        int[] rectangle = new int[2];
        while (m.find()) {
            if (!found) {
                rectangle[0] = Integer.parseInt(m.group());
                found = true;
            } else {
                rectangle[1] = Integer.parseInt(m.group());
            }
        }
        return rectangle;
    }

    public static String[][] rotateColumn(String input, String[][] display) {
        int[] rectangle = getNumbers(input);
        // copy matrix
        String[][] sDisp2 = cloneArray(display);
        // increment every column no rectangle[0] pos by rectangle[1]

        for(int i=0;i<rectangle[1];i++) {
            for (int j = 0; j < display.length; j++) {
                // increment string char by char, last char will be the new first
                if (j == 0) {
                    // first index, take last index to first
                    sDisp2[0][rectangle[0]] = display[(display.length)-1][rectangle[0]];
                } else {
                    sDisp2[j][rectangle[0]] = display[j - 1][rectangle[0]];
                }
            }
            display = cloneArray(sDisp2);
        }
        return display;
        // works fine
    }

    public static String[][] doRect(String rectSize, String[][] display) {
        int[] rectangle = getNumbers(rectSize);
        for(int i=0;i<rectangle[0]; i++) {
            for(int j=0;j<rectangle[1]; j++) {
                display[j][i] = "#";
            }
        }
        return display;
    }

    public static void printDisplay(String[][] display) {
        for(int i=0;i<display.length; i++) {
            for(int j=0;j<display[1].length; j++) {
                System.out.print("" + display[i][j]);
            }
            System.out.println("");
        }
    }
}
