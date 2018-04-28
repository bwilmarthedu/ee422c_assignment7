package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class cheaters {

    //    Hashtable<String, Integer> phrases = new Hashtable<>();
    public static void main(String args[]) {
        ArrayList<Hashtable<String, Integer>> filesContents = new ArrayList<>();

        //1) parse input

        try {
            filesContents = parseInput(args[0], Integer.valueOf(args[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("Incorrect number of inputs. Please try again using \"java cheaters [path to file] [number of words] [suspicion level]\"");
        }
        //2) compare files
        System.out.print("debug");
        ArrayList<Integer>  debug  =  compareFiles(filesContents);
        //3) print output

                System.out.print("debug2"); // todo
    }

    private static ArrayList<Integer> compareFiles(ArrayList<Hashtable<String, Integer>> filesContents) {
        ArrayList<Integer> collisions = new ArrayList(); // todo find some way of notating the two files that matched
        for (int i = 0; i < filesContents.size(); i++) {
            Hashtable first = filesContents.get(i);
            for (int j = 0; j < filesContents.size(); j++) {
                Hashtable second = filesContents.get(j);
                int collided = 0;
                if (first != second) { // same entry
                    Set<String> firstKeys = first.keySet();
                    Set<String> secondKeys = second.keySet();
                    for (String s : firstKeys) {
                        for (String st : secondKeys) {
                            if (s.equals(st)) {
                                collided++;
                            }
                        }
                    }
                    collisions.add(collided);
                }
            }
        }return collisions;
    }

    private static ArrayList<Hashtable<String, Integer>> parseInput(String arg, Integer numWords) {
        ArrayList<Hashtable<String, Integer>> filesContents = new ArrayList<>();

        File folder = new File(arg);
        for (File f : folder.listFiles()) {
            Hashtable<String, Integer> phrases = new Hashtable<>();
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(f))); //todo

                LinkedList<String> words = new LinkedList<>();

                while (sc.hasNext()) {
                    words.add(String.valueOf(sc.next()).replaceAll("[^a-zA-Z ]", "").toLowerCase()); //todo handle hyphens differently?
                }

                for (int i = 0; i < words.size() - numWords; i++) {
                    StringBuilder phrase = new StringBuilder();
                    for (int j = 0; j < numWords; j++) {
                        phrase.append(words.get(j + i)).append(" ");
                    }
                    phrases.put(phrase.toString(), 0);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            filesContents.add(phrases);
        }
        return filesContents;
    }
}
