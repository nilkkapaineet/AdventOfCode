package com.company;

import java.util.Arrays;
import java.util.*;
import java.util.HashMap;
import java.awt.Component;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Kalenteri6b {

    public static void main(String[] args) {
	// write your code here

        List<String> data3 = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("cal6.txt"))) {
            //  FileReader fileReader = new FileReader(filename);
            //   BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            while ((line = br.readLine()) != null) {
                data3.add(line);
            }
            br.close();
            data3.toArray(new String[data3.size()]);
        } catch(IOException e) {
            e.printStackTrace();
        }
        String[] data = new String[data3.size()];
        data = data3.toArray(data);

        ArrayList al1 = new ArrayList();
        ArrayList al2 = new ArrayList();
        ArrayList al3 = new ArrayList();
        ArrayList al4 = new ArrayList();
        ArrayList al5 = new ArrayList();
        ArrayList al6 = new ArrayList();
        ArrayList al7 = new ArrayList();
        ArrayList al8 = new ArrayList();

        for(int i=0; i<data.length; i++) {
            al1.add(data[i].substring(0,1));
        }
        for(int i=0; i<data.length; i++) {
            al2.add(data[i].substring(1,2));
        }
        for(int i=0; i<data.length; i++) {
            al3.add(data[i].substring(2,3));
        }
        for(int i=0; i<data.length; i++) {
            al4.add(data[i].substring(3,4));
        }
        for(int i=0; i<data.length; i++) {
            al5.add(data[i].substring(4,5));
        }
        for(int i=0; i<data.length; i++) {
            al6.add(data[i].substring(5,6));
        }
        for(int i=0; i<data.length; i++) {
            al7.add(data[i].substring(6,7));
        }
        for(int i=0; i<data.length; i++) {
            al8.add(data[i].substring(7,8));
        }

        String str1 = al1.get(0).toString();
        for(int i=0;i<al1.size();i++) {
            if (i>0) {
                str1 = str1.concat(al1.get(i).toString());
            }
        }

        String str2 = al2.get(0).toString();
        for(int i=0;i<al2.size();i++) {
            if (i>0) {
                str2 = str2.concat(al2.get(i).toString());
            }
        }

        String str3 = al3.get(0).toString();
        for(int i=0;i<al3.size();i++) {
            if (i>0) {
                str3 = str3.concat(al3.get(i).toString());
            }
        }

        String str4 = al4.get(0).toString();
        for(int i=0;i<al4.size();i++) {
            if (i>0) {
                str4 = str4.concat(al4.get(i).toString());
            }
        }

        String str5 = al5.get(0).toString();
        for(int i=0;i<al5.size();i++) {
            if (i>0) {
                str5 = str5.concat(al5.get(i).toString());
            }
        }

        String str6 = al6.get(0).toString();
        for(int i=0;i<al6.size();i++) {
            if (i>0) {
                str6 = str6.concat(al6.get(i).toString());
            }
        }

        String str7 = al7.get(0).toString();
        for(int i=0;i<al7.size();i++) {
            if (i>0) {
                str7 = str7.concat(al7.get(i).toString());
            }
        }

        String str8 = al8.get(0).toString();
        for(int i=0;i<al8.size();i++) {
            if (i>0) {
                str8 = str8.concat(al8.get(i).toString());
            }
        }

        System.out.println(countChars(str1));
        System.out.println(countChars(str2));
        System.out.println(countChars(str3));
        System.out.println(countChars(str4));
        System.out.println(countChars(str5));
        System.out.println(countChars(str6));
        System.out.println(countChars(str7));
        System.out.println(countChars(str8));

    }

    public static Character countChars(String str) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : str.toCharArray()) {
            if (map.containsKey(ch)) {
                int val = map.get(ch);
                map.put(ch, val + 1);
            } else {
                map.put(ch, 1);
            }
        }
        // Display elements
        // Get a set of the entries
        Set set = map.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            /*
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
            */
        }

        Map.Entry<Character,Integer> minEntry = null;

        for(Map.Entry<Character,Integer> entry : map.entrySet()) {
            if (minEntry == null || entry.getValue() < minEntry.getValue()) {
                minEntry = entry;
            }
        }
// minEntry should now contain the minimum,

        return minEntry.getKey(); // maxEntry.getValue();
    }

    public static String[] readLines(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            //  FileReader fileReader = new FileReader(filename);
            //   BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
            return lines.toArray(new String[lines.size()]);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
