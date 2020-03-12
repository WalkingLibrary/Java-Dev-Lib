package com.jumbodinosaurs.devlib.task;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class ScheduledTask extends Task
{
    private Future future;
    
    public ScheduledTask(ScheduledThreadPoolExecutor executor)
    {
        this.future = executor.scheduleAtFixedRate(this, getInitialDelay(), getPeriod(), getTimeUnit());
    }
    
    public void stop()
    {
        future.cancel(true);
    }
    
    public abstract int getInitialDelay();
    
    public abstract int getPeriod();
    
    public abstract TimeUnit getTimeUnit();
}
