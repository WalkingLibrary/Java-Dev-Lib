package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.pathfinding.Node;
import com.jumbodinosaurs.devlib.util.objects.Point;

import java.util.ArrayList;
import java.util.Objects;

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
    public ArrayList<AStarNode> getNeighbors(NeighborLambda neighborLambda)
    {
        ArrayList<AStarNode> aStarNodeNeighbors = new ArrayList<AStarNode>();
        for(Point neighborPoint: getPoint().getNeighbors())
        {
            aStarNodeNeighbors.add(neighborLambda.getNeighbor(this, neighborPoint));
        }
        return aStarNodeNeighbors;
    }
    
    @Override
    public String toString()
    {
        return "AStarNode{" + "parentNode=" + parentNode + ", point=" + point + ", gCost=" + gCost + '}';
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        AStarNode aStarNode = (AStarNode) o;
        return Double.compare(aStarNode.gCost, gCost) == 0 &&
               Objects.equals(parentNode, aStarNode.parentNode) &&
               Objects.equals(point, aStarNode.point);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(parentNode, point, gCost);
    }
}
