package com.jumbodinosaurs.devlib.netty;


import com.jumbodinosaurs.devlib.netty.handler.IHandlerHolder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public abstract class ConnectionListenerInitializer extends ChannelInitializer<SocketChannel> implements Runnable
{
    protected IHandlerHolder handlerHolder;
    private int port;
    private boolean isRunning;
    private EventLoopGroup bossGroup, workerGroup;
    
    public ConnectionListenerInitializer(int port, IHandlerHolder iHandlerHolder)
    {
        this.port = port;
        this.handlerHolder = iHandlerHolder;
    }
    
    public void run()
    {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        try
        {
            this.isRunning = true;
            ServerBootstrap bootstrap = new ServerBootstrap().group(this.bossGroup, this.workerGroup).channel(NioServerSocketChannel.class).childHandler(this);
            bootstrap.bind(this.port).sync().channel().closeFuture().sync();
            
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            shutDown();
        }
    }
    
    public void shutDown()
    {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
        this.isRunning = false;
    }
    
    
    public boolean isRunning()
    {
        return isRunning;
    }
    
    public void setRunning(boolean running)
    {
        isRunning = running;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
}

