// Dinil Perera - 20200516
import java.util.ArrayList;
import java.util.List;

class AcyclicDetector {
    public static boolean graphType(DirectedGraph graph) {
        // Print a message indicating that sinks are being removed from the graph
        System.out.print("The Sink Removed : ");
        // Start a counter for the number of sinks in the graph
        int count_of_sink = 0;
        // Create an ArrayList to contain each vertex's out-degrees
        ArrayList<Integer> out_Degree = new ArrayList<Integer>();
        // Create a copy of the graph so we can make changes without impacting the original
        DirectedGraph copy_Graph = graph.clone();
        //Iterate over through each vertex in the graph
        for (int u = 0; u < copy_Graph.get_Number_of_vertices(); u++) {
            // Add the current vertex's out-degree to the ArrayList
            out_Degree.add(copy_Graph.get_Out_Degree(u));
        }

        while (true){
            // Iterate in the the graph, changing each vertex's out-degree
            for (int u = 0; u < copy_Graph.get_Number_of_vertices(); u++) {
                //If the current vertex has not been deleted, its out-degree should be updated.
                if (!(out_Degree.get(u)==-1)){
                    out_Degree.set(u,copy_Graph.get_Out_Degree(u));
                }
            }
            // Find the sink vertex with the smallest ID
            int sink = find_Sink(out_Degree);

            // Check if the graph is acyclic by looking for any leftover vertices with non-zero out-degree
            boolean isAcyclic = true;
            for (int degree: out_Degree) {
                if (degree != -1) {
                    isAcyclic = false;
                    break;
                }
            }

            if (isAcyclic) {
                // Found a sink with no outgoing edges; the graph is acyclic
                if (count_of_sink==0){
                    System.out.println("Not found !");
                }else {
                    System.out.println();
                }
                return true;
            }

            if (sink!=-1){
                // Remove the sink's edges and update the outDegree array
                for (int v = 0; v < copy_Graph.get_Number_of_vertices() ; v++) {
                    // Check if an edge exists between vertex u and vertex v
                    if (copy_Graph.get_Adjancy_Matrix()[v][sink]) {
                        copy_Graph.get_Adjancy_Matrix()[v][sink]=false;
                    }
                }
                // Remove the sink
                System.out.print(sink+1+ " ");
                count_of_sink++;
                out_Degree.set(sink,-1);
            }else {
                // No sink found, graph is cyclic
                if (count_of_sink==0){
                    System.out.println("Not found !");
                }else {
                    System.out.println();
                }
                return false;
            }
        }
    }

    public static int[] findCycle(DirectedGraph graph) {
        // Get the number of vertices in a graph
        int number_of_vertices = graph.get_Number_of_vertices();
        // Create two arrays to monitor which vertices have been visited and which are now on the stack
        boolean[] visited = new boolean[number_of_vertices];
        boolean[] onStack = new boolean[number_of_vertices];
        // Create an empty list to contain the cycle's vertices
        List<Integer> cycle = new ArrayList<>();

        //Iterate through each vertex in the graph
        for (int u = 0; u < number_of_vertices; u++) {
            // If the current vertex has not been visited and a cycle beginning at this vertex has been found
            if (!visited[u] && findCycleHelper(graph, u, visited, onStack, cycle)) {
                // Create an array to contain the cycle's vertices
                int[] result = new int[cycle.size()];
                // Insert the array's vertices from the list
                for (int i = 0; i < cycle.size(); i++) {
                    result[i] = cycle.get(i);
                }
                // Return the cycle's vertices array
                return result;
            }
        }
        // No cycle found
        return null;
    }

    private static boolean findCycleHelper(DirectedGraph graph, int u, boolean[] visited, boolean[] onStack, List<Integer> cycle) {
        visited[u] = true;
        onStack[u] = true;
        cycle.add(u);

        for (int v : graph.get_Neighbors(u)) {
            if (!visited[v]) {
                if (findCycleHelper(graph, v, visited, onStack, cycle)) {
                    return true;
                }
            } else if (onStack[v]) {
                // Cycle detected
                int idx = cycle.indexOf(v);
                cycle.add(v);
                int[] cycleArr = cycle.subList(idx, cycle.size()).stream().mapToInt(i -> i).toArray();
                cycle.clear();
                for (int i : cycleArr) {
                    cycle.add(i);
                }
                return true;
            }
        }
        cycle.remove(cycle.size() - 1);
        onStack[u] = false;

        return false;
    }


    public static int find_Sink(ArrayList<Integer> outDegree) {
        for (int u = outDegree.size()-1; u>=0; u--) {
            if (outDegree.get(u) == 0) {
                // Found a sink
                return u;
            }
        }
        // No sink found
        return -1;
    }
}