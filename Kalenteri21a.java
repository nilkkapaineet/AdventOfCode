package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kalenteri21a {

    public static void main(String[] args) {
        String encode = "abcdefgh";
	// write your code here

        List<String> file = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("cal21.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                file.add(line);
            }
            br.close();
            file.toArray(new String[file.size()]);
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Encoding...");
        for (String input : file) {
            String[] commands = input.split(" ");
            // ------------------- command structure ------------------
            if (commands[0].equals("swap") && commands[1].equals("position")) {
                encode = swapPos(encode, Integer.parseInt(commands[2]), Integer.parseInt(commands[5]));
                System.out.println("swap pos: " + encode);
            } else if (commands[0].equals("swap") && commands[1].equals("letter")) {
                encode = swapLetter(encode, commands[2], commands[5]);
                System.out.println("swap letter: " + encode);
            } else if (commands[0].equals("reverse")) {
                encode = reverse(encode, Integer.parseInt(commands[2]), Integer.parseInt(commands[4]));
                System.out.println("reverse: " + encode);
            } else if (commands[0].equals("rotate") && !commands[1].equals("based")) {
                encode = rotateLR(encode, commands[1], Integer.parseInt(commands[2]));
                System.out.println("rotate RL: " + encode);
            } else if (commands[0].equals("rotate") && commands[1].equals("based")) {
                encode = rotatePos(encode, commands[6]);
                System.out.println("rotate pos: " + encode);
            } else if (commands[0].equals("move")) {
                encode = move(encode, Integer.parseInt(commands[2]), Integer.parseInt(commands[5]));
                System.out.println("move: " + encode);
            } else {
                System.out.println("Illegal input. Shutting down.");
                System.exit(0);
            }
            // ------------------- command structure ------------------
        }
        System.out.println("Encoded message: " + encode);

    }

    public static String swapPos(String input, Integer x, Integer y) {
        char cx = input.charAt(x);
        char cy = input.charAt(y);
        char[] ca = input.toCharArray();
        ca[y] = cx;
        ca[x] = cy;
        String retval = String.valueOf(ca);
        return retval;
    }
    public static String swapLetter(String input, String x, String y) {
        int xpos = input.indexOf(x);
        int ypos = input.indexOf(y);
        char[] ca = input.toCharArray();
        System.out.println(input + " x: " + x + " xpos: " + xpos + " y: " + y + " ypos: " + ypos);
        ca[xpos] = y.charAt(0);
        ca[ypos] = x.charAt(0);
        String retval = String.valueOf(ca);
        return retval;
    }
    public static String reverse(String input, Integer x, Integer y) {
        // all letters between pos x and y must be reversed in order
        char[] ca = input.toCharArray();
        char[] tempCA = Arrays.copyOf(ca, ca.length);
        int howLong = y;
        for(int i=x;i<=howLong;i++){
            ca[i] = tempCA[y];
            y--;
        }
        String retval = String.valueOf(ca);
        return retval;
    }
    public static String rotateRight(String input, Integer steps) {
        steps = steps%input.length();
        char[] ca = input.toCharArray();
        char[] newCA = new char[ca.length];
        for(int i=0;i<ca.length;i++) {
            if (ca.length-steps+i >= ca.length) {
                newCA[i] = ca[i - steps];
            } else {
                newCA[i] = ca[ca.length - steps + i];
            }
        }
        String retval = String.valueOf(newCA);
        return retval;
    }
    public static String rotateLeft(String input, Integer steps) {
        steps = steps%input.length();
        char[] ca = input.toCharArray();
        char[] newCA = new char[ca.length];
        for(int i=0;i<ca.length;i++) {
            if (i+steps < ca.length){
                newCA[i] = ca[i+steps];
            } else{
                newCA[i] = ca[i+steps-ca.length];
            }
        }
        String retval = String.valueOf(newCA);
        return retval;
    }
    public static String rotateLR(String input, String direction, Integer steps) {
        if (direction.equals("right")) {
            return rotateRight(input, steps);
        } else if (direction.equals("left")){
            return rotateLeft(input, steps);
        } else {
            System.out.println("Problem without input. Shutting down.");
            System.exit(0);
        }
        return ("failed");
    }
    public static String rotatePos(String input, String letter) {
        int steps = input.indexOf(letter)+1;
        if (input.indexOf(letter) >= 4) {
            steps += 1;
        }
        return rotateRight(input, steps);
    }
    public static String move(String input, Integer x, Integer y) {
        char[] ca = input.toCharArray();
        char[] newCA = new char[ca.length];
        if (x<y) {
            if (x > 0) {
                for (int i = 0; i < x; i++) {
                    newCA[i] = ca[i];
                }
            }
            if (ca.length > y) {
                for (int i=y+1;i<ca.length;i++) {
                    newCA[i] = ca[i];
                }
            }
            // everything between x and y is decremented one position
            for(int i=x;i<y;i++) {
                newCA[i] = ca[i+1];
            }
            newCA[y] = ca[x];
        } else {
          //  System.out.println(input + " x: " + x + " y: " + y);
            if (y > 0) {
                for (int i = 0; i < y; i++) {
                    newCA[i] = ca[i];
                }
            }
            if (ca.length > x) {
                for (int i=x+1;i<ca.length;i++) {
                    newCA[i] = ca[i];
                }
            }
            // everything between y and x is incremented one position
            for(int i=y+1;i<=x;i++) {
                newCA[i] = ca[i-1];
            }
            newCA[y] = ca[x];
        }
     //  System.out.println("what: " + input + " x: " + x + " y: " + y);
        String retval = String.valueOf(newCA);
        return retval;
    }
}
