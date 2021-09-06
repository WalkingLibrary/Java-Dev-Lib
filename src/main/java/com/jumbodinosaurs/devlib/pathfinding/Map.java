package com.jumbodinosaurs.devlib.pathfinding;

import java.util.HashMap;

public abstract class Map<E extends Node>
{
    protected E startNode;
    protected E goalNode;
    
    protected HashMap<String, Double> costsMap = new HashMap<String, Double>();
    
    public Map(E startNode, E goalNode)
    {
        this.startNode = startNode;
        this.goalNode = goalNode;
    }
    
    
    public Double g(Node node)
    {
        if(!costsMap.containsKey(node.toString()))
        {
            return Double.MAX_VALUE;
        }
        return costsMap.get(node.toString());
    }
    
    
    public void setG(Node node, Double cost)
    {
        if(!(cost >= Double.MAX_VALUE))
        {
            costsMap.put(node.toString(), cost);
        }
    }
    
    
    public abstract Double h(Node node);
    
    
    public abstract Double distance(Node parent, Node child);
    
    public abstract boolean satisfiesEnd(Node node);
    
    
    public E getStartNode()
    {
        return startNode;
    }
    
    public void setStartNode(E startNode)
    {
        this.startNode = startNode;
    }
    
    public E getGoalNode()
    {
        return goalNode;
    }
    
    public void setGoalNode(E goalNode)
    {
        this.goalNode = goalNode;
    }
}