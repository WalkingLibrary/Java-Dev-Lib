package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.pathfinding.Path;
import com.jumbodinosaurs.devlib.pathfinding.PathBuilder;
import com.jumbodinosaurs.devlib.pathfinding.exceptions.NoAvailablePathException;
import com.jumbodinosaurs.devlib.pathfinding.exceptions.PreMatureStopException;
import com.jumbodinosaurs.devlib.util.objects.Point;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AStarPathBuilder extends PathBuilder
{
    private final ArrayList<AStarNode> open = new ArrayList<AStarNode>();
    private final HashMap<String, AStarNode> closed = new HashMap<String, AStarNode>();
    private AStarNode nodeToExpand;
    
    public AStarPathBuilder(AStarMap map)
    {
        super(map);
    }
    
    
    public int getLowestCostingNodeIndex()
    {
        int nodeIndex = 0;
        for(int i = 0; i < this.open.size(); i++)
        {
            AStarNode currentNode = this.open.get(i);
            if(getMap().getFCost(currentNode) < getMap().getFCost(this.open.get(nodeIndex)))
            {
                nodeIndex = i;
            }
        }
        return nodeIndex;
    }
    
    @Override
    public AStarMap getMap()
    {
        return (AStarMap) super.getMap();
    }
    
    @Override
    public Path buildPath() throws NoAvailablePathException, PreMatureStopException
    {
        /* https://en.wikipedia.org/wiki/A*_search_algorithm
         *
         * Process for building a path using AStar
         * Add The Start Node to the open list
         *
         * While we have nodes in open and haven't reached the goal node
         *   Get the lowest Costing Node from open
         *   Expand this node
         *
         * Ensure the goal point is reachable
         * Create and return the path from the node to expand
         *
         *  */
    
        
    
        //Add The Start Node to the open list
        this.open.add(this.getMap().getStartNode());
        boolean reachedGoalNode = false;
        
        do
        {
            if(!shouldContinueGenerating())
            {
                throw new PreMatureStopException("Path not found before Stop");
            }
            this.buildingLoopHookStart();
    
            //Get the lowest Costing Node from open
            nodeToExpand = this.open.remove(getLowestCostingNodeIndex());
            this.closed.put(nodeToExpand.hashCode() + "", nodeToExpand);
    
            this.buildingLoopHookMiddle();
            
            if(this.getMap().satisfiesEnd(nodeToExpand))
            {
                reachedGoalNode = true;
                this.buildingLoopHookEnd();
                break;
            }
    
            
            //Expand this node
            for(AStarNode neighborNode : nodeToExpand.getNeighbors(getNeighborLambda()))
            {
                boolean isInClosed = this.closed.containsKey(neighborNode.hashCode() + "");
                
                double neighborsGCost = this.getMap().getNewGCost(nodeToExpand, neighborNode);
                neighborNode.setGCost(neighborsGCost);
                
                if(!isInClosed && neighborsGCost < Double.MAX_VALUE)
                {
                    boolean isNeighborInOpen = false;
                    for(int i = 0 ; i < this.open.size(); i++)
                    {
                        AStarNode openNode = this.open.get(i);
                        if(openNode.equals(neighborNode))
                        {
                            isNeighborInOpen = true;
                            if(neighborNode.getGCost() < openNode.getGCost())
                            {
                                openNode.setParentNode(nodeToExpand);
                                openNode.setGCost(neighborNode.getGCost());
                            }
                            break;
                        }
                    }
    
                    if(!isNeighborInOpen)
                    {
                        this.open.add(neighborNode);
                    }
                }
            }
            this.buildingLoopHookEnd();
        }
        while(open.size() > 0);
        
        //Ensure the goal point is reachable
        if(!reachedGoalNode)
        {
            throw new NoAvailablePathException("Could not find a path from \n" +
                                               getMap().getStartPoint() +
                                               "\nto\n" +
                                               getMap().getGoalPoint());
        }
        //Create and return the path from the node to expand
        ArrayList<Point> pathPoints = new ArrayList<Point>();
        AStarNode currentNode = nodeToExpand;
        while(currentNode.getParentNode() != null)
        {
            pathPoints.add(currentNode.getParentNode().getPoint());
            currentNode = currentNode.getParentNode();
        }
        
        
        return new Path(pathPoints);
    }
    
    protected abstract NeighborLambda getNeighborLambda();
    
    //Adding this Function allows the AStarNode to be modified to have extra properties and then
    //have those properties propagated to the child node by over writing this function.
    protected AStarNode getNeighbor(AStarNode parent, Point neighbor)
    {
        return new AStarNode(nodeToExpand, neighbor);
    }
    
    public AStarNode getNodeToExpand()
    {
        return nodeToExpand;
    }
    
    public void setNodeToExpand(AStarNode nodeToExpand)
    {
        this.nodeToExpand = nodeToExpand;
    }
}
