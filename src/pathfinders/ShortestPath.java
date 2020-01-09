package pathfinders;

import graph.Graph;
import graph.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShortestPath {
    private Graph graph;
    private HashMap<Character, Node> vertices;
    private HashMap<Character, Integer> distances;

    public ShortestPath(Graph g) {
        this.graph = g;
        vertices = graph.getVertices();
        distances = new HashMap<Character, Integer>();
    }

    public Graph getGraph() {
        return graph;
    }

    public int findShortestPath(Character source, Character dest) {
        clearDistances();
        distances = Dijkstra(source);
        return distances.get(dest);
    }

    private void clearDistances() {
        distances = new HashMap<Character, Integer>();
    }

    private HashMap<Character, Integer> Dijkstra(Character source){
        Set<Character> q = new HashSet<Character>();

        initializeDijkstra(source, q);

        while(!q.isEmpty()) {
            Character u = minDistVertex(q);
            q.remove(u);

            handleConnections(u);
        }

        int cycleDist = smallestCycle(source);
        distances.put(source, cycleDist);
        return distances;
    }

    private int smallestCycle(Character sourceName){     // modified: no longer relies on "from" method calls
        Node sourceNode = vertices.get(sourceName);
        int smallest = Integer.MAX_VALUE;

        for(Map.Entry<Character, Node> entry : vertices.entrySet()) {
            Character neighborName = entry.getKey();
            if (neighborName.equals(sourceName)) {
                continue;
            }

            Node neighborNode = entry.getValue();
            int sourceToNeighbor = distances.get(neighborName);
            int neighborToSource = neighborNode.getWeightTo(sourceName);

            if (sourceToNeighbor == Integer.MAX_VALUE || neighborToSource == Integer.MAX_VALUE) {
                continue;
            }

            int cycleDistance = sourceToNeighbor + neighborToSource;
            if (cycleDistance < smallest) {
                smallest = cycleDistance;
            }
        }

        return smallest;
    }


    private void initializeDijkstra(Character source, Set<Character> q) {
        distances.put(source, 0);
        for (Character vertex : vertices.keySet()) {
            if (!vertex.equals(source)) distances.put(vertex, Integer.MAX_VALUE);
            q.add(vertex);
        }
    }

    private void handleConnections(Character nodeName) {
        Node currNode = vertices.get(nodeName);

        for(Character connection : currNode.getKeysTo()) {
            int alt = distances.get(nodeName) + currNode.getWeightTo(connection);

            if(alt >= 0 && alt < distances.get(connection)) {
                distances.put(connection, alt);
            }
        }
    }

    private char minDistVertex(Set<Character> q) {

        int minDistance = Integer.MAX_VALUE;
        Map.Entry<Character, Integer> minDistEntry = null;


        for(Map.Entry<Character, Integer> entry : distances.entrySet()) {
            char nodeName = entry.getKey();
            int alt = entry.getValue();
            if(q.contains(nodeName) && alt <= minDistance){
                minDistance = alt;
                minDistEntry = entry;
            }
        }

        return minDistEntry.getKey();

    }

    public boolean invalidInput(char source, char dest){
        HashMap<Character, Node> vertices = graph.getVertices();
        if(!(vertices.containsKey(source) && vertices.containsKey(dest))){
            return true;
        }
        return false;
    }
}
