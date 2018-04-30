package assignment7;

import javafx.application.Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static java.lang.Integer.min;
import static javafx.application.Application.launch;

public class cheaters {

    public static void main(String args[]) {
        Hashtable<String, Hashtable<String, Integer>> filesContents = new Hashtable<>();

        //1) parse input
        try {
            filesContents = parseInput(args[0], Integer.valueOf(args[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Incorrect number of inputs. Please try again using \"java cheaters [path to file] [number of words] [suspicion level (optional)]\"");
        }

        //2) compare files
        System.out.print("debug");
        Hashtable<String, Hashtable<String, Integer>> similarities = new Hashtable<>(); //todo track String = file1,file2 and Integer = number of collisions
        similarities = compareFiles(filesContents);

        //3) print output
        Integer suspicionLevel = 0;
        if (args.length == 3) {
            Integer.parseInt(args[2]);
        }
        printSimilarities(similarities, suspicionLevel);
        Application.launch(LineGraph.class, args);
        System.out.print("debug2"); // todo
    }

    private static void printSimilarities(Hashtable<String, Hashtable<String, Integer>> similarities, Integer suspicionLevel) {
        for (String keys : similarities.keySet()) {
            for (String s : similarities.get(keys).keySet()) {
                if (similarities.get(keys).size() >= suspicionLevel) {
                    LineGraph.fileSimilarities.add(similarities.get(keys));
                    System.out.println(keys + ": " + similarities.get(keys).get(s) + " similarities");
                }
            }
        }
        /*
        for (int i = 0; i < similarities.size(); i++) {
            for (String files : similarities.get(i).keySet()) {
                if (similarities.get(i).get(files) >= suspicionLevel) {
                    LineGraph.fileSimilarities.add(similarities.get(i));
                    System.out.println(files + ": " + similarities.get(i).get(files) + " similarities");
                }
            }
        }
        */
    }

    private static Hashtable<String, Hashtable<String, Integer>> compareFiles(Hashtable<String, Hashtable<String, Integer>> filesContents) {
        int file1 = 0;
        int file2 = 1;
        Hashtable<String, Hashtable<String, Integer>> collisions = new Hashtable<>();
        Hashtable<String, Integer> fileCollisions = new Hashtable<>();

        for(String key1 : filesContents.keySet()){
            Set<String> keySet = filesContents.keySet();
            for(String key2 : filesContents.keySet()){
                String combinedKey = combine(key1, key2);
                if(collisions.get(combinedKey) == null && !key1.equals(key2)) {
                    collisions.put(combinedKey, compare(filesContents.get(key1), filesContents.get(key2), key1, key2));
                }
            }
        }
        /*
        Set<String> keySet = filesContents.keySet();
        for (int k = 0; k < keySet.size() - 1; k++) {
            for (int l = k + 1; l < keySet.size(); l++) {
                collisions.put(key1 + key2, compare(filesContents.get(key1), filesContents.get(key2), key1, key2));
            }
        }
        */
        return collisions;
    }

    private static String combine(String key1, String key2){
        if(key1.compareTo(key2) < 0){ return key1 + key2; }
        else{ return key2 + key1; }
    }

    private static Hashtable<String, Integer> compare(Hashtable<String, Integer> h1, Hashtable<String, Integer> h2, String file1Name, String file2Name) {
        Hashtable<String, Integer> small;
        Hashtable<String, Integer> large;
        Hashtable<String, Integer> collisions = new Hashtable<>();
        if (h1.size() < h2.size()) {
            small = h1;
            large = h2;
        } else {
            large = h1;
            small = h2;
        }

        for (String s : small.keySet()
                ) {
            if (large.containsKey(s)) {

                int old = 0;
                if (collisions.get(file1Name + file2Name) != null) {
                    old = collisions.get(file1Name + file2Name);
                }
                collisions.put(file1Name + file2Name, old + min(small.get(s), large.get(s)));
            }
        }
        return collisions;
    }

    private static Hashtable<String, Hashtable<String, Integer>> parseInput(String arg, Integer numWords) {
        Hashtable<String, Hashtable<String, Integer>> filesContents = new Hashtable<String, Hashtable<String,Integer>>();

        File folder = new File(arg);
        for (File f : Objects.requireNonNull(folder.listFiles())) {
            Hashtable<String, Integer> phrases = new Hashtable<>();
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(f))); //todo

                LinkedList<String> words = new LinkedList<>();

                while (sc.hasNext()) {
                    boolean add = words.add(String.valueOf(sc.next()).replaceAll("[^a-zA-Z ]", "").toLowerCase());//todo handle hyphens differently?
                }

                for (int i = 0; i < words.size() - numWords; i++) {
                    StringBuilder phrase = new StringBuilder();
                    for (int j = 0; j < numWords; j++) {
                        phrase.append(words.get(j + i)).append(" ");
                    }
                    if (phrases.containsKey(phrase.toString())) {
                        //todo utilize
                        phrases.put(phrase.toString(), (phrases.get(phrase.toString()) + 1));
                    } else {
                        phrases.put(phrase.toString(), 1);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String justName = f.toString().substring(f.toString().lastIndexOf("/")+1,f.toString().indexOf("."));
            filesContents.put(justName + " ", phrases);
        }
        return filesContents;
    }
}