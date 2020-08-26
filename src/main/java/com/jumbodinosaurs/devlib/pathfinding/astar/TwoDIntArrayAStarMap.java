package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.util.objects.Point2D;

public class TwoDIntArrayAStarMap extends AStarMap
{
    private int[][] map;
    /* Map Makers
     * 0 means the cell can be traversed
     * 1 means the cell can not be traversed
     * 2 is the start cell
     * 3 is the goal cell
     *  */
    
    public TwoDIntArrayAStarMap(AStarNode startPoint, AStarNode goalPoint, int[][] map)
    {
        super(startPoint, goalPoint);
        this.map = map;
    }
    
    /* Map Makers
     * 0 means the cell can be traversed
     * 1 means the cell can not be traversed
     * 2 is the start cell
     * 3 is the goal cell
     *  */
    public static Point2D getStartPoint(int[][] map)
    {
        for(int r = 0; r < map.length; r++)
        {
            for(int c = 0; c < map[r].length; c++)
            {
                if(map[r][c] == 2)
                {
                    return new Point2D(r, c);
                }
            }
        }
        return null;
    }
    
    /* Map Makers
     * 0 means the cell can be traversed
     * 1 means the cell can not be traversed
     * 2 is the start cell
     * 3 is the goal cell
     *  */
    public static Point2D getGoalPoint(int[][] map)
    {
        for(int r = 0; r < map.length; r++)
        {
            for(int c = 0; c < map[r].length; c++)
            {
                if(map[r][c] == 3)
                {
                    return new Point2D(r, c);
                }
            }
        }
        return null;
    }
    
    @Override
    public double getNewGCost(AStarNode parentNode, AStarNode childNode)
    {
        /* Map Makers
         * 0 means the cell can be traversed
         * 1 means the cell can not be traversed
         * 2 is the start cell
         * 3 is the goal cell
         *  */
        
        /* To get the correct g cost we need to check the given map for obstacles at the child nodes
         * spot in the array
         *
         * Note: if the child is not in the array bounds we assume it can not be traversed
         *
         * Note: it's assumed the int[][] map that is given is square
         * */
        int childX, childZ;
        childX = (int)((Point2D)childNode.getPoint()).chop().getX();
        childZ = (int)((Point2D)childNode.getPoint()).chop().getZ();
        
        //Check to see if it's in the bounds of map
        if(childX >= map.length || childX < 0 ||
           childZ >= map[childX].length || childZ < 0)
        {
            return Double.MAX_VALUE;
        }
        
        //If there is an obstacle at the child node we return infinity
        if(map[childX][childZ] == 1)
        {
            return Double.MAX_VALUE;
        }
        
        //now we return the g cost of the parent plus the distance from the parent node to the child node
        return parentNode.getGCost() + parentNode.getPoint().getEuclideanDistance(childNode.getPoint());
    }
}
