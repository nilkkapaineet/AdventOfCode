package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;

public class Kalenteri12b {

    public static void main(String[] args) {
        int noOfRegs = 4;
	// write your code here
        /*
        cpy x y copies x (either an integer or the value of a register) into register y.
inc x increases the value of register x by one.
dec x decreases the value of register x by one.
jnz x y jumps to an instruction y away (positive means forward; negative means backward), but only if x is not zero.
         */
        List<String> file = new ArrayList<String>();

        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("cal12.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                file.add(line);
                counter++;
            }
            br.close();
            file.toArray(new String[file.size()]);
        } catch(IOException e) {
            e.printStackTrace();
        }
        String[] commands = new String[counter];
        int index = 0;
        for (String input : file) {
            commands[index] = input;
            index++;
        }

        Register[] regs = new Register[noOfRegs];
        Character charI = 'a';
        for(int i=0;i<noOfRegs;i++) {
            regs[i] = new Register(charI);
            // only one change needed to complete part 2
            if(charI.equals('c')){
                regs[i].assignValue(1);
            }
            charI++;
        }

        for(int i=0;i<counter;i++) {
            String str = commands[i].substring(0, 3);
            if (str.equals("cpy")) {
                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher(commands[i]);
                int found = 0;
                while (m.find()) {
                    found = Integer.parseInt(m.group());
                }
                Character target = commands[i].charAt(commands[i].length()-1);
                int targetReg = charToInt(target, regs, noOfRegs);
                if (found == 0) {
                    // no copyValue found, then one should copy registerValue
                    int source = charToInt(commands[i].charAt(4), regs, noOfRegs);
                    regs[targetReg].assignValue(regs[source].getValue());
                } else {
                    regs[targetReg].assignValue(found);
                }
            }
            if (str.equals("inc")) {
                Character target = commands[i].charAt(commands[i].length()-1);
                int targetReg = charToInt(target, regs, noOfRegs);
                regs[targetReg].incValue();
            }
            if (str.equals("dec")) {
                Character target = commands[i].charAt(commands[i].length()-1);
                int targetReg = charToInt(target, regs, noOfRegs);
                int formerReg = regs[targetReg].getValue();
                regs[targetReg].decValue();
            }
            if (str.equals("jnz")) {
                // find numbers, if one number only: check register
                // if two numbers, check if first != 0
                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher(commands[i]);
                boolean found = false;
                int[] number = new int[2];
                int foundIndex = 0;
                while (m.find()) {
                    if (!found) {
                        number[0] = Integer.parseInt(m.group());
                        found = true;
                    } else {
                        number[1] = Integer.parseInt(m.group());
                        found = false;
                    }
                }
                if (!found) {
                    // two numbers found
                    if (number[1] != 0) {
                        // jump
                        i += number[1];
                        i--;
                    } // else do nothing
                } else {
                    // one number found, check register
                    Character target = commands[i].charAt(4);
                    int targetReg = charToInt(target, regs, noOfRegs);
                    int regValue = regs[targetReg].getValue();
                    if (regValue != 0) {
                        // jump number[0]
                        i += number[0];
                        i--;
                    } // else do nothing
                }
            }
        }
        // print reg A
        int printReg = charToInt('a', regs, noOfRegs);
        System.out.println("Register A contains value: " + regs[printReg].getValue() );

    }
    private static int charToInt(Character source, Register[] regs, int noOfRegs) {
        int sourceReg = 0;
        for(int i=0;i<noOfRegs;i++) {
            if(source.equals(regs[i].getChar())) {
                sourceReg = i;
            }
        }
        return sourceReg;
    }

}

class Register {
    private int value;
    private Character name;
    public Register(Character n) { name = n; value = 0; }
    public Character getChar() { return name; }
    public int getValue() {
        return value;
    }
    public void assignValue(int v) {
        value = v;
    }
    public void incValue() { value++; }
    public void decValue() { value--; }
}