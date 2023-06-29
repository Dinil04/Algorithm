// Dinil Perera - 20200516
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GraphParser {
    DirectedGraph graph;
    public GraphParser(File file , String line) throws IOException {
        // To read the file, create a new buffered reader
        // Create a new BufferedReader object that reads from a file
        BufferedReader doc_reader = new BufferedReader(new FileReader(file));
        // Create a new DirectedGraph object with the number of vertices supplied in the file's first line
        this.graph = new DirectedGraph(Integer.parseInt(line));
        // Read further from the file until there are no more lines to read
        while ((line = doc_reader.readLine()) != null) {
            // Apply the space character as a delimiter to divide the current line into an array of strings
            String[] tokens_line = line.split(" ");
            try {
                int u = Integer.parseInt(tokens_line[0]) - 1;
                int v = Integer.parseInt(tokens_line[1]) - 1;
                graph.add_Edge(u, v);
            } catch (Exception ignored) {
            }
        }
        doc_reader.close();
    }

    public DirectedGraph getGraph() {
        return graph;
    }
}