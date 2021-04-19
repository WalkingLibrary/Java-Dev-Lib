package com.jumbodinosaurs.devlib.log.filters.webhook;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.jumbodinosaurs.devlib.log.filters.webhook.objects.WebHookSubscriber;

public class WebHookFilter extends Filter<ILoggingEvent>
{
    @Override
    public FilterReply decide(ILoggingEvent event)
    {
        for(WebHookSubscriber subscriber: WebHookFilterUtil.getSubScribes())
        {
            subscriber.getFilterReply(event);
        }
        return FilterReply.ACCEPT;
    }
}
