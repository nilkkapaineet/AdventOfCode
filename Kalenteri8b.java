package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kalenteri8b {

    public static void main(String[] args) {
	// write your code here
        Display display = new Display(6, 50);

        // read file
        List<String> input = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader("cal8.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                input.add(line);
            }
            br.close();
            input.toArray(new String[input.size()]);
        } catch(IOException e) {
            e.printStackTrace();
        }
        // find either rect, row or column and make the operation
        // loop through list array
        for (String str : input) {
            String isRect = str.substring(0,4);
            if (isRect.equals("rect")) {
                display.doRect(str);
            } else {
                String RorC = str.substring(0,8);
                if (RorC.equals("rotate c")) {
                    display.rotateColumn(str);
                } else {
                    display.rotateRow(str);
                }
            }
        }
        display.printDisplay();
        System.out.println("Number of pixels lit: " + display.howManyLit());
    }
}

class Display {

    private int height;
    private int width;
    String[][] display;

    public Display(int h, int w) {
        width = w;
        height = h;
        display = new String[height][width];
        this.initDisplay();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void initDisplay() {
        for(int i=0;i<this.getHeight(); i++) {
            for(int j=0;j<this.getWidth(); j++) {
                display[i][j] = ".";
            }
        }
    }

    public int howManyLit() {
        // if # count one
        int counter = 0;
        for(int i=0;i<this.getHeight(); i++) {
            for(int j=0;j<this.getWidth(); j++) {
                if (display[i][j].equals("#")) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public void printDisplay() {
        for(int i=0;i<this.getHeight(); i++) {
            for(int j=0;j<this.getWidth(); j++) {
                System.out.print("" + display[i][j]);
            }
            System.out.println("");
        }
    }

    public void doRect(String rectSize) {
        int[] rectangle = getNumbers(rectSize);
        for(int i=0;i<rectangle[1]; i++) {
            for(int j=0;j<rectangle[0]; j++) {
                display[i][j] = "#";
            }
        }
    }

    public int[] getNumbers(String input) {
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

    public void rotateColumn(String input) {
        int[] rectangle = getNumbers(input);
        // copy matrix
        String[][] sDisp2 = cloneArray(display);
        // increment every column no rectangle[0] pos by rectangle[1]
//System.out.println("dl: " + display.length + " re0: " + rectangle[0] + " ja d: " + display[4][2]);
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
    }

    private String[][] cloneArray(String[][] src) {
        int length = src.length;
        String[][] target = new String[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public void rotateRow(String input) {
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
    }

}
