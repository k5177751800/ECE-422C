package assignment4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class GraphPoet {


    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    private Vertex graph;

    public GraphPoet(File corpus) throws IOException {


        /* Read in the File and place into graph here */
        Scanner scanner = new Scanner(corpus); //scanner implemented
        Vertex store = null;
        Vertex firstStore = null;
        Vertex circle = null;
        while(scanner.hasNext()){
            //when there is input
            String scanned = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", ""); //first input

            if(firstStore == null){
                //Implement
                graph = new Vertex(scanned);
                firstStore = graph;
                store = firstStore;
            }
            else {
                if(firstStore.isExists(scanned)){
                    circle = firstStore.isExists(scanned, new HashSet<>());
                    store.addEdges(circle);
                } else{
                    circle = new Vertex(scanned);
                    store.addEdges(circle);
                }
                store = circle;
            }
        }
        scanner.close();
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {

        /* Read in input and use graph to complete poem */
        String poem = "";
        String prev = null;
        Scanner scanner = new Scanner(input);
        if(scanner.hasNext()){
            prev = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", "");
        }
        // Split the input into words

        while(scanner.hasNext()){
            String curr = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", "");
            if (poem.equals("")) {
                if(graph != null && graph.isExists(prev)){
                    Vertex v = graph.find(prev);
                    String bridge = v.poemAdder(curr);
                    poem = poem + prev + " " + bridge;
                }else{
                    poem = poem + prev;
                }
            } else if (graph != null && graph.isExists(prev)){
                Vertex v = graph.find(prev);
                String bridge = v.poemAdder(curr);
                if(bridge.length() > 0){
                    poem = poem + " " + prev + " " + bridge;
                } else{
                    poem = poem + " " + prev;
                }
            } else{
                poem = poem + " " + prev;
            }
            prev = curr;
        }
        poem = poem + " " + prev;
        return poem;
    }
}
