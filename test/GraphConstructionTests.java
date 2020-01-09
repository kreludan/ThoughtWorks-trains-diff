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


public class GraphConstructionTests {

    Trains tester = new Trains();


    @Test
    public void caseSensitivityTests() { // Graph building should be case sensitive.
        Graph caseSensitivity = tester.constructGraph("Aa4, aB5");
        assertEquals("9", tester.getPathDistance(caseSensitivity, "AaB"));
    }

    @Test
    public void nonAlphabetTests() { // Graph building should accept non-alphabet identifiers.
        Graph nonAlphabetCharacters = tester.constructGraph("q38, x.4, 3x9");
        assertEquals("17", tester.getPathDistance(nonAlphabetCharacters, "q3x"));
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(GraphConstructionTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
