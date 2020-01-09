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


public class DistanceTests {

    Trains tester = new Trains();

    Graph empty = tester.constructGraph("");
    Graph simpleGraph = tester.constructGraph("AB5, BC7, OR3, SQ4, CS9, CA8, AC1");

    @Test
    public void emptyGraphTests() {
        // PATH DISTANCE -
        // Empty string path should return 0
        assertEquals("0", tester.getPathDistance(empty, ""));

        // A path with any nodes should return invalid
        assertEquals("INVALID PATH", tester.getPathDistance(empty, "A"));
        assertEquals("INVALID PATH", tester.getPathDistance(empty, "A-B-C-D"));

        // Gibberish input should return invalid (so long as it contains characters that aren't keys we want
        assertEquals("INVALID PATH", tester.getPathDistance(empty, "egs;ldkv;'fdv"));
    }

    @Test
    public void functionalityTests() {
        // Basic questions
        assertEquals("21", tester.getPathDistance(simpleGraph, "ABCS"));
        assertEquals("13", tester.getPathDistance(simpleGraph, "CSQ"));
        assertEquals("29", tester.getPathDistance(simpleGraph, "BCACSQ"));

        // Path of length 1 should return 0
        assertEquals("0", tester.getPathDistance(simpleGraph, "A"));


        // Should be able to deal with cycles and repeated edges
        assertEquals("32", tester.getPathDistance(simpleGraph, "ABCABC"));

    }

    @Test
    public void errorTests() {
        // Keys not in the graph should still give invalid input
        assertEquals("INVALID PATH", tester.getPathDistance(simpleGraph, "ABZ"));

        // Impossible path should give no path found
        assertEquals("NO SUCH ROUTE", tester.getPathDistance(simpleGraph, "AOR"));

        // Even when the path is mostly valid
        assertEquals("NO SUCH ROUTE", tester.getPathDistance(simpleGraph, "ABCABCABCSQOR"));

        // Can't repeat nodes (assuming no self-edges)
        assertEquals("NO SUCH ROUTE", tester.getPathDistance(simpleGraph, "AA"));
    }

    @Test
    public void delimiterTests() {
        // Hyphens are OK
        assertEquals("32", tester.getPathDistance(simpleGraph, "A-B-C-A-B-C"));

        // So are spaces
        assertEquals("32", tester.getPathDistance(simpleGraph, "A B C A B C"));
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DistanceTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
