package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph{
    private HashMap<Character, Node> vertices;

    public Graph(){
        vertices = new HashMap<Character, Node>();
    }

    public HashMap<Character, Node> getVertices(){
        return vertices;
    }

    public Node getVertex(Character c){
        createVertexIfNeeded(c);
        return vertices.get(c);
    }

    private void addNode(Character name){
        Node node = new Node(name);
        vertices.put(name, node);
    }

    public void addEdge(Character from, Character to, int weight){
        createVertexIfNeeded(from);
        createVertexIfNeeded(to);
        Node f = getVertex(from);
        f.addConnectionTo(to, weight);
    }

    private void createVertexIfNeeded(Character c){
        if(!vertices.containsKey(c)){
            addNode(c);
        }
    }

    public int calcPathDistance(String pathString){
        if (pathString.length() < 2) {
            return 0;
        }

        char[] path = pathString.toCharArray();
        int distance = 0;

        for(int i = 0; i < pathString.length()-1; i++){
            distance = tryAddSegmentDistance(distance, path[i], path[i+1]);
            if(distance == Integer.MAX_VALUE){
                break;
            }
        }

        return distance;
    }

    private int tryAddSegmentDistance(int distance, Character src, Character dst){
        Node source = vertices.get(src);
        int segDist = source.getWeightTo(dst);
        if(segDist == Integer.MAX_VALUE){
            return segDist;
        }
        return distance + segDist;
    }

    public boolean invalidPath(char[] nodes){
        for (char node : nodes){
            if(!vertices.containsKey(node)){
                return true;
            }
        }
        return false;
    }




}
