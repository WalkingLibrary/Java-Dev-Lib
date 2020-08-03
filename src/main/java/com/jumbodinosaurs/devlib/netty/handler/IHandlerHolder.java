package com.jumbodinosaurs.devlib.netty.handler;

import io.netty.handler.codec.MessageToMessageDecoder;

public interface IHandlerHolder<E>
{
    MessageToMessageDecoder<E> getInstance();
}
