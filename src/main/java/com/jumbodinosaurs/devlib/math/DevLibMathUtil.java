package com.jumbodinosaurs.devlib.math;

import com.jumbodinosaurs.devlib.math.exceptions.DivideByZeroException;
import com.jumbodinosaurs.devlib.math.exceptions.ImaginaryNumberException;
import com.jumbodinosaurs.devlib.math.exceptions.MatrixIsNotSquareException;
import com.jumbodinosaurs.devlib.pathfinding.PathFindingUtil;
import com.jumbodinosaurs.devlib.pathfinding.direction.Direction2D;
import com.jumbodinosaurs.devlib.util.objects.Point2D;
import com.jumbodinosaurs.devlib.util.objects.Point3D;

import java.util.ArrayList;

public class DevLibMathUtil
{
    
    
    /*
     *
     */
    public static double getDeterminate(double[][] matrix)
            throws MatrixIsNotSquareException
    {
        /*
         * Process for getting the Determinate of a Given Matrix
         * Ensure the Matrix given is square
         * Recursively solve for the Determinate
         *
         * Tutorial on Taking the Determinate of a Matrix
         * Khan Video
         * https://youtu.be/u00I3MCrspU
         *
         *  */
        
        //Ensure the Matrix given is square
        boolean isSquareMatrix = isSquareMatrix(matrix);
       
        
        if(!isSquareMatrix)
        {
                throw new MatrixIsNotSquareException("The Matrix Given was Not Square");
        }
        
        //Recursively solve for the Determinate
        if(matrix.length > 2)
        {
            double determinate = 0;
            for(int i = 0; i < matrix.length; i++)
            {
                double sign = -1;
                if((i + 1) % 2 != 0)
                {
                    sign = 1;
                }
                
                determinate += (sign * (matrix[0][i] * getDeterminate(getSubMatrixFromSquareMatrix(matrix, 0, i))));
            }
            return determinate;
        }
        else
        {
            //End of Recursion Loop
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        }
        
        
    }
    
    public static boolean isSquareMatrix(double[][] matrix)
    {
        for(int r = 0; r < matrix.length; r++)
        {
            if(matrix.length != matrix[r].length)
            {
                return false;
            }
        }
        return true;
    }
    
    public static double[][] getSubMatrixFromSquareMatrix(double[][] matrix, int rowToRemove, int columnToRemove)
            throws MatrixIsNotSquareException
    {
        /*
         * Process for getting a sub matrix of a given matrix
         * Ensure the Matrix given is square
         * Remove the Specified Columns and Rows
         *
         * Note: Matrices Given are assumed to be square
         *
         * Tutorial on Getting the sub matrix of a matrix
         * https://youtu.be/KvzS03KB2X8
         *  */
    
        //Ensure the Matrix given is square
        boolean isSquareMatrix = isSquareMatrix(matrix);
        if(!isSquareMatrix)
        {
            throw new MatrixIsNotSquareException("The Matrix Given was Not Square");
        }
    
        //Remove the Specified Columns and Rows
        double[][] subMatrix = new double[matrix.length - 1][matrix.length - 1];
        ArrayList<Double> tempSubMatrix = new ArrayList<Double>();
    
        for(int row = 0; row < matrix.length; row++)
        {
            for(int column = 0; column < matrix[row].length; column++)
            {
                if(row != rowToRemove && column != columnToRemove)
                {
                    tempSubMatrix.add(new Double(matrix[row][column]));
                }
            }
        }
        
        int tempSubMatrixIndex = 0;
        for(int r = 0; r < subMatrix.length; r++)
        {
            for(int c = 0; c < subMatrix[r].length; c++)
            {
                subMatrix[r][c] = tempSubMatrix.get(tempSubMatrixIndex);
                tempSubMatrixIndex++;
            }
        }
        return subMatrix;
    }
    
    
    public static double[][] changeColumn(double[][] matrix, double[] newColumn, int column)
    {
        double[][] tempMatrix = matrix.clone();
        tempMatrix[column] = newColumn;
        return tempMatrix;
    }
    
    
    public static double[] getCoefficients(Point2D pointOne, Point2D pointTwo, Point2D pointThree)
            throws DivideByZeroException, MatrixIsNotSquareException
    {
        /* y = ax^2 + bx + c
         * Process for getting the Coefficients of a quadratic from three points
         * Create three equation matrices using the x and z values of the points given
         * Solve The Determinate for the Denominator Matrix
         * Swap the Correct Column for each Numerator Matrix with the zValue Array
         * Solve the Determinate for Each of the Numerators
         * Solve for the Coefficients of the Quadratic
         *
         * Note: This Function is using Cramer's Rule to solve for the Quadratics Coefficients
         *
         * Tutorials/Information on Cramer's Rule
         *
         * Getting a Parabola from three points
         * https://www.youtube.com/watch?v=MMl8VHt1nU4
         *
         * Mario's Math Tutoring Explains The Solving of Systems of Equations
         * https://www.youtube.com/watch?v=v1gSS2oG4LA&t=3s
         *
         *  */
        
        
        //Create three equation matrices using the x and z values of the points given
        double[] equationOne = {Math.pow(pointOne.getX(), 2), pointOne.getX(), 1};
        double[] equationTwo = {Math.pow(pointTwo.getX(), 2), pointTwo.getX(), 1};
        double[] equationThree = {Math.pow(pointThree.getX(), 2), pointThree.getX(), 1};
        
        
        // Solve The Determinate for the Denominator Matrix
        double[][] denominator = {equationOne, equationTwo, equationThree};
        double denominatorDeterminate = 0;
        
        try
        {
            denominatorDeterminate = getDeterminate(denominator);
        }
        catch(MatrixIsNotSquareException e)
        {
            //Should never happen
            throw e;
        }
        
        if(denominatorDeterminate != 0)
        {
            throw new DivideByZeroException("The Determinate was Zero");
        }
        
        double[] zValueArray = {pointOne.getZ(), pointTwo.getZ(), pointThree.getZ()};
        
        //Swap the Correct Column for each Numerator Matrix with the zValue Array
        double[][] equationOnesNumerator = {equationOne.clone(), equationTwo.clone(), equationThree.clone()};
        equationOnesNumerator = changeColumn(equationOnesNumerator, zValueArray, 0);
        
        double[][] equationTwosNumerator = {equationOne.clone(), equationTwo.clone(), equationThree.clone()};
        equationTwosNumerator = changeColumn(equationTwosNumerator, zValueArray, 1);
        
        double[][] equationThreesNumerator = {equationOne.clone(), equationTwo.clone(), equationThree.clone()};
        equationThreesNumerator = changeColumn(equationThreesNumerator, zValueArray, 2);
        
        
        try
        {
            //Solve the Determinate for Each of the Numerators
            //Solve for the Coefficients of the Quadratic
            double a, b, c;
            a = getDeterminate(equationOnesNumerator) / denominatorDeterminate;
            b = getDeterminate(equationTwosNumerator) / denominatorDeterminate;
            c = getDeterminate(equationThreesNumerator) / denominatorDeterminate;
            return new double[]{a, b, c};
        }
        catch(MatrixIsNotSquareException e)
        {
            //Should never happen
            throw e;
        }
    }
    
    /*
     * y = ax^2 + bx + c
     * */
    public static double solveQuadraticForY(double[] coefficients, double x)
    {
        /*
         * Process for solving a Quadratic for Y
         * Plug in x and the coefficients
         *  */
        double[] tempCoefficients = coefficients.clone();
        double a, b, c, xSquared;
        a = tempCoefficients[0];
        b = tempCoefficients[1];
        c = tempCoefficients[2];
        xSquared = Math.pow(x, 2);
        return ((a * xSquared) + (x * b) + c);
    }
    
    
    /*
     * Quadratic Formula
     * x = -b +- sqrt(b^2-4ac) / 2a
     */
    public static double[] solveQuadraticFormula(double[] coefficients, double y)
            throws ImaginaryNumberException
    {
        /*
         * Process for solving the Quadratic Formula
         * Break the coefficients into their respective parts
         * Determine if There is an Imaginary Number Exception
         * Solve for the Positive and Negative Roots
         *  */
        //Break the coefficients into their respective parts
        double[] tempCoefficients = coefficients.clone();
        tempCoefficients[2] = tempCoefficients[2] - y;
        double a, b, c, negativeB, aTwo, fourAC, bSquared, bSquaredMinus4AC;
        a = tempCoefficients[0];
        b = tempCoefficients[1];
        c = tempCoefficients[2];
        bSquared = Math.pow(b, 2);
        fourAC = 4 * a * c;
        bSquaredMinus4AC = bSquared - fourAC;
        
        //Determine if There is an Imaginary Number Exception
        if(bSquaredMinus4AC >= 0)
        {
            negativeB = -b;
            aTwo = 2 * a;
            
            //Solve for the Positive and Negative Roots
            
            double positiveRoot = ((negativeB + Math.sqrt(bSquaredMinus4AC)) / aTwo);
            double negativeRoot = ((negativeB - Math.sqrt(bSquaredMinus4AC)) / aTwo);
            
            return new double[]{positiveRoot, negativeRoot};
            
        }
        else
        {
            throw new ImaginaryNumberException("No Roots A: " + a + " B:" + b + " C: " + c + " Y: " + y);
        }
    }
    
    
    /* "Proceed as if Success is inevitable" -Linberg
     * */
    public static double getQuadraticMinOrMaxXValue(double[] coefficients)
    {
        /*
         * Process for getting the Min or Max X Value of a given Quadratics Coefficients
         *
         * We take the Derivative of y = ax2 + bx + c with respects to x and where a, b, and c are constants
         * Giving us y` = 2ax + b
         * We are then looking for when the slope of y = ax2 + bx + c is zero
         * We plug 0 into y` = 2ax + b for y` and solve for X
         *
         *  */
        double[] tempCoefficients = coefficients.clone();
        double a, b, twoA;
        a = tempCoefficients[0];
        b = tempCoefficients[1];
        twoA = a * 2;
        return ((0 - b) / twoA);
    }
    
    
    /*
         Anatomy of a Jump
         * Using these three points we can make a
            Quadratic Equation to then give us each
            Point we will travel thru when jumping
            
         * Split the x-axis and z-axis
         * Six Points
         * 3 for x-axis
         * 3 for z-axis
                  y-axis
                   |
                   |
                   |
                   |         O
                   |         : ((x1 - x2), maxJumpHeight)
                   |         :
        ___________O_________:_________O______________
                   |(x1,0)              (x2,0)       x-axis
                   |
                   |
                   |
                   |
                  
       */
    public static ArrayList<Point3D> getJumpPointsQuadratic(Point3D startPoint, Point3D endPoint, double maxJumpHeight)
            throws ImaginaryNumberException
    {
        /* Process of Getting the Points of A 3D Jump
         * Split the X and Z axis and solve/store each axis's coefficients
         * Combine Both Quadratics
         *
         *
         *
         *  */
        ArrayList<Point3D> jumpPoints = new ArrayList<Point3D>();
        
        
        //Split the X and Z axis and solve/store each axis's coefficients
        Direction2D directionDifference = PathFindingUtil.get2DDirectionFrom3DPoints(startPoint, endPoint);
        
        //Note: it's assumed the half way Point between StartX/Z and EndX/Z is the maximum of the Jump
        double xDifference, zDifference, xDiffMiddle, zDiffMiddle;
        
        
        xDifference = Math.abs(endPoint.getX() - startPoint.getX());
        zDifference = Math.abs(endPoint.getZ() - startPoint.getZ());
        
        xDiffMiddle = xDifference / 2;
        zDiffMiddle = zDifference / 2;
        
        
        Point2D[] xAxisPoints = new Point2D[3];
        xAxisPoints[0] = new Point2D(startPoint.getX(), startPoint.getY());
        
        xAxisPoints[1] = new Point2D(startPoint.getX() + (xDiffMiddle * directionDifference.x),
                                     startPoint.getY() + maxJumpHeight);
        
        xAxisPoints[2] = new Point2D(endPoint.getX(), endPoint.getY());
        
        
        Point2D[] zAxisPoints = new Point2D[3];
        zAxisPoints[0] = new Point2D(startPoint.getZ(), startPoint.getY());
        
        zAxisPoints[1] = new Point2D(startPoint.getZ() + (zDiffMiddle * directionDifference.z),
                                     startPoint.getY() + maxJumpHeight);
        
        zAxisPoints[2] = new Point2D(endPoint.getZ(), endPoint.getY());
        
        
        double[] xCoefficients, zCoefficients;
        
        xCoefficients = null;
        zCoefficients = null;
        
        try
        {
            xCoefficients = getCoefficients(xAxisPoints[0], xAxisPoints[1], xAxisPoints[2]);
        }
        catch(DivideByZeroException e)
        {
        
        }
        catch(MatrixIsNotSquareException e)
        {
            //Shouldn't happen
            e.printStackTrace();
        }
        
        
        try
        {
            zCoefficients = getCoefficients(zAxisPoints[0], zAxisPoints[1], zAxisPoints[2]);
        }
        catch(DivideByZeroException e)
        {
            e.printStackTrace();
        }
        catch(MatrixIsNotSquareException e)
        {
            //Shouldn't happen
            e.printStackTrace();
        }
      

        
        
        /*
                    y-axis
                     |
                     |
                     |
                     |         O
                     |         : ((x1 - x2), maxJumpHeight)
                     |         :
          ___________O_________:________________________
                     |(x1,0)                          x-axis
                     |
                     |
                     |                    O
                     |                     (x2,0)         - I want to be able to get points below the start point.
                    
         */
        
        
        /* Combine Both Quadratics
         * Ensure the maximum point is in the final list
         * Add points at the specified Interval
         *
         */
        
        
        //Ensure the maximum point is in the final list
        double maxY, minY, xMaxY, zMaxY;
        maxY = startPoint.getY() + maxJumpHeight;
        minY = Math.min(startPoint.getY(), endPoint.getY());
        
        //If the coefficients of the x or z quadratic are null
        // we assume there is no change in the x or z in the jump
        xMaxY = startPoint.getX();
        zMaxY = startPoint.getZ();
        
        if(xCoefficients != null)
        {
            xMaxY = getQuadraticMinOrMaxXValue(xCoefficients);
        }
        
        if(zCoefficients != null)
        {
            zMaxY = getQuadraticMinOrMaxXValue(zCoefficients);
        }
        
        jumpPoints.add(new Point3D(xMaxY, maxY, zMaxY));
        
        
        /* Add points at the specified Interval
         *
         * How this For Loop Works
         * we Start at the top of the Jump -> maxY Note: (minus the specifiedInterval)
         * we then slice/work our way down to the lowest point -> minY
         * we add each new y interval -> yInterval; Along with the x and z points
         *  solved from their respective quadratics
         *
         */
        double specifiedInterval = .01;
        for(double yInterval = maxY - specifiedInterval; minY <
                                                         (yInterval -
                                                          specifiedInterval); yInterval -= specifiedInterval)
        {
            double[] xAnswers = new double[2];
            double[] zAnswers = new double[2];
            
            xAnswers[0] = startPoint.getX();
            xAnswers[1] = startPoint.getX();
            
            zAnswers[0] = startPoint.getZ();
            zAnswers[1] = startPoint.getZ();
            
            if(xCoefficients != null)
            {
                xAnswers = solveQuadraticFormula(xCoefficients, yInterval);
            }
            
            if(zCoefficients != null)
            {
                zAnswers = solveQuadraticFormula(zCoefficients, yInterval);
            }
            
            /* Solving a Quadratic gives us two answers
             * Depending on the Direction Difference we need to swap the answers given by
             * the function solveQuadraticFormula
             * If the Direction Difference is negative we swap the two answers
             */
            
            if(directionDifference.x == -1)
            {
                double temp = xAnswers.clone()[0];
                double temp2 = xAnswers.clone()[1];
                xAnswers[0] = temp2;
                xAnswers[1] = temp;
            }
            
            if(directionDifference.z == -1)
            {
                double temp = zAnswers.clone()[0];
                double temp2 = zAnswers.clone()[1];
                zAnswers[0] = temp2;
                zAnswers[1] = temp;
            }
            
            /* We now add the two points depending on if the yInterval
             * is above the initial start y
             *  */
            
            if(yInterval > startPoint.getY())
            {
                jumpPoints.add(0, new Point3D(xAnswers[0], yInterval, zAnswers[0]));
            }
            
            if(yInterval > endPoint.getY())
            {
                jumpPoints.add(jumpPoints.size(), new Point3D(xAnswers[1], yInterval, zAnswers[1]));
            }
        }
        
        return jumpPoints;
    }
    
    
    public static int round(double value)
    {
        int num1, num2;
        num1 = (int) value;
        num2 = num1 + 1;
        double differenceNum1 = Math.abs(num1 - value);
        double differenceNum2 = Math.abs(num2 - value);
        if(differenceNum1 < differenceNum2)
        {
            return num1;
        }
        return num2;
    }
    
    public static double roundAvoid(double value, int places)
    {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
    
}



