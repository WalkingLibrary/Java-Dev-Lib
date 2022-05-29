package com.jumbodinosaurs.devlib.pathfinding.direction;

import java.util.Arrays;
import java.util.List;

public enum Direction2D
{
    //Directions should always have only ones and zeros as there x and z
    WEST(-1, 0, "WEST"),
    EAST(1, 0, "EAST"),
    NORTH(0, -1, "NORTH"),
    SOUTH(0, 1, "SOUTH"),
    NORTHWEST(-1, -1, "NORTHWEST"),
    NORTHEAST(1, -1, "NORTHEAST"),
    SOUTHWEST(-1, 1, "SOUTHWEST"),
    SOUTHEAST(1, 1, "SOUTHEAST"),
    SAMEPOINT(0, 0, "SAMEPOINT");
    
    
    public int x, z;
    public String direction;
    
    Direction2D(int x, int z, String direction)
    {
        this.x = x;
        this.z = z;
        this.direction = direction;
    }
    
    public static List<Direction2D> getCardinalDirections()
    {
        Direction2D[] cardinalPointsArray = {NORTH, SOUTH, EAST, WEST};
        return Arrays.asList(cardinalPointsArray);
        
    }
    
    public static List<Direction2D> getOrdinalDirections()
    {
        Direction2D[] ordinalPointsArray = {NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST};
        return Arrays.asList(ordinalPointsArray);
        
    }
    
    public boolean isDiagonal()
    {
        return this.x != 0 && this.z != 0;
    }
    
    public String toString()
    {
        return direction;
    }
    
    public Direction2D getOppositeDirection()
    {
        for (Direction2D direction : Direction2D.values())
        {
            if ((this.x * -1) == direction.x && (this.z * -1) == direction.z)
            {
                return direction;
            }
        }
        return SAMEPOINT;
    }

    public Direction2D getNextClockDirection(boolean clockWise)
    {
        if (clockWise)
        {
            switch (this)
            {
                case NORTH:
                    return NORTHEAST;
                case NORTHEAST:
                    return EAST;
                case EAST:
                    return SOUTHEAST;
                case SOUTHEAST:
                    return SOUTH;
                case SOUTH:
                    return SOUTHWEST;
                case SOUTHWEST:
                    return WEST;
                case WEST:
                    return NORTHWEST;
                case NORTHWEST:
                    return NORTH;
                default:
                    return SAMEPOINT;
            }
        }

        switch (this)
        {
            case NORTH:
                return NORTHWEST;
            case NORTHWEST:
                return WEST;
            case WEST:
                return SOUTHWEST;
            case SOUTHWEST:
                return SOUTH;
            case SOUTH:
                return SOUTHEAST;
            case SOUTHEAST:
                return EAST;
            case EAST:
                return NORTHEAST;
            case NORTHEAST:
                return NORTH;
            default:
                return SAMEPOINT;
        }
    }

    public boolean equals(Direction2D direction)
    {
        return direction.x == this.x && direction.z == this.z;
    }
}
