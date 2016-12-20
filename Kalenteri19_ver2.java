package com.company;

public class Kalenteri19_ver2 {

    public static void main(String[] args) {
	// write your code here
        int maxRound = 3014387;

        // number of presents per position
        int[] pos = new int[maxRound];
        for(int i=0;i<maxRound;i++) {
            // everyone gets one present
            pos[i] = 1;
        }

        System.out.println("Searching...");
        int index = 0;
        while(true) {
            if (index == maxRound) {
                index = 0;
            }
            if (pos[index] == maxRound) {
                System.out.println("Elf no: " + (index + 1) + " got all the presents.");
                System.exit(0);
            }
      //      System.out.println("figuring out elf no " + (index+1) + " with " + pos[index] + " presents.");
            if (pos[index] != 0) {
                for (int nextIndex = (index + 1); nextIndex < (maxRound + index); nextIndex++) {
                    if (nextIndex == maxRound) {
                        nextIndex = 0;
                    }
           //         System.out.println("subround: " + nextIndex + " presents: " + pos[nextIndex]);
                    if (pos[nextIndex] != 0) {
                        pos[index] += pos[nextIndex];
                        pos[nextIndex] = 0;
                        break;
                    }
                    if (nextIndex == index) {
                        System.out.println("Elf no: " + (index + 1) + " got all the presents.");
                        System.exit(0);
                    }
                }
            }
            index++;
        }
    }
}
