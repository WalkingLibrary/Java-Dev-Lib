package com.jumbodinosaurs.devlib.util.objects;

import com.jumbodinosaurs.devlib.pathfinding.direction.Direction3D;

import java.util.ArrayList;
import java.util.Objects;

public class Point3D extends Point2D
{
    private double y;
    
    public Point3D(double x, double y, double z)
    {
        super(x, z);
        this.y = y;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public Point3D chop()
    {
        return new Point3D((int) getX(), (int) this.y, (int) getZ());
    }
    
    public Point3D differenceY(double yDifference)
    {
        return new Point3D(getX(), this.y + yDifference, getZ());
    }
    
    @Override
    public Point3D differenceX(double xDifference)
    {
        return new Point3D(getX() + xDifference, this.y, getZ());
    }
    
    @Override
    public Point3D differenceZ(double zDifference)
    {
        return new Point3D(getX(), this.y, getZ() + zDifference);
    }
    
    
    /*Maybe Look at the wiki next time you big dumb
     * https://en.wikipedia.org/wiki/Euclidean_distance
     * */
    @Override
    public double getEuclideanDistance(Point point)
    {
        Point3D wayPoint = (Point3D) point;
        return Math.sqrt(Math.pow(getX() - wayPoint.getX(), 2) +
                         (Math.pow(getZ() - wayPoint.getZ(), 2)) +
                         (Math.pow(getY() - wayPoint.getY(), 2)));
    }
    
   
    @Override
    public <E extends Point> ArrayList<E> getNeighbors()
    {
        ArrayList<Point3D> points = new ArrayList<Point3D>();
        for(Direction3D direction3D: Direction3D.values())
        {
            if(!direction3D.direction.equals("SAMEPOINT"))
            {
                Point3D neighbor = this.differenceX(direction3D.x);
                neighbor = neighbor.differenceY(direction3D.y);
                neighbor = neighbor.differenceZ(direction3D.z);
                points.add(neighbor);
            }
        }
        return (ArrayList<E>) points;
    }
    
    @Override
    public String toString()
    {
        return "Point3D{" + "x=" + getX() + "y=" + y  +  ", z=" + getZ() + '}';
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }
        Point3D point3D = (Point3D) o;
        return Double.compare(point3D.y, y) == 0;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), y);
    }
}
