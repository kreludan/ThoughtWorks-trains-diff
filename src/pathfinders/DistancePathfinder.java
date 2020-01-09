package pathfinders;

import graph.Graph;

public class DistancePathfinder extends Pathfinder {

    public DistancePathfinder(Graph g) {
        super(g);
    }


    public boolean meetsConditions(String path, int distance, FindType type) {
        if (type == FindType.EQUALS && pathDistance(path) == distance) {
            return true;
        }

        if (type == FindType.LEQ && pathDistance(path) <= distance) {
            return true;
        }

        if (type == FindType.LESS && pathDistance(path) < distance) {
            return true;
        }

        return false;
    }

    public boolean tooLarge(String path, int distance) {
        return (pathDistance(path) >= distance);
    }
}
