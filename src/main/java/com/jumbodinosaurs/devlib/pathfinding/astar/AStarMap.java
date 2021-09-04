package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.pathfinding.Map;
import com.jumbodinosaurs.devlib.util.objects.Point;

public abstract class AStarMap extends Map
{
    public AStarMap(AStarNode startPoint, AStarNode goalPoint)
    {
        super(startPoint, goalPoint);
    }
    
    
    
    public double getFCost(AStarNode node)
    {
        return getGCost(node) + getHCost(node.getPoint());
    }
    
    public double getGCost(AStarNode point)
    {
        return point.getGCost();
    }
    
    public abstract double getNewGCost(AStarNode parentNode, AStarNode childNode);
    
    public boolean satisfiesEnd(AStarNode node)
    {
        return node.getPoint().equals(getGoalPoint());
    }
    
    
    //This Allows for context to be added to nodes and be map specific
    public boolean isSameNode(AStarNode openNode, AStarNode neighborNode)
    {
        return openNode.getPoint().equals(neighborNode);
    }
    
    public double getHCost(Point point)
    {
        return point.getEuclideanDistance(getGoalPoint());
    }
    
    @Override
    public AStarNode getStartNode()
    {
        return (AStarNode)super.getStartNode();
    }
    
    @Override
    public AStarNode getGoalNode()
    {
        return (AStarNode) super.getGoalNode();
    }
}
