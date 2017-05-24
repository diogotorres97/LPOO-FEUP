package com.lpoo.bombic.net.pipeline;

import com.lpoo.bombic.Logic.MultiPlayerGame;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 Created by pedro on 07/05/2017.
 */
public class GameChannelPipeline
        extends ChannelInitializer<SocketChannel> {

    public static final int PACKET_HEADER = 2;

    private final MultiPlayerGame mMultiPlayerGame;

    public GameChannelPipeline(final MultiPlayerGame pMultiPlayerGame) {
        mMultiPlayerGame = pMultiPlayerGame;
    }

    protected void initChannel(final SocketChannel ch)
            throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("frameDecoder", new GameFrameDecoder());
        pipeline.addLast("commandLookup", new GameCommandLookup(mMultiPlayerGame));
        pipeline.addLast("lengthPrepender", new LengthFieldPrepender(PACKET_HEADER));
    }
}
