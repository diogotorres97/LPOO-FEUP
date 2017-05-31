package com.lpoo.bombic.net;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.net.pipeline.GameChannelPipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 Created by pedro on 07/05/2017.
 */
public class GameServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    private static final int GAME_SERVER_PORT = 8080;

    private final GameManager mGameManager;

    public GameServer(final GameManager pGameManager) {
        mGameManager = pGameManager;
    }

    public void startListening() {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY, true)
              .option(ChannelOption.SO_KEEPALIVE, true)
              .childHandler(new GameChannelPipeline(this, mGameManager))
              .bind(GAME_SERVER_PORT);
        LOGGER.info("Game Server up! Listening for connections...");
    }
}
