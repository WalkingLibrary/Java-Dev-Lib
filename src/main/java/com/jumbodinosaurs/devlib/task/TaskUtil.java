package com.jumbodinosaurs.devlib.task;

import com.jumbodinosaurs.devlib.reflection.ReflectionUtil;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class TaskUtil
{
    public ArrayList<StartUpTask> getStartUpTasks()
    {
        ArrayList<Class> startUpTaskClasses = ReflectionUtil.getSubClasses(StartUpTask.class);
        ArrayList<StartUpTask> startUpTasks = new ArrayList<StartUpTask>();
        for(Class classType : startUpTaskClasses)
        {
            try
            {
                startUpTasks.add((StartUpTask) classType.newInstance());
            }
            catch(ReflectiveOperationException e)
            {
                e.printStackTrace();
            }
        }
        return startUpTasks;
    }
    
    
    public ArrayList<ScheduledTask> getScheduledTasks(ScheduledThreadPoolExecutor executor)
    {
        ArrayList<Class> startUpTaskClasses = ReflectionUtil.getSubClasses(StartUpTask.class);
        ArrayList<ScheduledTask> startUpTasks = new ArrayList<ScheduledTask>();
        for(Class classType : startUpTaskClasses)
        {
            try
            {
                ScheduledTask scheduledTask =
                        (ScheduledTask) classType.getConstructor(ScheduledThreadPoolExecutor.class)
                                                 .newInstance(executor);
                startUpTasks.add(scheduledTask);
            }
            catch(ReflectiveOperationException e)
            {
                e.printStackTrace();
            }
        }
        return startUpTasks;
    }
}
