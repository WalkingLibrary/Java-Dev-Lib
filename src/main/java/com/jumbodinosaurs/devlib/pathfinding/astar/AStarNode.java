package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.pathfinding.Node;
import com.jumbodinosaurs.devlib.util.objects.Point;
import com.jumbodinosaurs.devlib.util.objects.Point3D;

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
        ArrayList<Node> neighbors = new ArrayList<Node>();
        for(Point neighbor: this.getPoint().getNeighbors())
        {
            Point3D neighbor3D = (Point3D) neighbor;
            neighbors.add(new AStarNode<Point3D>(this, neighbor3D));
        }
        return neighbors;
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
