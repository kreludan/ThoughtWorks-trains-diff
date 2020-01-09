import graph.Graph;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import pathfinders.DistancePathfinder;
import pathfinders.Pathfinder;
import pathfinders.ShortestPath;
import pathfinders.StopsPathfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


    public static class UnitTests {

        Trains tester = new Trains();

        // ASSUMPTIONS: each character is a unique identifier for a node (case sensitive)
        // Characters are the only data type for cities
        // Edges will always be positive integers
        // Only one edge can go from 1 node to another (can't have e.g. AB5, AB7 in the same graph)

        @Test
        public void emptyGraphTests() {
            Graph g = tester.constructGraph("");

            // PATH DISTANCE -
            // Empty string path should return 0
            assertEquals("0", tester.getPathDistance(g, ""));

            // A path with any nodes should return invalid
            assertEquals("INVALID PATH", tester.getPathDistance(g, "A"));
            assertEquals("INVALID PATH", tester.getPathDistance(g, "A-B-C-D"));

            // Gibberish input should return invalid (so long as it contains characters that aren't keys we want
            assertEquals("INVALID PATH", tester.getPathDistance(g, "egs;ldkv;'fdv"));

            // PATH FINDING -
            // Any parameters should return invalid regardless of source/dest, amount or type of PathFinder
            StopsPathfinder stopEmpty = new StopsPathfinder(g);
            assertEquals("INVALID INPUT", tester.pathsFind(stopEmpty, 'G', 'Q', 0, Pathfinder.FindType.EQUALS));
            assertEquals("INVALID INPUT", tester.pathsFind(stopEmpty, ';', 'w', 0, Pathfinder.FindType.LESS));
            assertEquals("INVALID INPUT", tester.pathsFind(stopEmpty, '!', 'n', 0, Pathfinder.FindType.LEQ));
            assertEquals("INVALID INPUT", tester.pathsFind(stopEmpty, 'y', 'D', 90, Pathfinder.FindType.LESS));

            DistancePathfinder distanceEmpty = new DistancePathfinder(g);
            assertEquals("INVALID INPUT", tester.pathsFind(distanceEmpty, 'G', 'Q', 0, Pathfinder.FindType.EQUALS));
            assertEquals("INVALID INPUT", tester.pathsFind(distanceEmpty, ';', 'w', 0, Pathfinder.FindType.LESS));
            assertEquals("INVALID INPUT", tester.pathsFind(distanceEmpty, '!', 'n', 0, Pathfinder.FindType.LEQ));
            assertEquals("INVALID INPUT", tester.pathsFind(distanceEmpty, 'y', 'D', 90, Pathfinder.FindType.LESS));

            // SHORTEST PATH -
            // Same situation. Should be invalid regardless of inputs.
            ShortestPath shortestEmpty = new ShortestPath(g);
            assertEquals("INVALID INPUT", tester.shortestPath(shortestEmpty, 'G', 'Q'));
            assertEquals("INVALID INPUT", tester.shortestPath(shortestEmpty, ';', 'w'));
            assertEquals("INVALID INPUT", tester.shortestPath(shortestEmpty, '!', 'n'));
            assertEquals("INVALID INPUT", tester.shortestPath(shortestEmpty, 'y', 'D'));

        }

        @Test
        public void DistanceTests() {
            // Testing the getPathDistance function
            Graph simpleTest = tester.constructGraph("AB5, BC7, OR3, SQ4, CS9, CA8, AC1");

            // Basic questions
            assertEquals("21", tester.getPathDistance(simpleTest, "ABCS"));
            assertEquals("13", tester.getPathDistance(simpleTest, "CSQ"));
            assertEquals("29", tester.getPathDistance(simpleTest, "BCACSQ"));

            // Path of length 1 should return 0
            assertEquals("0", tester.getPathDistance(simpleTest, "A"));

            // Keys not in the graph should still give invalid input
            assertEquals("INVALID PATH", tester.getPathDistance(simpleTest, "ABZ"));

            // Impossible path should give no path found
            assertEquals("NO SUCH ROUTE", tester.getPathDistance(simpleTest, "AOR"));

            // Even when the path is mostly valid
            assertEquals("NO SUCH ROUTE", tester.getPathDistance(simpleTest, "ABCABCABCSQOR"));

            // Should be able to deal with cycles and repeated edges
            assertEquals("32", tester.getPathDistance(simpleTest, "ABCABC"));

            // Hyphens are OK
            assertEquals("32", tester.getPathDistance(simpleTest, "A-B-C-A-B-C"));

            // So are spaces
            assertEquals("32", tester.getPathDistance(simpleTest, "A B C A B C"));

            // Can't repeat nodes (assuming no self-edges)
            assertEquals("NO SUCH ROUTE", tester.getPathDistance(simpleTest, "AA"));

            // What about stuff that like. happens at the Graph building level... should make sure it's case sensitive, non-alphabet stuff is OK
            Graph caseSensitivity = tester.constructGraph("Aa4, aB5");
            assertEquals("9", tester.getPathDistance(caseSensitivity, "AaB"));

            // SHOULD non-alphabet identifiers this be allowed? I'm gonna say yes for now but in practice I dunno
            Graph nonAlphabetCharacters = tester.constructGraph("q38, x.4, 3x9");
            assertEquals("17", tester.getPathDistance(nonAlphabetCharacters, "q3x"));

        }

        @Test
        public void PathfinderTests() {
            Graph pathfinderTests = tester.constructGraph("AB5, BC3, CA7, AD5, CD9");

            //Stops first
            StopsPathfinder stops = new StopsPathfinder(pathfinderTests);

            // Making sure the invalid input thing works w/o an empty graph
            assertEquals("INVALID INPUT", tester.pathsFind(stops, 'Q',
                    'M', '8', Pathfinder.FindType.EQUALS));

            // Ensuring cycles work, also that the 3 different 'types' (<, <=, =) are ok
            assertEquals("1", tester.pathsFind(stops, 'A', 'A', 9, Pathfinder.FindType.EQUALS));
            assertEquals("2", tester.pathsFind(stops, 'A', 'A', 9, Pathfinder.FindType.LESS));
            assertEquals("3", tester.pathsFind(stops, 'A', 'A', 9, Pathfinder.FindType.LEQ));

            // Another test for the different types
            assertEquals("1", tester.pathsFind(stops, 'A', 'D', 3, Pathfinder.FindType.LESS));
            assertEquals("2", tester.pathsFind(stops, 'A', 'D', 3, Pathfinder.FindType.LEQ));

            // Checking that the edges aren't reversible (no D-A but yes A-D), also that it can return 0
            assertEquals("0", tester.pathsFind(stops, 'D', 'A', 1, Pathfinder.FindType.EQUALS));
            assertEquals("1", tester.pathsFind(stops, 'A', 'D', 1, Pathfinder.FindType.EQUALS));

            // I kept typing the source/dest as strings... should probably be able to convert that then
            assertEquals("2", tester.pathsFind(stops, "A", "A", 9, Pathfinder.FindType.LESS));

            // But also should throw an error if it's a longer string
            assertEquals("INVALID INPUT", tester.pathsFind(stops, "AB", "BA", 9, Pathfinder.FindType.LESS));

            // Distance next
            // Only really need to test whether the distance calculation is working, the above covers the rest OK
            DistancePathfinder distance = new DistancePathfinder(pathfinderTests);

            // Cycles + type checking
            assertEquals("1", tester.pathsFind(distance, 'A', 'A', 45, Pathfinder.FindType.EQUALS));
            assertEquals("2", tester.pathsFind(distance, 'A', 'A', 45, Pathfinder.FindType.LESS));
            assertEquals("3", tester.pathsFind(distance, 'A', 'A', 45, Pathfinder.FindType.LEQ));

            // No Reversibility
            assertEquals("0", tester.pathsFind(distance, 'D', 'A', 5, Pathfinder.FindType.EQUALS));
            assertEquals("1", tester.pathsFind(distance, 'A', 'D', 5, Pathfinder.FindType.EQUALS));

        }

        @Test
        public void ShortestPathTests(){
            Graph shortestPathTests = tester.constructGraph("AB4, BC2, AC7, CD9, AD2, JE4, JA7, AG9, GA2, CA1, DB9");

            ShortestPath shortest = new ShortestPath(shortestPathTests);

            // Making sure the invalid input thing works w/o an empty graph
            assertEquals("INVALID INPUT", tester.shortestPath(shortest, 'Q', 'M'));

            // If there's no path, it says so
            assertEquals("NO PATH FOUND", tester.shortestPath(shortest, 'A', 'J'));

            // Doesn't mess up when there are multiple paths
            assertEquals("6", tester.shortestPath(shortest, 'A', 'C'));
            assertEquals("2", tester.shortestPath(shortest, 'A', 'D'));

            // Again no reversibility; it's 2 from A->D but 12 from D->A
            assertEquals("12", tester.shortestPath(shortest, 'D', 'A'));

            // String testing
            assertEquals("6", tester.shortestPath(shortest, "A", "C"));
            assertEquals("INVALID INPUT", tester.shortestPath(shortest, "AB", "BA"));

            // Cycles shouldn't return 0, they should actually check
            assertEquals("7", tester.shortestPath(shortest, "A", "A"));
            assertEquals("11", tester.shortestPath(shortest, "G", "G"));
            assertEquals("7", tester.shortestPath(shortest, "B", "B"));

            // Last thing; testing with multi-digit weights on the edges
            Graph multiWeights = tester.constructGraph("AB12, BC13, CD25");
            ShortestPath bigNumbers = new ShortestPath(multiWeights);
            assertEquals("50", tester.shortestPath(bigNumbers, "A", "D"));
        }

        public static void main(String[] args) {
            Result result = JUnitCore.runClasses(UnitTests.class);
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}
