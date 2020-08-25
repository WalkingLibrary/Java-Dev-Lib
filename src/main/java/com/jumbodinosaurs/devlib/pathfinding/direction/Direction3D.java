package com.jumbodinosaurs.devlib.pathfinding.direction;

public enum Direction3D
{
    //Directions should always have only ones and zeros as there x and z
    
    //no Y Change
    WEST(-1, 0,0, "WEST"),
    EAST(1, 0,0, "EAST"),
    NORTH(0, 0,-1, "NORTH"),
    SOUTH(0, 0,1, "SOUTH"),
    NORTHWEST(-1, 0,-1, "NORTHWEST"),
    NORTHEAST(1, 0,-1, "NORTHEAST"),
    SOUTHWEST(-1, 0,1, "SOUTHWEST"),
    SOUTHEAST(1, 0,1, "SOUTHEAST"),
    SAMEPOINT(0, 0,0, "SAMEPOINT"),
    
    //UP
    UP_WEST(-1, 1,0, "UP_WEST"),
    UP_EAST(1, 1,0, "UP_EAST"),
    UP_NORTH(0, 1,-1, "UP_NORTH"),
    UP_SOUTH(0, 1,1, "UP_SOUTH"),
    UP_NORTHWEST(-1, 1,-1, "UP_NORTHWEST"),
    UP_NORTHEAST(1, 1,-1, "UP_NORTHEAST"),
    UP_SOUTHWEST(-1, 1,1, "UP_SOUTHWEST"),
    UP_SOUTHEAST(1, 1,1, "UP_SOUTHEAST"),
    UP(0, 1,0, "UP"),
    
    //DOWN
    DOWN_WEST(-1, -1,0, "DOWN_WEST"),
    DOWN_EAST(1, -1,0, "DOWN_EAST"),
    DOWN_NORTH(0, -1,-1, "DOWN_NORTH"),
    DOWN_SOUTH(0, -1,1, "DOWN_SOUTH"),
    DOWN_NORTHWEST(-1, -1,-1, "DOWN_NORTHWEST"),
    DOWN_NORTHEAST(1, -1,-1, "DOWN_NORTHEAST"),
    DOWN_SOUTHWEST(-1, -1,1, "DOWN_SOUTHWEST"),
    DOWN_SOUTHEAST(1, -1,1, "DOWN_SOUTHEAST"),
    DOWN(0, -1,0, "UP");
    
    
    public int x, y, z;
    public String direction;
    
    Direction3D(int x, int y, int z, String direction)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
    }
    
    public String toString()
    {
        return direction;
    }
    
    
    public boolean equals(Direction3D direction)
    {
        return direction.x == this.x && direction.y == this.y && direction.z == this.z;
    }
}
