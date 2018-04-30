The cheaters program works in three stages:
    *1 it parses input
    *3 it compares every pairing of files
    *3 it prints and displays the similarities between these pairings
    
In parsing the input we count the number of times the same N length phrase appears in the file. When we compare the files against each other, we choose the smaller of the appearances (if file 1 has "i like to eat bananas" 6 times and file 2 has "i like to eat bananas" 30 times, we count it as 6 similarities).

The output is a javaFX linegraph showing the number of similarities in a given file pair