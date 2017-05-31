package com.lpoo.bombic.net.pipeline;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.GameServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 Created by pedro on 07/05/2017.
 */
public class GameChannelPipeline
        extends ChannelInitializer<SocketChannel> {

    private static final int PACKET_HEADER = Short.BYTES;

    private final GameServer  mGameServer;
    private final GameManager mGameManager;

    public GameChannelPipeline(final GameServer pGameServer, final GameManager pGameManager) {
        mGameServer = pGameServer;
        mGameManager = pGameManager;
    }

    protected void initChannel(final SocketChannel ch)
            throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        final GameConnection gameConnection = new GameConnection(mGameManager, ch);

        pipeline.addLast("frameDecoder", new GameFrameDecoder());
        pipeline.addLast("commandLookup", new GameCommandLookup(gameConnection));
        pipeline.addLast("lengthPrepender", new LengthFieldPrepender(PACKET_HEADER));
    }
}
