package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int counter = 0;
        List<String> file = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("cal20.txt"))) {
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

        Map<Long, Long> map = new HashMap<Long, Long>();
        // low range - high range
        long[][] ranges = new long[counter][2];
        int i = 0;
        for (String input : file) {
            long[] numbers = getNumbers(input);
            ranges[i][0] = numbers[0];
            ranges[i][1] = numbers[1];
            i++;
            map.put(numbers[0], numbers[1]);
        }

        // System.out.println("\nSorted Map......By Value");
        Map<Long, Long> valueMap = sortByValue(map);
        // printMap(valueMap);

        // System.out.println("\nSorted Map......By Key");
        Map<Long, Long> keyMap = new TreeMap<Long, Long>(map);
        // printMap(keyMap);
        System.out.println("Searching...");

        long legalIPs = 0;
        Map.Entry<Long, Long> lowest=keyMap.entrySet().iterator().next();
        long veryLow = lowest.getKey();
        long veryHigh = lowest.getValue();
        for (Map.Entry<Long, Long> entry : keyMap.entrySet()) {
            long low = entry.getKey();
            long high = entry.getValue();
            if ((veryHigh+1.1) <= low) {
                // add to legal IPs
                long temp = low-veryHigh-1;
                legalIPs += temp;
                if (veryHigh <= high) {
                    veryHigh = high;
                }
            } else {
                if (veryHigh <= high) {
                    veryHigh = high;
                }
            }
        }

        System.out.println("Range: Low: " + veryLow + " High: " + veryHigh + " So, found: " + (veryHigh+1));
        System.out.println("Legal IPs: " + legalIPs);

    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }

    public static long[] getNumbers(String input) {
        // input type: digit1-digit2
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(input);
        boolean found = false;
        long[] rectangle = new long[2];
        while (m.find()) {
            if (!found) {
                rectangle[0] = Long.parseLong(m.group());
                found = true;
            } else {
                rectangle[1] = Math.abs(Long.parseLong(m.group()));
            }
        }
        return rectangle;
    }

    private static Map<Long, Long> sortByValue(Map<Long, Long> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Long, Long>> list =
                new LinkedList<Map.Entry<Long, Long>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Long, Long>>() {
            public int compare(Map.Entry<Long, Long> o1,
                               Map.Entry<Long, Long> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Long, Long> sortedMap = new LinkedHashMap<Long, Long>();
        for (Map.Entry<Long, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Long>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Long> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/

        return sortedMap;
    }
}
