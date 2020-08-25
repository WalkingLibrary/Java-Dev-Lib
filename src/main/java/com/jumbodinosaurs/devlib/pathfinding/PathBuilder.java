package com.jumbodinosaurs.devlib.pathfinding;

import com.jumbodinosaurs.devlib.pathfinding.exceptions.NoAvailablePathException;
import com.jumbodinosaurs.devlib.pathfinding.exceptions.PreMatureStopException;

public abstract class PathBuilder<E extends Map>
{
    private E map;
    private boolean continueGenerating = true;
    
    public PathBuilder(E map)
    {
        this.map = map;
    }
    
    public E getMap()
    {
        return map;
    }
    
    public void setMap(E map)
    {
        this.map = map;
    }
    
    public abstract Path buildPath() throws NoAvailablePathException, PreMatureStopException;
    
    
    
    /* These methods are here to allow the implementing application do things
     * to the PathBuilder/Application while the path builder is looping/building the path
     * For example drawing the path in real time or stopping the builder before a path is found
     * */
    public abstract void buildingLoopHookStart();
    public abstract void buildingLoopHookMiddle();
    public abstract void buildingLoopHookEnd();
    
    public boolean shouldContinueGenerating()
    {
        return continueGenerating;
    }
    
    public void setContinueGenerating(boolean continueGenerating)
    {
        this.continueGenerating = continueGenerating;
    }
}
