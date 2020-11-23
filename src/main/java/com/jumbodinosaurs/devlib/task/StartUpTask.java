package com.jumbodinosaurs.devlib.task;

public abstract class StartUpTask extends Task
{
    //Smaller Numbers Come First
    private int orderingNumber;
    
    public StartUpTask()
    {
    }
    
    public StartUpTask(int orderingNumber)
    {
        this.orderingNumber = orderingNumber;
    }
    
    public int getOrderingNumber()
    {
        return orderingNumber;
    }
    
    public void setOrderingNumber(int orderingNumber)
    {
        this.orderingNumber = orderingNumber;
    }
}
