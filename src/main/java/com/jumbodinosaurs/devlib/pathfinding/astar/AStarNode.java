package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.pathfinding.Node;
import com.jumbodinosaurs.devlib.util.objects.Point;

import java.util.ArrayList;

public class AStarNode<E extends Point> implements Node
{
    private AStarNode parentNode;
    private E point;
    
    public AStarNode(AStarNode parentNode, E point)
    {
        this.parentNode = parentNode;
        this.point = point;
    }
    
    @Override
    public ArrayList<Node> getNeighbors()
    {
        return null;
    }
    
    @Override
    public AStarNode getParentNode()
    {
        return parentNode;
    }
    
    
    @Override
    public void setParentNode(Node parentNode)
    {
        this.parentNode = (AStarNode) parentNode;
    }
    
    public void setParentNode(AStarNode parentNode)
    {
        this.parentNode = parentNode;
    }
    
    public E getPoint()
    {
        return point;
    }
    
    public void setPoint(E point)
    {
        this.point = point;
    }
}
