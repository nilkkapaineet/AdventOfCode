package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kalenteri22a {

    public static void main(String[] args) {
        // write your code here
        List<String> file = new ArrayList<String>();
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("cal22.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                file.add(line);
                counter++;
            }
            br.close();
            file.toArray(new String[file.size()]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node nodes[] = new Node[counter-2]; // there are two non-node lines
        int index = 0;
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        int passTwo = 0;
        for (String input : file) {
            passTwo++;
            // 0:x, 1:y, 2:size, 3:used, 4:avail, 5:use%
            int[] numbers = getNumbers(input);
          //  System.out.println("numbers: " + numbers[1] + ", " + numbers[2] + ", " +  numbers[3] + ", " +  numbers[4] + ", " +  numbers[5]);
            if (passTwo > 2) { // pass non-node lines
                nodes[index] = new Node(numbers[0], numbers[1], numbers[2], numbers[3], numbers[4], numbers[5], index);
                if (numbers[0] > maxX) { maxX = numbers[0]; }
                if (numbers[1] > maxY) { maxY = numbers[1]; }
                if (numbers[0] < minX) { minX = numbers[0]; }
                if (numbers[1] < minY) { minY = numbers[1]; }
         //       System.out.println("joo: " + index);
                index++;
            }
        }
        int pairs = 0;
        for(int i=0;i<nodes.length;i++) {
   //         System.out.println(i);
            int cid = nodes[i].getId();
            int cSize = nodes[i].getSize();
            int cAvail = nodes[i].getAvail();
            int cUsed = nodes[i].getUsed();
            int cx = nodes[i].getPosx();
            int cy = nodes[i].getPosy();
            Node cn = null;
            Node ce = null;
            Node cs = null;
            Node cw = null;
            for(int j=0;j<nodes.length;j++) {
                int nid = nodes[j].getId();
                int nx = nodes[j].getPosx();
                int ny = nodes[j].getPosy();
                int nSize = 0;
                int nAvail = 0;
                nSize = nodes[j].getSize();
                nAvail = nodes[j].getAvail();
                /*
                if (nx == cx && ny == cy+1) {
                    cn = nodes[j];
                    nSize = nodes[j].getSize();
                    nAvail = nodes[j].getAvail();
                }
                if (nx == cx+1 && ny == cy) {
                    ce = nodes[j];
                    nSize = nodes[j].getSize();
                    nAvail = nodes[j].getAvail();
                }
                if (nx == cx && ny == cy-1) {
                    cs = nodes[j];
                    nSize = nodes[j].getSize();
                    nAvail = nodes[j].getAvail();
                }
                if (nx == cx-1 && ny == cy) {
                    cw = nodes[j];
                    nSize = nodes[j].getSize();
                    nAvail = nodes[j].getAvail();
                }
                */
                //System.out.println("cId: " + cid + " cUsed: " + cUsed + " nId: " + nid + " nAvail: " + nAvail);
                if((cUsed > 0) && (cid != nid) && (nAvail > cUsed)) {
                    pairs++;
                }
            }
            nodes[i].assignNeighbours(cn, ce, cs, cw);
        }
        System.out.println("Number of viable pairs is " + pairs);

    }

    public static int[] getNumbers(String input) {
        // input type: /dev/grid/node-x0-y10    88T   71T    17T   80%
        // Filesystem              Size  Used  Avail  Use%
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(input);
        int[] rectangle = new int[6];
        int i = 0;
        while (m.find()) {
            rectangle[i] = Integer.parseInt(m.group());
            i++;
        }
        return rectangle;
    }
}

class Node {
    private int posx;
    private int posy;
    private int size;
    private int used;
    private int avail;
    private int pros;
    private int id;
    Node north;
    Node east;
    Node south;
    Node west;
    public Node(int x, int y, int s, int u, int a, int p, int i) {
        posx = x;
        posy = y;
        size = s;
        used = u;
        avail = a;
        pros = p;
        id = i;
    }
    public void assignNeighbours(Node n, Node e, Node s, Node w) {
        north = n;
        east = e;
        south = s;
        west = w;
    }
    public int getId() { return id; }
    public int getPosx() { return posx; }
    public int getPosy() { return posy; }
    public void newSize(int s) {
        size = s;
        int a = getAvail();
        int u = getUsed();
        int p = getPros();
        this.newAvail(Math.abs(s-u));
        this.newUsed(Math.abs(s-a));
        this.newPros((getUsed()/s)*100);
    }
    public int getSize() { return size; }
    public void newAvail(int a) {

    }
    public int getAvail() { return avail; }
    public int getPros() { return pros; }
    public void newPros(int p) {

    }
    public int getUsed() { return used; }
    public void newUsed(int u) {

    }
}
