/* Cheaters <Main.java>
 * EE422C Project 7 submission by
 * Replace <...> with your actual data.
 * Kassandra Smith
 * KSS2474
 * 15465
 * Brian Wilmarth
 * bw24274
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

package assignment7;

import javafx.application.Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static java.lang.Integer.min;

/**
 * Main class for cheaters
 */
public class cheaters {
    /**
     * @param args Should be input as [path to folder] [number of words] [(level to raise suspicion)]
     */
    public static void main(String args[]) {
        HashMap<String, HashMap<String, Integer>> filesContents = new HashMap<>();

        /*1) parse input*/
        try {
            filesContents = parseInput(args[0], Integer.valueOf(args[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Incorrect number of inputs. Please try again using \"java cheaters [path to folder] [number of words] [suspicion level (optional)]\"");
        }

        /*2) compare files*/
        HashMap<String, HashMap<String, Integer>> similarities;
        similarities = compareFiles(filesContents);

        /*3) print output*/
        Integer suspicionLevel = 0;
        if (args.length == 3) {
            suspicionLevel = Integer.parseInt(args[2]);
        }
        printSimilarities(similarities, suspicionLevel);
        showSimiliarities();

    }

    /**
     * This function launches the JavaFX visual representation of the similarities between files
     */
    private static void showSimiliarities() {
        Application.launch(LineGraph.class);
    }

    /**
     * This function prints out the filenames as well and the number of similar N length strings between them
     *
     * @param similarities   is a HashMap of HashMaps where the String value of the combined filenames is the key to each HashMap of filenames and number of similarities
     * @param suspicionLevel is the number of similarities a file must exceed to be considered "suspicious" and output
     */
    private static void printSimilarities(HashMap<String, HashMap<String, Integer>> similarities, Integer suspicionLevel) {
        for (String keys : similarities.keySet()) {
            for (String s : similarities.get(keys).keySet()) {
                if (similarities.get(keys).get(s) >= suspicionLevel) {
                    LineGraph.fileSimilarities.add(similarities.get(keys));
                    System.out.println(keys + ": " + similarities.get(keys).get(s) + " similarities");
                }
            }
        }
    }

    /**
     * Compares every pairing of files
     *
     * @param filesContents A HashMap with key: the filename and value: a HashMap with key: every N length string and value: the number of times it occurred
     * @return A HashMap with key: the combined filenames and value: a HashMap with the combined filenames and value: the number of collisions between the two
     */
    private static HashMap<String, HashMap<String, Integer>> compareFiles(HashMap<String, HashMap<String, Integer>> filesContents) {
        HashMap<String, HashMap<String, Integer>> collisions = new HashMap<>();
        for (String key1 : filesContents.keySet()) {
            for (String key2 : filesContents.keySet()) {
                String combinedKey = combine(key1, key2);
                if (collisions.get(combinedKey) == null && !key1.equals(key2)) {
                    collisions.put(combinedKey, compare(filesContents.get(key1), filesContents.get(key2), key1, key2));
                }
            }
        }
        return collisions;
    }

    /**
     * Compares two file contents to find collisions. Takes the smaller of the two files and compares its contents against the larger with a HashMap
     *
     * @param h1        the first HashMap containing all N length phrases in file1
     * @param h2        the second HashMap containing all N length phrases in file 2
     * @param file1Name the name of file1
     * @param file2Name the name of file2
     * @return A HashMap with the combined filename as key and the number of times it collides as value
     */
    private static HashMap<String, Integer> compare(HashMap<String, Integer> h1, HashMap<String, Integer> h2, String file1Name, String file2Name) {
        HashMap<String, Integer> small;
        HashMap<String, Integer> large;
        HashMap<String, Integer> collisions = new HashMap<>();
        String combinedKey = combine(file1Name, file2Name);
        if (h1.size() < h2.size()) {
            small = h1;
            large = h2;
        } else {
            large = h1;
            small = h2;
        }
        for (String s : small.keySet()) {
            if (large.containsKey(s)) {
                int old = 0;
                if (collisions.get(combinedKey) != null) {
                    old = collisions.get(combinedKey);
                }
                collisions.put(combinedKey, old + min(small.get(s), large.get(s)));
            }
        }
        return collisions;
    }

    /**
     * Parses the files into N length strings to compare
     *
     * @param arg      the arguments passed to the class
     * @param numWords the number of words in a phrase ("N")
     * @return a HashMap with key: filename and value: a HashMap with key: every N length string and value: the number of times it appears in the file
     */
    private static HashMap<String, HashMap<String, Integer>> parseInput(String arg, Integer numWords) {
        HashMap<String, HashMap<String, Integer>> filesContents = new HashMap<String, HashMap<String, Integer>>();
        File folder = new File(arg);
        for (File f : Objects.requireNonNull(folder.listFiles())) {
            HashMap<String, Integer> phrases = new HashMap<>();
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(f)));
                LinkedList<String> words = new LinkedList<>();
                while (sc.hasNext()) {
                    words.add(String.valueOf(sc.next()).replaceAll("[^a-zA-Z ]", "").toLowerCase());
                }
                for (int i = 0; i < words.size() - numWords; i++) {
                    StringBuilder phrase = new StringBuilder();
                    for (int j = 0; j < numWords; j++) {
                        phrase.append(words.get(j + i)).append(" ");
                    }
                    if (phrases.containsKey(phrase.toString())) {
                        phrases.put(phrase.toString(), (phrases.get(phrase.toString()) + 1));
                    } else {
                        phrases.put(phrase.toString(), 1);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String justName = f.toString().substring(f.toString().lastIndexOf("/") + 1, f.toString().indexOf("."));
            filesContents.put(justName + " ", phrases);
        }
        return filesContents;
    }

    /**
     * Combines two filenames in alphabetical order to prevent duplication
     *
     * @param key1 filename 1
     * @param key2 filename 2
     * @return the combined filenames in alphabetical order
     */
    private static String combine(String key1, String key2) {
        if (key1.compareTo(key2) < 0) {
            return key1 + key2;
        } else {
            return key2 + key1;
        }
    }

}