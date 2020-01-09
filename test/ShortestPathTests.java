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


public class ShortestPathTests {

    Trains tester = new Trains();

    Graph empty = tester.constructGraph("");
    ShortestPath e = new ShortestPath(empty);

    Graph simpleGraph = tester.constructGraph("AB4, BC2, AC7, CD9, AD2, JE4, JA7, AG9, GA2, CA1, DB9");
    ShortestPath shortest = new ShortestPath(simpleGraph);

    Graph multiDigitWeights = tester.constructGraph("AB12, BC13, CD25");
    ShortestPath m = new ShortestPath(multiDigitWeights);

    @Test
    public void emptyGraphTests() {
        // Should be invalid regardless of inputs.
        assertEquals("INVALID INPUT", tester.shortestPath(e, 'G', 'Q'));
        assertEquals("INVALID INPUT", tester.shortestPath(e, ';', 'w'));
        assertEquals("INVALID INPUT", tester.shortestPath(e, '!', 'n'));
        assertEquals("INVALID INPUT", tester.shortestPath(e, 'y', 'D'));
    }

    @Test
    public void errorTests() {
        // Making sure the invalid input thing works w/o an empty graph
        assertEquals("INVALID INPUT", tester.shortestPath(shortest, 'Q', 'M'));

        // Making sure the invalid input thing works w/o an empty graph
        assertEquals("INVALID INPUT", tester.shortestPath(shortest, 'Q', 'M'));

        // Multi-character string testing
        assertEquals("INVALID INPUT", tester.shortestPath(shortest, "AB", "BA"));

        // If there's no path, it says so
        assertEquals("NO PATH FOUND", tester.shortestPath(shortest, 'A', 'J'));
    }

    @Test
    public void multiplePathsTests() {
        // Doesn't mess up when there are multiple paths
        assertEquals("6", tester.shortestPath(shortest, 'A', 'C'));
        assertEquals("2", tester.shortestPath(shortest, 'A', 'D'));
    }

    @Test
    public void reversibilityTests() {
        // Again no reversibility; it's 2 from A->D but 12 from D->A
        assertEquals("2", tester.shortestPath(shortest, 'A', 'D'));
        assertEquals("12", tester.shortestPath(shortest, 'D', 'A'));
    }

    @Test
    public void cyclesTests() {
        // Cycles shouldn't return 0, they should actually check
        assertEquals("7", tester.shortestPath(shortest, "A", "A"));
        assertEquals("11", tester.shortestPath(shortest, "G", "G"));
        assertEquals("7", tester.shortestPath(shortest, "B", "B"));
    }

    @Test
    public void stringTests(){
        // Function accepts strings as well as characters
        assertEquals("6", tester.shortestPath(shortest, "A", "C"));
    }

    @Test
    public void multiDigitWeightsTests() {
        assertEquals("50", tester.shortestPath(m, "A", "D"));
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ShortestPathTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
