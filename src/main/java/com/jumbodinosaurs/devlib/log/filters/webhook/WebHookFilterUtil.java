package com.jumbodinosaurs.devlib.log.filters.webhook;

import com.jumbodinosaurs.devlib.log.filters.webhook.objects.WebHookSubscriber;
import com.jumbodinosaurs.devlib.reflection.ReflectionUtil;

import java.util.ArrayList;

public class WebHookFilterUtil
{
    public static ArrayList<WebHookSubscriber> getSubScribes()
    {
        ArrayList<WebHookSubscriber> subscribers = new ArrayList<WebHookSubscriber>();
        for(Class classType : ReflectionUtil.getSubClasses(WebHookSubscriber.class))
        {
            try
            {
                WebHookSubscriber newModule = (WebHookSubscriber) classType.newInstance();
                subscribers.add(newModule);
            }
            catch(Exception e)
            {
                //Can Log this error or you risk recursive errors
                System.out.println(classType.getSimpleName() + " failed to load.");
            }
        }
        return subscribers;
    }
}
