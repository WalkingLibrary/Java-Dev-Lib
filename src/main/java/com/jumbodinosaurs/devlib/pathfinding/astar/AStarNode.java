package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.pathfinding.Node;
import com.jumbodinosaurs.devlib.util.objects.Point;

public class AStarNode implements Node
{
    private AStarNode parentNode;
    private Point point;
    private double gCost;
    
    public AStarNode(AStarNode parentNode, Point point)
    {
        this.parentNode = parentNode;
        this.point = point;
        
    }
    
    public AStarNode(AStarNode parentNode, Point point, double gCost)
    {
        this.parentNode = parentNode;
        this.point = point;
        this.gCost = gCost;
    }
    
    
    @Override
    public Point getPoint()
    {
        return point;
    }
    
    @Override
    public void setPoint(Point point)
    {
        this.point = point;
    }
    
    public AStarNode getParentNode()
    {
        return parentNode;
    }
    
    public void setParentNode(AStarNode parentNode)
    {
        this.parentNode = parentNode;
    }
    
    public double getGCost()
    {
        return gCost;
    }
    
    public void setGCost(double gCost)
    {
        this.gCost = gCost;
    }
   
    
    @Override
    public String toString()
    {
        return "AStarNode{" + "parentNode=" + parentNode + ", point=" + point + ", gCost=" + gCost + '}';
    }
    
    public boolean equals(AStarNode aStarNode)
    {
        return aStarNode.getPoint().equals(getPoint());
    }
    
   
}
