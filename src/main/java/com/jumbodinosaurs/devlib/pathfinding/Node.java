package com.jumbodinosaurs.devlib.pathfinding;

import java.util.ArrayList;

public interface Node
{
    Node getParentNode();
    
    
    void setParentNode(Node parentNode);
    
    
    ArrayList<Node> getNeighbors();
}
