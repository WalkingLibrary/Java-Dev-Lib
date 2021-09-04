package com.jumbodinosaurs.devlib.pathfinding.astar;

import com.jumbodinosaurs.devlib.util.objects.Point;

public interface NeighborLambda
{
    AStarNode getNeighbor(AStarNode parent, Point neighbor);
}
