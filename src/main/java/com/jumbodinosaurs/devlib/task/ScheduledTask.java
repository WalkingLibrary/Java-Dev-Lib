package com.jumbodinosaurs.devlib.task;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class ScheduledTask extends Task
{
    private ScheduledThreadPoolExecutor executor;
    private Future future;
    private int delay;
    private TimeUnit timeUnit;
    private int period;
    
    public ScheduledTask(ScheduledThreadPoolExecutor executor)
    {
        this.executor = executor;
    }
    
    public void start()
    {
        this.future = executor.scheduleAtFixedRate(this, getDelay(), getPeriod(), getTimeUnit());
    }
    
    public void stop()
    {
        future.cancel(true);
    }
    
    public int getDelay()
    {
        return delay;
    }
    
    public void setDelay(int delay)
    {
        this.delay = delay;
    }
    
    public TimeUnit getTimeUnit()
    {
        return timeUnit;
    }
    
    public void setTimeUnit(TimeUnit timeUnit)
    {
        this.timeUnit = timeUnit;
    }
    
    public int getPeriod()
    {
        return period;
    }
    
    public void setPeriod(int period)
    {
        this.period = period;
    }
}
