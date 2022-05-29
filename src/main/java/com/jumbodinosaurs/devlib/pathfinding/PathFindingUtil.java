package com.jumbodinosaurs.devlib.pathfinding;

import com.jumbodinosaurs.devlib.pathfinding.direction.Direction2D;
import com.jumbodinosaurs.devlib.pathfinding.direction.Direction3D;
import com.jumbodinosaurs.devlib.util.objects.Point3D;

public class PathFindingUtil
{
    //returns the 2D direction from point one to point two.
    public static Direction2D get2DDirectionFrom3DPoints(Point3D pointOne, Point3D pointTwo)
    {
        double z = pointOne.getZ() - pointTwo.getZ();
        double x = pointOne.getX() - pointTwo.getX();
        
        if(z != 0)
        {
            if(z != Math.abs(z))
            {
                z = -1;
            }
            else
            {
                z = 1;
            }
        }
        
        
        if(x != 0)
        {
            
            if(x != Math.abs(x))
            {
                x = -1;
            }
            else
            {
                x = 1;
            }
        }

        //Invert it because we are subtracting the first point from the second point
        //but really we want the direction from point one to point two.
        x = x * -1;
        z = z * -1;

        for (Direction2D direction : Direction2D.values())
        {
            if (direction.x == ((int) x) && direction.z == ((int) z))
            {
                return direction;
            }
        }
        //Should be unreachable
        return null;
    }

    //returns the 2D direction from point one to point two.
    public static Direction3D get3DDirectionFrom3DPoints(Point3D pointOne, Point3D pointTwo)
    {
        double z = pointOne.getZ() - pointTwo.getZ();
        double x = pointOne.getX() - pointTwo.getX();
        double y = pointOne.getY() - pointTwo.getY();

        if (z != 0)
        {
            if (z != Math.abs(z))
            {
                z = -1;
            }
            else
            {
                z = 1;
            }
        }


        if (x != 0)
        {

            if (x != Math.abs(x))
            {
                x = -1;
            }
            else
            {
                x = 1;
            }
        }

        if (y != 0)
        {

            if (y != Math.abs(x))
            {
                y = -1;
            }
            else
            {
                y = 1;
            }
        }

        //Invert it because we are subtracting the first point from the second point
        //but really we want the direction from point one to point two.
        x = x * -1;
        z = z * -1;
        y = y * -1;

        for (Direction3D direction : Direction3D.values())
        {
            if (direction.x == ((int) x) && direction.z == ((int) z) && direction.y == ((int) y))
            {
                return direction;
            }
        }
        //Should be unreachable
        return null;
    }
}
