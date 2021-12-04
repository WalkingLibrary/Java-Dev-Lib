package com.jumbodinosaurs.devlib.database.objectHolder;

public class NoLimit extends SelectLimiter
{
    @Override
    public String getSelectLimiterStatement()
    {
        return "";
    }
}
