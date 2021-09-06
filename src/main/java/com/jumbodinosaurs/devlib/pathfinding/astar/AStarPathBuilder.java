package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.pathfinding.Map;
import com.jumbodinosaurs.devlib.pathfinding.Node;
import com.jumbodinosaurs.devlib.pathfinding.PathBuilder;
import com.jumbodinosaurs.devlib.pathfinding.exceptions.NoAvailablePathException;
import com.jumbodinosaurs.devlib.pathfinding.exceptions.PreMatureStopException;

import java.util.HashMap;

public abstract class AStarPathBuilder<E extends Node> extends PathBuilder
{
    
    protected HashMap<E, Double> open = new HashMap<E, Double>();
    
    protected Map map;
    private E nodeToExpand;
    
    public AStarPathBuilder(Map map)
    {
        this.map = map;
    }
    
    public E getLowestCostingNode()
    {
        E bestNode = null;
        double bestFCost = Double.MAX_VALUE;
        
        for(E node : open.keySet())
        {
            if(bestFCost > open.get(node))
            {
                bestFCost = open.get(node);
                bestNode = node;
            }
        }
        
        return bestNode;
    }
    
    
    public E buildPath()
            throws NoAvailablePathException, PreMatureStopException
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
        this.open.put((E) map.getStartNode(), 0.0);
        while(open.keySet().size() > 0)
        {
            if(!shouldContinueGenerating())
            {
                throw new PreMatureStopException("Path not found before Stop");
            }
            this.buildingLoopHookStart();
            
            //Get the lowest Costing Node from open
            nodeToExpand = getLowestCostingNode();
            this.buildingLoopHookMiddle();
            
            if(nodeToExpand.equals(map.getGoalNode()) || map.satisfiesEnd(nodeToExpand))
            {
                
                this.buildingLoopHookEnd();
                return nodeToExpand;
            }
            
            
            //Expand this node
            for(Node neighborNode : nodeToExpand.getNeighbors())
            {
                
                double tentativeG = map.g(nodeToExpand) + map.distance(nodeToExpand, neighborNode);
                if(tentativeG < map.g(neighborNode))
                {
                    map.setG(neighborNode, tentativeG);
                    neighborNode.setParentNode(nodeToExpand);
                    open.replace((E) neighborNode, map.g(neighborNode) + map.h(neighborNode));
                }
                
                
                if(!open.containsKey(neighborNode))
                {
                    open.put((E) neighborNode, map.g(neighborNode));
                }
            }
            
            this.buildingLoopHookEnd();
        }
        
        throw new NoAvailablePathException("Could not find a path from \n" +
                                           map.getStartNode().toString() +
                                           "\nto\n" +
                                           map.getGoalNode().toString());
    }
    
    
    public E getNodeToExpand()
    {
        return nodeToExpand;
    }
    
    public void setNodeToExpand(E nodeToExpand)
    {
        this.nodeToExpand = nodeToExpand;
    }
}
