import static org.junit.jupiter.api.Assertions.assertEquals;

import graph.Graph;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import pathfinders.DistancePathfinder;
import pathfinders.Pathfinder;
import pathfinders.ShortestPath;
import pathfinders.StopsPathfinder;


public class PathfinderTests {

    Trains tester = new Trains();

    Graph empty = tester.constructGraph("");
    StopsPathfinder e = new StopsPathfinder(empty);

    Graph simpleGraph = tester.constructGraph("AB5, BC3, CA7, AD5, CD9");
    StopsPathfinder stops = new StopsPathfinder(simpleGraph);
    DistancePathfinder distance = new DistancePathfinder(simpleGraph);


    @Test
    public void emptyGraphTests() {
        // Any parameters should return invalid regardless of source/dest, amount or type of PathFinder
        assertEquals("INVALID INPUT", tester.pathsFind(e, 'G', 'Q', 0, Pathfinder.FindType.EQUALS));
        assertEquals("INVALID INPUT", tester.pathsFind(e, ';', 'w', 0, Pathfinder.FindType.LESS));
        assertEquals("INVALID INPUT", tester.pathsFind(e, '!', 'n', 0, Pathfinder.FindType.LEQ));
        assertEquals("INVALID INPUT", tester.pathsFind(e, 'y', 'D', 90, Pathfinder.FindType.LESS));
    }

    @Test
    public void errorTests() {
        // Making sure the invalid input thing works w/o an empty graph
        assertEquals("INVALID INPUT", tester.pathsFind(stops, 'Q',
                'M', '8', Pathfinder.FindType.EQUALS));

        // But also should throw an error if it's a longer string
        assertEquals("INVALID INPUT", tester.pathsFind(stops, "AB", "BA", 9, Pathfinder.FindType.LESS));
    }

    @Test
    public void stopsTypeTests() {
        // Ensuring cycles work, also that the 3 different 'types' (<, <=, =) are ok
        assertEquals("1", tester.pathsFind(stops, 'A', 'A', 9, Pathfinder.FindType.EQUALS));
        assertEquals("2", tester.pathsFind(stops, 'A', 'A', 9, Pathfinder.FindType.LESS));
        assertEquals("3", tester.pathsFind(stops, 'A', 'A', 9, Pathfinder.FindType.LEQ));

        // Another test for the different types
        assertEquals("1", tester.pathsFind(stops, 'A', 'D', 3, Pathfinder.FindType.LESS));
        assertEquals("2", tester.pathsFind(stops, 'A', 'D', 3, Pathfinder.FindType.LEQ));

    }

    @Test
    public void stopsReversibilityTests() {
        // Checking that the edges aren't reversible (no D-A but yes A-D), also that it can return 0
        assertEquals("0", tester.pathsFind(stops, 'D', 'A', 1, Pathfinder.FindType.EQUALS));
        assertEquals("1", tester.pathsFind(stops, 'A', 'D', 1, Pathfinder.FindType.EQUALS));
    }

    @Test
    public void distanceTypeTests() {
        // Cycles + type checking
        assertEquals("1", tester.pathsFind(distance, 'A', 'A', 45, Pathfinder.FindType.EQUALS));
        assertEquals("2", tester.pathsFind(distance, 'A', 'A', 45, Pathfinder.FindType.LESS));
        assertEquals("3", tester.pathsFind(distance, 'A', 'A', 45, Pathfinder.FindType.LEQ));
    }

    @Test
    public void distanceReversibilityTests() {
        // No Reversibility
        assertEquals("0", tester.pathsFind(distance, 'D', 'A', 5, Pathfinder.FindType.EQUALS));
        assertEquals("1", tester.pathsFind(distance, 'A', 'D', 5, Pathfinder.FindType.EQUALS));
    }

    @Test
    public void stringTests() {
        // Function accepts strings as well as characters
        assertEquals("1", tester.pathsFind(stops, "A", "A", 9, Pathfinder.FindType.EQUALS));
        assertEquals("1", tester.pathsFind(distance, "A", "A", 45, Pathfinder.FindType.EQUALS));
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PathfinderTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
