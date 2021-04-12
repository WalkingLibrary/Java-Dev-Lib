package com.jumbodinosaurs.devlib.util.objects;

import com.jumbodinosaurs.devlib.pathfinding.direction.Direction2D;

import java.util.ArrayList;
import java.util.Objects;

public class Point2D extends Point
{
    protected double x, z;
    
    public Point2D(double x, double z)
    {
        this.x = x;
        this.z = z;
    }
    
    public double getX()
    {
        return x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public double getZ()
    {
        return z;
    }
    
    public void setZ(double z)
    {
        this.z = z;
    }
    
    public Point2D differenceX(double xDifference)
    {
        return new Point2D(this.x + xDifference, this.z);
    }
    
    public Point2D differenceZ(double zDifference)
    {
        return new Point2D(this.x, this.z + zDifference);
    }
    
    public Point2D difference(Point2D point2D)
    {
        return new Point2D(this.x + point2D.getX(), this.z + point2D.getZ());
    }
    
    public Point2D chop()
    {
        return new Point2D((int) this.x, (int) this.z);
    }
    
    
    /*Maybe Look at the wiki next time you big dumb
     * https://en.wikipedia.org/wiki/Euclidean_distance
     * */
    @Override
    public double getEuclideanDistance(Point point)
    {
        Point2D wayPoint = (Point2D) point;
        return Math.sqrt(Math.pow(getX() - wayPoint.getX(), 2) + (Math.pow(getZ() - wayPoint.getZ(), 2)));
    }
    
    @Override
    public <E extends Point> ArrayList<E> getNeighbors()
    {
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        for(Direction2D direction2D: Direction2D.values())
        {
            if(!direction2D.direction.equals("SAMEPOINT"))
            {
                Point2D neighbor = this.differenceX(direction2D.x);
                neighbor = neighbor.differenceZ(direction2D.z);
                points.add(neighbor);
            }
        }
        return (ArrayList<E>) points;
    }
    
    @Override
    public String toString()
    {
        return "Point2D{" + "x=" + x + ", z=" + z + '}';
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
        Point2D point2D = (Point2D) o;
        return Double.compare(point2D.x, x) == 0 && Double.compare(point2D.z, z) == 0;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(x, z);
    }
}
