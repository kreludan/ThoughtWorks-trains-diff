package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

public class Node{
    private Character name;
    private HashMap<Character, Integer> connectionsTo;

    public Node(Character n){
        this.name = n;
        connectionsTo = new HashMap<Character, Integer>();
    }

    public void addConnectionTo(Character dest, int weight){
        connectionsTo.put(dest, weight);
    }

    public Set<Character> getKeysTo(){
        return connectionsTo.keySet();
    }

    public int getWeightTo(Character c){
        if(!connectionsTo.containsKey(c)){
            return Integer.MAX_VALUE;
        }
        return connectionsTo.get(c);
    }

}
