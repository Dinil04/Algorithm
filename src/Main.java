// Dinil Perera - 20200516
//import packages
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String input_files = "dataset";
        // Make a new File object using the supplied directory's path
        File document = new File(input_files);
        // Retrieve a list of all files in the directory
        File[] document_name = document.listFiles();

        // Make an ArrayList to contain the list of.txt files
        ArrayList<File> dataset = new ArrayList<File>();
        // Go through all of the files in the directory.
        for (File file : document_name) {
            // Check if the file is a regular file and has the extension ".txt"
            if (file.isFile() && file.getName().endsWith(".txt")) {
                // Add the file to the dataset ArrayList
                dataset.add(file);
            }
        }

        // Shuffle the list of input files in the dataset ArrayList
        Collections.shuffle(dataset);
        // Loop through each file in the dataset ArrayList
        for (File file: dataset) {
            // Print the name of the file to the console
            System.out.println("The File Name: " + file.getName());
            // Call the vertices() method to get the maximum number of vertices in the file
            String max_vertices = vertices(file);
            // Call the process_graph() method to process the file
            process_graph(file , max_vertices);
        }
    }


    private static void process_graph(File file , String line) {
        try {
            long starting_Time = System.currentTimeMillis();
// The current system time in milliseconds is stored in a variable for later use
// A new GraphParser object is created to parse the input file into a directed graph
            GraphParser graphParser = new GraphParser(file , line);

// A BufferedReader object is constructed to read the input file
            BufferedReader reader = new BufferedReader(new FileReader(file));

            //The number of vertices is sent to the console
            System.out.println("The number of vertices : "+ line);

// The opening brace for the edges of the graph is shown to the console
            System.out.print("The graph's edges are : { ");

// A while loop is used to read each line of the input file
            while ((line = reader.readLine()) != null) {

                // The current line is split into tokens
                String[] tokens = line.split(" ");

                // If there are more lines to read, the current edge, followed by a comma, is shown to the console
                if (reader.ready()) {
                    System.out.print("["+tokens[0]+","+ tokens[1] +"]" + ", ");
                }

                // If there are no more lines to read, the current edge is printed to the console followed by a closing brace
                else {
                    System.out.println("["+tokens[0]+","+ tokens[1] +"]" +" }");
                }
            }

            // Check whether or not the graph is acyclic
            if (AcyclicDetector.graphType(graphParser.getGraph())) {
                // If the graph is acyclic, print a message to the console
                System.out.println("The Graph is acyclic");
            } else {
                // If the graph is cyclic, print a message to the console and find a cycle
                System.out.println("The Graph is cyclic");

                // An array representing the cycle in the graph is found using the AcyclicDetector class
                int[] cycle = AcyclicDetector.findCycle(graphParser.getGraph());

                // The cycle is printed to the console
                System.out.print("Cycle: ");
                for (int i = 0; i < cycle.length; i++) {
                    System.out.print(cycle[i] + 1);
                    if (i < cycle.length - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
            }

// The end time is recorded and the elapsed time is calculated in milliseconds
            long endTime = System.currentTimeMillis();
            long time_Elapsed = endTime - starting_Time;

// The elapsed time is printed to the console
            System.out.println("The Elapsed time: " + time_Elapsed + " milliseconds");

// A blank line is printed to the console for formatting purposes
            System.out.println();


        } catch (IOException e) {
            // If there was an error reading the file, print an error message to the console and exit the program
            System.err.println("The Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }

    public static String vertices(File redar) {

        int manim = 0;
        ArrayList<Integer> test  = new ArrayList<>();
        ArrayList<String> listOfStrings = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(redar);
            while (scanner.hasNext()) {
                int value = Integer.parseInt(scanner.next());
                if(value != 0){
                    if(!test.contains(value)){
                        test.add(value);
                    }
                }
            }
            manim= test.size();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading the file");
        }
        return Integer.toString(manim);


    }
}