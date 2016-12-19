package com.company;

import java.security.MessageDigest;

public class Kalenteri14a {

    public static void main(String[] args) {
        String hashInput = "yjdafjpo";
	// write your code here
       System.out.println("Searching...");
        boolean three = false;
        int index = 0;
        int keys = 0;
        while (true) {
            three = containsThreeSeq(hashInput, index);
            if (three) { keys++; }
            if (keys == 64) { break; }
            index++;
        }
        System.out.println("Found 64th key at: " + index + " : " + MD5("abc".concat(Integer.toString(index))));

    }
    public static boolean containsFiveSeq(String input, char toBeFound) {
        // loop through to check similar chars
        for(int i=0;i<input.length()-4;i++) {
            char current = input.charAt(i);
            if (current == toBeFound) {
                // continue searching
                char next1 = input.charAt(i+1);
                char next2 = input.charAt(i+2);
                char next3 = input.charAt(i+3);
                char next4 = input.charAt(i+4);
                if (current == next1 && current == next2 && current == next3 && current == next4) { return true; }
            }  // that was it, take next char
        }
        return false;
    }
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    public static boolean containsThreeSeq(String input, int index) {
        String inputFor5 = input;
        input = input.concat(Integer.toString(index)); // append index
        input = MD5(input);
        // loop through to check similar chars
        for(int i=0;i<input.length()-2;i++) {
            // check next two
            char current = input.charAt(i);
            char next = input.charAt(i+1);
            char following = input.charAt(i+2);
            if (current == next && current == following) {
                boolean retval = false;
                for(int j=1;j<1001;j++) {
                    String tempString = inputFor5.concat(Integer.toString(index+j));
                    tempString = MD5(tempString);
                    boolean fiver = containsFiveSeq(tempString, next);
                    if (fiver) { retval = true; }
                }
                if (retval) { return true; }
                else { return false; }
            }
        }
        return false;
    }
}
