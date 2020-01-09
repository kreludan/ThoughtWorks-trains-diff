package pathfinders;

import graph.Graph;
import graph.Node;

import java.util.HashMap;
import java.util.LinkedList;


public abstract class Pathfinder {

    public enum FindType {LEQ, LESS, EQUALS};

    private Graph graph;
    private LinkedList<String> paths;

    public Pathfinder(Graph g) {
        this.graph = g;
        paths = new LinkedList<String>();
    }

    public LinkedList<String> findPaths(Character source, Character dest, int amount, FindType type){
        clearPaths();
        recurse(source.toString(), source, dest, amount, type);
        return paths;
    }

    public void clearPaths() {
        paths = new LinkedList<String>();
    }


    public void recurse(String path, Character curr, Character dest, int amount, FindType type) {
        if (finished(path, curr, dest) && meetsConditions(path, amount, type)) {
            paths.add(path);
        }

        if (tooLarge(path, amount)) {
            return;
        }

        Node currLast = graph.getVertex(curr);

        for (Character c : currLast.getKeysTo()) {
            String updatedPath = path + c;
            recurse(updatedPath, c, dest, amount, type);
        }

    }

    public Graph getGraph() {
        return graph;
    }

    public int pathDistance(String path) {
        return graph.calcPathDistance(path);
    }

    public int pathStops(String path){
        return path.length()-1;
    }

    public boolean finished(String path, Character curr, Character dest) {
        if (curr.equals(dest) && path.length() > 1) {
            return true;
        }

        return false;
    }

    public boolean invalidInput(char source, char dest){
        HashMap<Character, Node> vertices = graph.getVertices();
        if(!(vertices.containsKey(source) && vertices.containsKey(dest))){
            return true;
        }
        return false;
    }

    public abstract boolean meetsConditions(String path, int amount, FindType type);

    public abstract boolean tooLarge(String path, int amount);

}
