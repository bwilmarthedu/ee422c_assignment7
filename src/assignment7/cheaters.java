package assignment7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class cheaters {
    public static void main(String args[]) {
        System.out.print(args);
        File f = new File(args[0]);
        int numWords = Integer.valueOf(args[1]); //todo - catch
        Hashtable<String, Integer> phrases = new Hashtable<>();
        try {
            Scanner sc = new Scanner(f);
            String[] words = sc.next().trim().replaceAll("[^a-zA-Z ]", " ").replaceAll("\\s+", " ").toLowerCase().split(" ");
            for (int i = 0; i < words.length; i++) {
                StringBuilder phrase = new StringBuilder();
                for (int j = 0; j < numWords; j++) {
                    phrase.append(words[i + j]).append(" ");
                }
                phrases.put(phrase.toString(), 0);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
