package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static java.lang.Integer.min;

public class cheaters {

    public static void main(String args[]) {
        ArrayList<Hashtable<String, Integer>> filesContents = new ArrayList<>();

        //1) parse input

        try {
            filesContents = parseInput(args[0], Integer.valueOf(args[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Incorrect number of inputs. Please try again using \"java cheaters [path to file] [number of words] [suspicion level (optional)]\"");
        }
        //2) compare files
        System.out.print("debug");
        ArrayList<Hashtable<String, Integer>> similarities = new ArrayList<>(); //todo track String = file1,file2 and Integer = number of collisions
        similarities = compareFiles(filesContents);
        //3) print output
        Integer suspicionLevel = 0;
        if (args.length == 3) {
            Integer.parseInt(args[2]);
        }
        printSimilarities(similarities, suspicionLevel);
        System.out.print("debug2"); // todo
    }

    private static void printSimilarities(ArrayList<Hashtable<String,Integer>> similarities, Integer suspicionLevel) {
        for(int i =0; i < similarities.size(); i++){
        for (String files : similarities.get(i).keySet()) {
            if (similarities.get(i).get(files) >= suspicionLevel) {
                System.out.println(files + ": " + similarities.get(i).get(files) + " similarities");
            }
        }
        }
    }

    private static ArrayList<Hashtable<String, Integer>> compareFiles(ArrayList<Hashtable<String, Integer>> filesContents) {
        int file1 = 0;
        int file2 = 1;
        ArrayList<Hashtable<String, Integer>> collisions = new ArrayList<>();
        Hashtable<String, Integer> fileCollisions = new Hashtable<>();
        for (int k = 0; k < filesContents.size() - 1; k++) {
            for (int l = k + 1; l < filesContents.size(); l++) {

                collisions.add(compare(filesContents.get(k), filesContents.get(l), String.valueOf(k), String.valueOf(l)));
            }
        }
//        for (int i = 0; i < filesContents.size(); i++) {
//            for (int j = i + 1; j < filesContents.size(); j++) {
//                Set<String> first = filesContents.get(i).keySet();
//                Set<String> collisions = new HashSet<>(first); // todo find some way of notating the two files that matched
//                Set<String> second = filesContents.get(j).keySet();
//                collisions.retainAll(second);
//                int collided = collisions.size();
//                if (collided > 0) {
////                    System.out.println("f1 , f2: " + collided + " similarities"); // todo
//                    fileCollisions.put(String.valueOf(file1) + String.valueOf(file2), collided); // todo replace "file1 file 2" with file names
//                    file1++; file2++;
//                }
//            }

        return collisions;
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

    private static ArrayList<Hashtable<String, Integer>> parseInput(String arg, Integer numWords) {
        ArrayList<Hashtable<String, Integer>> filesContents = new ArrayList<>();

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
            filesContents.add(phrases);
        }
        return filesContents;
    }
}