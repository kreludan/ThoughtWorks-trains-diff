import graph.Graph;
import pathfinders.DistancePathfinder;
import pathfinders.Pathfinder;
import pathfinders.ShortestPath;
import pathfinders.StopsPathfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Trains {

    public static Graph buildGraphFromFile(String filepath) {

        String input = parseFile(filepath);
        return constructGraph(input);
    }

    public static String parseFile(String filepath) {
        try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            return sc.nextLine();
        }
        catch (FileNotFoundException f) {
            System.out.println("FILE NOT FOUND.");
            return "";
        }
        catch (NoSuchElementException n) {
            System.out.println("FILE EMPTY.");
            return "";
        }

    }

    public static Graph constructGraph(String input){
        Graph graph = new Graph();

        if(input.isEmpty()){
            return graph;
        }

        String[] edges = input.split(", ");

        for (String edge : edges) {
            char[] e = edge.toCharArray();
            String numString = edge.substring(2);
            int weight = Integer.parseInt(numString);
            graph.addEdge(e[0], e[1], weight);
        }

        return graph;
    }


    public static String getPathDistance(Graph g, String pathString){
        String formattedString = pathString.replaceAll("[\\s\\-]", "");
        char[] nodes = formattedString.toCharArray();
        if (g.invalidPath(nodes)) {
            return "INVALID PATH";
        }
        int distance = g.calcPathDistance(formattedString);
        if(distance == Integer.MAX_VALUE){
            return "NO SUCH ROUTE";
        }
        return Integer.toString(distance);
    }

    public static String pathsFind(Pathfinder p, Character source, Character dest, int amount, Pathfinder.FindType type){
        if (p.invalidInput(source, dest)) {
            return "INVALID INPUT";
        }

        LinkedList<String> pathsFound = p.findPaths(source, dest, amount, type);
        return Integer.toString(pathsFound.size());
    }

    public static String pathsFind(Pathfinder p, String source, String dest, int amount, Pathfinder.FindType type){
        if(source.length() > 1 || dest.length() > 1){
            return "INVALID INPUT";
        }

        char charSource = source.charAt(0);
        char charDest = dest.charAt(0);

        return pathsFind(p, charSource, charDest, amount, type);
    }

    public static String shortestPath(ShortestPath p, Character source, Character dest){
        if (p.invalidInput(source, dest)){
            return "INVALID INPUT";
        }

        int shortest = p.findShortestPath(source, dest);
        if (shortest == Integer.MAX_VALUE) {
            return "NO PATH FOUND";
        }
        return Integer.toString(shortest);
    }

    public static String shortestPath(ShortestPath p, String source, String dest){
        if(source.length() > 1 || dest.length() > 1){
            return "INVALID INPUT";
        }

        char charSource = source.charAt(0);
        char charDest = dest.charAt(0);

        return shortestPath(p, charSource, charDest);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(new File(".").getAbsoluteFile());
        Graph g = buildGraphFromFile(args[0]);
        System.out.println("TEST RESULTS");
        System.out.println("1. " + getPathDistance(g, "A-B-C"));
        System.out.println("2. " + getPathDistance(g, "A-D"));
        System.out.println("3. " + getPathDistance(g, "A-D-C"));
        System.out.println("4. " + getPathDistance(g, "A-E-B-C-D"));
        System.out.println("5. " + getPathDistance(g, "A-E-D"));

        StopsPathfinder stops = new StopsPathfinder(g);
        System.out.println("6. " + pathsFind(stops, 'C', 'C', 3, Pathfinder.FindType.LEQ));
        System.out.println("7. " + pathsFind(stops, 'A', 'C', 4, Pathfinder.FindType.EQUALS));

        ShortestPath shortest = new ShortestPath(g);
        System.out.println("8. " + shortestPath(shortest, 'A', 'C'));
        System.out.println("9. " + shortestPath(shortest, 'B', 'B'));

        DistancePathfinder distance = new DistancePathfinder(g);
        System.out.println("10. " + pathsFind(distance, 'C', 'C', 30, Pathfinder.FindType.LESS));

    }


}
