package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kalenteri10a {
    // tyylikkäin toteutus olisi laittaa targetit robottiolion sisään:
    /*
    public Robot() {
        targetBin
        targetRobot
    }
    tällöin kohteisiin pääsee feedChipillä suoraan käsiksi, eikä robotteja tarvitse laittaa argumenttiin
    aluksi olisi vielä järjestettävä kohteet pienimmästä numerosta alkaen, jotta ne päästään luomaan asiallisesti
    samalla robotteja tulee vain tarvittu määrä
     */

    public static void main(String[] args) {
        int[] interestingValues = new int[2];
        interestingValues[0] = 17; // low
        interestingValues[1] = 61; // high
        // write your code here
        List<String> file = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader("cal10.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                file.add(line);
            }
            br.close();
            file.toArray(new String[file.size()]);
        } catch(IOException e) {
            e.printStackTrace();
        }

        Robot[] robots = new Robot[300];
        OutputBin[] opBins = new OutputBin[300];
        for(int i = 0; i < 300; i++) {
            opBins[i] = new OutputBin(i);
            robots[i] = new Robot(i);
        }

        List<String> temp1 = new ArrayList<String>(); // values bottom, instructions first
        List<String> temp2 = new ArrayList<String>();
        for (String input : file) {
            if (input.substring(0, 2).equals("va")) {
                temp1.add(input);
            } else {
                // instructions
                temp2.add(input);
            }
        }
        for (String input : temp1) {
            temp2.add(input);
        }

            for (String input : temp2) {
                System.out.println(input);
                if (input.substring(0, 2).equals("va")) {
                    // command is value x
                    // match numbers, 0: value , 1: robot number
                    int[] numbers = getNumbers(input);
                    // assign chip
                    robots[numbers[1]].feedChip(numbers[0], robots, interestingValues);
                } else {
                    // input: "bot 2 gives low to bot 1 and high to bot 0";
                    // or 0: giverBot, 1: bin1, 2: pos1, 3: bin2, 4: pos2
                    // 4 digits before number are either bot or put
                    int[] numbers = getNumbers(input);
                    // check if taker is a bot or bin
                    String binbot1 = input.substring(numbers[4]-4,numbers[4]);
                    String binbot2 = input.substring(numbers[4]-4,numbers[4]);
                    boolean targets[] = new boolean[2];
                    if (binbot1.equals("bot ")) {
                        targets[0] = true;
                    } else {
                        targets[0] = false;
                    }
                    if (binbot2.equals("bot ")) {
                        targets[1] = true;
                    } else {
                        // give them to output bin
                        //opBins[numbers[3]].dump(higher);
                        targets[1] = false;
                    }
                    robots[numbers[0]].takeInstructions(numbers, targets);
                    //robots[numbers[0]].removeChips();
                }
            }
    }

    public static int[] getNumbers (String input){
        // input: "bot 1 gives low to output 1 and high to bot 0";
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(input);
        boolean found = false;
        boolean foundAgain = false;
        int[] rectangle = new int[5];
        // r3 firstbin/bot, r5 second bin/bot
        while (m.find()) {
            if (!found) {
                rectangle[0] = Integer.parseInt(m.group());
                found = true;
            } else if (!foundAgain){
                rectangle[1] = Integer.parseInt(m.group());
                // find position of number
                rectangle[2] = m.start();
                foundAgain = true;
            } else {
                rectangle[3] = Integer.parseInt(m.group());
                rectangle[4] = m.start();
            }
        }
        return rectangle;
    }
}

class Robot {
    private int[] chip = new int[2];
    private int number;
    private int[] lowTarget = new int[2];
    private int[] highTarget = new int[2];
    // if target[0] = 1 then target is another bot, if target[0] = 2 target is outputbin
    // target[1] stores bin/bot target number
    // chips are 0 if empty
    public Robot(int n) {
        this.number = n;
        this.chip[0] = 0;
        this.chip[1] = 0;
        this.lowTarget[0] = 0;
        this.lowTarget[1] = 0;
        this.highTarget[0] = 0;
        this.highTarget[1] = 0;
    }

    public int[] getTargets() {
        int[] retval = new int[4];
        retval[0] = this.lowTarget[0]; // 1: bot, 2: bin
        retval[1] = this.lowTarget[1]; // target number
        retval[2] = this.highTarget[0];
        retval[3] = this.highTarget[1];
        return retval;
    }

    private void interestingChips (int[] iv, int chip1, int chip2) {
        int lower;
        int higher;
        if (chip1 < chip2) {
            lower = chip1;
            higher = chip2;
        } else {
            lower = chip2;
            higher = chip1;
        }
        if (lower == iv[0] && higher == iv[1]) {
            System.out.println("Bot " + this.getNumber() + " did the interesting deal");
            System.exit(0);
        }
    }

    public int getNumber() {
        return number;
    }

    public boolean moveAllowed() {
        if (this.chip[0] != 0 && this.chip[1] != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean feedChip(int value, Robot[] robots, int[] interestingValues) {
        // returns true if full
        // put chip into a empty slot
        int[] values = this.getChipValues();
        if (values[0] == 0) {
            this.chip[0] = value;
            return false;
        }
        if (values[1] == 0) {
            interestingChips(interestingValues, this.chip[0], value);
            this.chip[1] = value;
            // recursive loop
            // sort chip values
            int lowChip = 0;
            int highChip = 0;
            if (this.chip[0] < this.chip[1]){
                lowChip = this.chip[0];
                highChip = this.chip[1];
            } else {
                lowChip = this.chip[1];
                highChip = this.chip[0];
            }
            if (lowTarget[0] == 1) {
                robots[lowTarget[1]].feedChip(lowChip, robots, interestingValues);
            }
            if (highTarget[0] == 1) {
                robots[highTarget[1]].feedChip(highChip, robots, interestingValues);
            }
            return true;
        }
        return false;
    }

    public void takeInstructions(int[] targets, boolean[] targetIsBot) {
        // or 0: giverBot, 1: bin1, 2: pos1, 3: bin2, 4: pos2
        // boolean 0: lowTarget, 1: highTarget
        this.lowTarget[1] = targets[1];
        this.highTarget[1] = targets[3];
        if (targetIsBot[0]) {
            this.lowTarget[0] = 1;
        } else {
            this.lowTarget[0] = 2;
        }
        if (targetIsBot[1]) {
            this.highTarget[0] = 1;
        } else {
            this.highTarget[0] = 2;
        }
    }

    public int[] getChipValues() {
        return this.chip;
    }

    public void removeChips() {
        this.chip[0] = 0;
        this.chip[1] = 0;
    }
}

class OutputBin {
    private List<Integer> bin = new ArrayList<Integer>();
    private int number;
    public OutputBin(int n) {
        this.number = n;
    }
    public void dump(int chip) {
        bin.add(chip);
    }
    public int getNumber() {
        return number;
    }
    public void printBin() {
        System.out.print("Bin " + this.getNumber() + " contains: ");
        for (int i : this.bin) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }
}