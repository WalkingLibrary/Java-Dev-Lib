package com.jumbodinosaurs.devlib.pathfinding;

import com.jumbodinosaurs.devlib.util.objects.Point;

public abstract class Map<E extends Node>
{
    private E startNode, goalNode;
    
    public Map(E startNode, E goalNode)
    {
        this.startNode = startNode;
        this.goalNode = goalNode;
    }
    
    public Point getStartPoint()
    {
        return startNode.getPoint();
    }
    
    public Point getGoalPoint()
    {
        return goalNode.getPoint();
    }
    
    
    public E getStartNode()
    {
        return startNode;
    }
    
    public E getGoalNode()
    {
        return goalNode;
    }
}
