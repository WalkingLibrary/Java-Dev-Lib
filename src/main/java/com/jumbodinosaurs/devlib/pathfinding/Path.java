package com.jumbodinosaurs.devlib.pathfinding;

import com.jumbodinosaurs.devlib.util.objects.Point;

import java.util.ArrayList;

public class Path<E extends Point>
{
    private ArrayList<E> path;
    
    public Path(ArrayList<E> path)
    {
        this.path = path;
    }
    
    public ArrayList<E> getPath()
    {
        return path;
    }
    
    public void setPath(ArrayList<E> path)
    {
        this.path = path;
    }
    
    public void insertPoint(E point, int index) throws IndexOutOfBoundsException
    {
        this.path.add(index, point);
    }
    
}
