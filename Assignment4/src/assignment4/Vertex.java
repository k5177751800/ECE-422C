package assignment4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.*;
public class Vertex{
        private String name; //name inside of circle
        private Map<Vertex, Integer> edges;
        //edges, children circle
        //vertex= key, circle

        //makes 동그람미 constructor
        public Vertex(String ver){
                name = ver;
                edges = new HashMap<>(); //empty hashmap
        }


        public void addEdges(Vertex aft){
                //edges.getordefualt increments when existsw
                edges.put(aft, edges.getOrDefault(aft, 0)+1);
        }

        public Map<Vertex, Integer> getEdges() {
                return edges;
        }
        public boolean isExists(String input){
                //compare child
                // if exist return true
                // if doesn't exits return false
                return this.isExists(input, new HashSet<>()) !=null;

        }
        public Vertex find(String input) {
                return this.isExists(input, new HashSet<>());
        }
        public Vertex isExists(String input, HashSet<Vertex> visited) {
                if (visited.contains(this)) {
                        return null;
                } else if (this.name.equals(input)) { // Compare with the input, not name
                        return this;
                } else {
                        visited.add(this);
                        for (Vertex ver : this.edges.keySet()) {
                                Vertex found = ver.isExists(input, visited);
                                if (found != null) {
                                        return found;
                                }
                        }
                        return null;
                }
        }
        public String printName(){
                return name;
        }
        //////////////////////////////////////////////////////////////////////////
        public String poemAdder(String name) {
                Map<String, Integer> map = new HashMap<>();
                for(Vertex a : edges.keySet()){
                        a.poemChecker(name, map);
                }
                //sorting priority
                PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));
                queue.addAll(map.keySet());

                if (queue.isEmpty()) return ""; // Return an empty string if the queue is empty.
                return queue.poll();
        }
        private void poemChecker(String name, Map<String, Integer> map) {
                for (Vertex secondNeighbor : this.edges.keySet()) {
                        if(secondNeighbor.printName().equals(name)){
                                String bridgeWord = this.printName();
                                map.put(bridgeWord, map.getOrDefault(bridgeWord, this.edges.get(secondNeighbor)));
                                return;
                        }
                }
                return;
        }


        //make add function


}
