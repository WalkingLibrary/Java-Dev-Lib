package com.jumbodinosaurs.devlib.task;

public abstract class StartUpTask extends Task
{
    private Phase phase;
    
    public StartUpTask(Phase phase)
    {
        this.phase = phase;
    }
    
    public Phase getPhase()
    {
        return phase;
    }
    
    public void setPhase(Phase phase)
    {
        this.phase = phase;
    }
}
