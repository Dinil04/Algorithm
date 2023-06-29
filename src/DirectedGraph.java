// Dinil Perera - 20200516
public class DirectedGraph implements Cloneable{
    private int number_of_vertices;
    private boolean[][] adjMatrix;

    public DirectedGraph(int numVertices) {
        this.number_of_vertices = numVertices;
        adjMatrix = new boolean[numVertices][numVertices];
    }

    public int get_Number_of_vertices() {
        return number_of_vertices;
    }

    public void add_Edge(int u, int v) {
        adjMatrix[u][v] = true;
    }

    public int get_Out_Degree(int u) {
        int count = 0;
        for (int v = 0; v < number_of_vertices; v++) {
            // Check whether an edge exists between vertex u and vertex v
            if (adjMatrix[u][v]) {
                count++;
            }
        }
        // Return the vertex u's out-degree
        return count;
    }

    public int[] get_Neighbors(int u) {
        int[] neighbors = new int[get_Out_Degree(u)];
        int index = 0;
        for (int v = 0; v < number_of_vertices; v++) {
            // Check whether an edge exists between vertex u and vertex v
            if (adjMatrix[u][v]) {
                neighbors[index] = v;
                index++;
            }
        }
        return neighbors;
    }

    public boolean[][] get_Adjancy_Matrix() {
        return adjMatrix;
    }

    @Override
    public DirectedGraph clone() {
        try {
            return (DirectedGraph) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}