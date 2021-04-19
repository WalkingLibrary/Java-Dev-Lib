package com.jumbodinosaurs.devlib.log.filters.webhook.objects;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.FilterReply;

public abstract class WebHookSubscriber
{
    public abstract FilterReply getFilterReply(ILoggingEvent event);
}
