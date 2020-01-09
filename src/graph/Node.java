package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

public class Node{
    private Character name;
    private HashMap<Character, Integer> connectionsTo;
    private HashMap<Character, Integer> connectionsFrom;

    public Node(Character n){
        this.name = n;
        connectionsTo = new HashMap<Character, Integer>();
        connectionsFrom = new HashMap<Character, Integer>();
    }

    public void addConnectionTo(Character dest, int weight){
        connectionsTo.put(dest, weight);
    }

    public void addConnectionFrom(Character source, int weight){
        connectionsFrom.put(source, weight);
    }

    public Set<Character> getKeysTo(){
        return connectionsTo.keySet();
    }

    public Set<Character> getKeysFrom(){
        return connectionsFrom.keySet();
    }

    public int getWeightTo(Character c){
        if(!connectionsTo.containsKey(c)){
            return Integer.MAX_VALUE;
        }
        return connectionsTo.get(c);
    }

    public int getWeightFrom(Character c){
        if(!connectionsFrom.containsKey(c)){
            return Integer.MAX_VALUE;
        }
        return connectionsFrom.get(c);
    }


}
