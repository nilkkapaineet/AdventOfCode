package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kalenteri9a {

    public static void main(String[] args) {
        // write your code here

        /*
        String[] input = new String[6];
        input[0] = "ADVENT";
        input[1] = "A(1x5)BC"; // repeats only the B a total of 5 times, becoming ABBBBBC for a decompressed length of 7.
        input[2] = "(3x3)XYZ"; // becomes XYZXYZXYZ for a decompressed length of 9.
        input[3] = "A(2x2)BCD(2x2)EFG"; //doubles the BC and EF, becoming ABCBCDEFEFG for a decompressed length of 11.
        input[4] = "(6x1)(1x3)A"; //simply becomes (1x3)A - the (1x3) looks like a marker,
        // but because it's within a data section of another marker, it is not treated any differently from
        // the A that comes after it. It has a decompressed length of 6.
        input[5] = "X(8x2)(3x3)ABCY"; //becomes X_(3x3)ABC_(3x3)ABC_Y (for a decompressed length of 18), because
        // the decompressed data from the (8x2) marker (the (3x3)ABC) is skipped and not processed further.
        // XABCABCABC_ABCABCABC_ABCABCABC_|ABCABCABC_ABCABCABC_ABCABCABC_Y ei näin
        */
        // read file
        List<String> file = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader("cal9.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                file.add(line);
            }
            br.close();
            file.toArray(new String[file.size()]);
        } catch(IOException e) {
            e.printStackTrace();
        }

        Decompressor dc = new Decompressor();

        int counter = 0;
        for (String input : file) {
            if (dc.containsDigits(input)) {
                int[] positions = dc.matchFirst(input);
                // pos 0 = position (, pos 1 = position ) , pos 2 = howManyCharsToRepeat, pos 3 = repeatValue
                String retval = dc.decompress(input, positions[0], positions[1], positions[2], positions[3]);
                System.out.println("jes: " + retval);
                counter += retval.length();
            } else {
                System.out.println("no: " + input);
                counter += input.length();
            }
            //System.out.println("");
        }
        System.out.println("Total length: " + counter);
    }
}

    class Decompressor {

        public Decompressor() {
            // ?
        }

        public String decompress(String input, int openPar, int closePar, int howManyChars, int howManyTimes) {
            String restOfString = stripInput(input, closePar+1);
            String beginningOfString = input.substring(0, openPar);
            String repeatedString = restOfString.substring(0, howManyChars);
            // if repeatedString contains marker, it's not one
            String tempString = "";
            for(int i=0;i<howManyTimes;i++) {
                  tempString += repeatedString;
            }
            restOfString = stripInput(restOfString, howManyChars);
            String retval = beginningOfString + tempString;
            // take string that is not repeated
            boolean moreToRepeat = false;
            if (howManyChars < restOfString.length()) {
                retval += restOfString.substring(0, openPar);
                String notRepeated = stripInput(restOfString, howManyChars-1);
               // System.out.println("rest: " + notRepeated);
                if (containsDigits(notRepeated)) {
                    // System.out.println("joo");
                    int[] tempInt = matchFirst(notRepeated);
                    retval += decompress(notRepeated, tempInt[0], tempInt[1], tempInt[2], tempInt[3]);
                    moreToRepeat = true;
                }
            }
            if (!moreToRepeat) {
                retval = beginningOfString + tempString + restOfString;
            }
            // if rest of string contains marker, call again
            //System.out.println(retval);
            return retval;
        }

        public String stripInput(String input, int position) {
            // removes chars from beginning of the string until given position
            return input.substring(position);
        }

        public boolean containsDigits(String input) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(input);
            if (m.find()) {
                return true;
            } else {
                return false;
            }
        }

        public int[] matchFirst(String input) {
            int retval[] = new int[4];
            // 0: position of first (, 1: position of first ), 2: value of first int, 3: value of second int
            // if not found, both are zero
            retval[0] = input.indexOf("(");
            retval[1] = input.indexOf(")");
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(input);
            if (m.find()) {
                retval[2] = Integer.valueOf(m.group());
            } else {
                retval[2] = 0;
            }
            String tempString = input.substring(input.indexOf("x"));
            m = p.matcher(tempString);
            if (m.find()) {
                retval[3] = Integer.valueOf(m.group());
            } else {
                retval[3] = 0;
            }
            return retval;
        }

        public int[] getNumbers(String input) {
            // input type: rotate column x=7 by 2
            // input type: "(6x1)(1x3)A"
            //
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
    }

