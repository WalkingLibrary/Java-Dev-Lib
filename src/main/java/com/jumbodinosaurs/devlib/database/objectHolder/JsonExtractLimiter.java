package com.jumbodinosaurs.devlib.database.objectHolder;

public class JsonExtractLimiter extends SelectLimiter
{
    
    private final String field;
    private final String searchValue;
    
    public JsonExtractLimiter(String field, String searchValue)
    {
        this.field = field;
        this.searchValue = searchValue;
    }
    
    @Override
    public String getSelectLimiterStatement()
    {
        String statement = "WHERE JSON_EXTRACT(%S, \"$.%s\")=%s";
        statement = String.format(statement, SQLDatabaseObjectUtil.OBJECT_COLUMN_NAME, this.field,
                                  "\"" + this.searchValue + "\"");
        return statement;
    }
}
