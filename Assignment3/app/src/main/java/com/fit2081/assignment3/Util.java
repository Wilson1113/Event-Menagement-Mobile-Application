package com.fit2081.assignment3;


import java.util.Random;

public class Util {
    public static String generateCategoryId() {
        Random rand = new Random();

        return "C"
                + (char)(rand.nextInt(26) + 'A')
                + (char)(rand.nextInt(26) + 'A')
                + "-" + String.format("%04d", rand.nextInt(10000));
    }

    public static String generateEventId() {
        Random rand = new Random();

        return "E"
                + (char)(rand.nextInt(26) + 'A')
                + (char)(rand.nextInt(26) + 'A')
                + "-" + String.format("%04d", rand.nextInt(100000));
    }
}
