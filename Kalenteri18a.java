package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Kalenteri18a {

    public static void main(String[] args) {
        int rows = 400000;
        String input = ".^.^..^......^^^^^...^^^...^...^....^^.^...^.^^^^....^...^^.^^^...^^^^.^^.^.^^..^.^^^..^^^^^^.^^^..^";
        // . = safe = true
        // ^ = trap  false
	// write your code here
        char[] ca = input.toCharArray();
        boolean[] b = new boolean[ca.length+2];
        for(int i=0;i<ca.length;i++) {
            if (ca[i] == '.') {
                b[i+1] = true;
            } else {
                b[i+1] = false;
            }
        }
        b[0] = true;
        b[ca.length+1] = true;

        ArrayList<boolean[]> floor = new ArrayList();
        floor.add(b);
        for(int i=0;i<rows-1;i++) {
            boolean[] nextRow = new boolean[b.length];
            for(int j=0;j<b.length-2;j++) { // loop through current row
                boolean left = b[j];
                boolean center = b[j+1];
                boolean right = b[j+2];
                if (!left && !center && right) {
                    nextRow[j+1] = false;
                } else if (!center && !right && left) {
                    nextRow[j+1] = false;
                } else if (center && right && !left) {
                    nextRow[j+1] = false;
                } else if (center && left && !right) {
                    nextRow[j+1] = false;
                } else {
                    nextRow[j+1] = true;
                }
            //    System.out.println(i+ " left: " + left + " center: " + center + " right: " +right + " Result: " + nextRow[j+1]);
            }
            nextRow[0] = true;
            nextRow[nextRow.length-1] = true;
            floor.add(nextRow);
            b = Arrays.copyOf(nextRow, nextRow.length);
        }
        printFloor(floor);

    }
    public static void printFloor(ArrayList<boolean[]> floor) {
        int counter = 0;
        for (boolean[] row : floor) {
            for(int i=1;i<row.length-1;i++) { // there are extra tiles i.e. walls
                if(row[i]) {
                 //   System.out.print(".");
                    counter++;
                } else {
                   // System.out.print("^");
                }
            }
            //System.out.println("");
        }
        System.out.println("Floor has " + counter + " safe tiles.");
    }
}
