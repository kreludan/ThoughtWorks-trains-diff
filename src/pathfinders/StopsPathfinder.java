package pathfinders;

import graph.Graph;

import java.util.LinkedList;

/**
 *
 */
public class StopsPathfinder extends Pathfinder {

    public StopsPathfinder(Graph g) {
        super(g);
    }

    public boolean meetsConditions(String path, int stops, FindType type) {
        if (type == FindType.EQUALS && pathStops(path) == stops) {
            return true;
        }

        if (type == FindType.LEQ && pathStops(path) <= stops) {
            return true;
        }

        if (type == FindType.LESS && pathStops(path) < stops) {
            return true;
        }

        return false;
    }

    public boolean tooLarge(String path, int stops) {
        return (pathStops(path) >= stops);
    }



}
