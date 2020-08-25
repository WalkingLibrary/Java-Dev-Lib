package com.jumbodinosaurs.devlib.util.objects;

import java.util.ArrayList;

public abstract class Point
{
    public abstract double getEuclideanDistance(Point point);
    
    public abstract <E extends Point> ArrayList<E> getNeighbors();
}
