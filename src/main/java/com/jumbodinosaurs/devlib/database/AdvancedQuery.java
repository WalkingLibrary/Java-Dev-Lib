package com.jumbodinosaurs.devlib.database;

public abstract class AdvancedQuery extends Query
{
    public AdvancedQuery(String query)
    {
        super(query);
    }
    
    public abstract void afterExecutionInstructions();
}
