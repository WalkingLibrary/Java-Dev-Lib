package com.jumbodinosaurs.devlib.task;

import java.util.ArrayList;

public class DefaultStartUpTask implements Runnable
{
    
    public DefaultStartUpTask()
    {
    }
    
    //Added this just for you
    public static ArrayList<StartUpTask> getOrderedStarkUpTasks(ArrayList<StartUpTask> tasks)
    {
        
        ArrayList<StartUpTask> correctlyOrderedTasks = new ArrayList<StartUpTask>();
        while(tasks.size() > 0)
        {
            int taskToMoveIndex = 0;
            for(int i = 0; i < tasks.size(); i++)
            {
                int orderingNumberToCheck = tasks.get(taskToMoveIndex).getOrderingNumber();
                int orderingNumberToCheckAgainst = tasks.get(i).getOrderingNumber();
                if( orderingNumberToCheck > orderingNumberToCheckAgainst)
                {
                    taskToMoveIndex = i;
                }
            }
            correctlyOrderedTasks.add(tasks.remove(taskToMoveIndex));
        }
        
        return correctlyOrderedTasks;
    }
    
    @Override
    public void run()
    {
        /*
         * Process for Starting
         * Get StartUp Tasks
         * Order the StartUpTasks Correctly
         * Execute Each Task
         *   */
        
        //Get StartUp Tasks
        ArrayList<StartUpTask> startUpTasks = TaskUtil.getStartUpTasks();
        
        //Order the StartUpTasks Correctly
        startUpTasks = getOrderedStarkUpTasks(startUpTasks);
        
        for(StartUpTask task: startUpTasks)
        {
            //LogManager.consoleLogger.info("Starting Task: " + task.getClass().getSimpleName());
            task.run();
        }
        
       // LogManager.consoleLogger.info("Server Setup Task Complete");
    }
}
