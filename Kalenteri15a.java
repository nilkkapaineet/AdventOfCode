package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kalenteri15a {

    public static void main(String[] args) {
	// write your code here
        String[] input = new String[6];
        input[0] = "Disc #1 has 17 positions; at time=0, it is at position 1.";
        input[1] = "Disc #2 has 7 positions; at time=0, it is at position 0.";
        input[2] = "Disc #3 has 19 positions; at time=0, it is at position 2.";
        input[3] = "Disc #4 has 5 positions; at time=0, it is at position 0.";
        input[4] = "Disc #5 has 3 positions; at time=0, it is at position 0.";
        input[5] = "Disc #6 has 13 positions; at time=0, it is at position 5.";
        Disc[] discs = new Disc[input.length];
        for(int i=0;i<input.length;i++) {
            int[] numbers = getNumbers(input[i]);
            discs[i] = new Disc(numbers[1], numbers[3], numbers[0]);
        }
        // Click when okay
        int counter = 0;
        discs[0].incCurrentPos();
        for(int i=0;i<input.length-1;i++) { discs[i+1].addCurrentPos(i+2); }
        while(true) {
            int[] position = new int[input.length];
            for(int i=0;i<input.length;i++) { position[i] = discs[i].getCurrentPos(); }
            int zeroPosCounter = 0;
            for(int i=0;i<input.length;i++) {
                if (position[i] == 0) { zeroPosCounter++; }
            }
            if (zeroPosCounter == input.length) { break; }
            counter++;
            for(int i=0;i<input.length;i++) { discs[i].incCurrentPos(); }
        }
        System.out.println("Right time to push button is: " + counter);
    }
    public static int[] getNumbers(String input) {
        // input: "Disc #2 has 2 positions; at time=0, it is at position 1.";
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(input);
        int[] rectangle = new int[4];
        int i=0;
        while (m.find()) {
            rectangle[i] = Integer.parseInt(m.group());
            i++;
        }
        return rectangle;
    }
}
class Disc {
    private int maxPositions;
    private int currentPosition;
    private int discNumber;
    public Disc(int m, int c, int d) {
        maxPositions = m;
        currentPosition = c;
        discNumber = d;
    }
    public int getMaxPos() {
        return maxPositions;
    }
    public int getCurrentPos() {
        return currentPosition;
    }
    public void assignCurrentPos(int pos) {
        currentPosition = pos;
    }
    public void incCurrentPos() {
        if (currentPosition+1 < maxPositions) {
            currentPosition += 1;
        } else {
            currentPosition = 0;
        }
    }
    public void addCurrentPos(int added) {
        for(int i=0;i<added;i++) {
            this.incCurrentPos();
        }
    }
    public int getDiscNumber() {
        return discNumber;
    }
}
