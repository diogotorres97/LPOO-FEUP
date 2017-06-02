package com.lpoo.bombic.net;

import com.lpoo.bombic.Logic.GameLogic.MultiPlayerGame;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.pipeline.GameChannelPipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SocketManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketManager.class);

    private static final String HOST = "127.0.0.1";
    private static final int    PORT = 8080;

    private final MultiPlayerGame mMultiPlayerGame;

    private Channel mChannel;

    public SocketManager(final MultiPlayerGame pMultiPlayerGame) {
        mMultiPlayerGame = pMultiPlayerGame;
    }

    public boolean init() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                 .channel(NioSocketChannel.class)
                 .handler(new GameChannelPipeline(mMultiPlayerGame));
        try {
            mChannel = bootstrap.connect(HOST, PORT)
                                .sync()
                                .channel();
        } catch (Exception pE) {
            LOGGER.warn("Couldn't connect to server! {}", pE.getMessage());
            return false;
        }
        LOGGER.info("Connected to server!");
        return true;
    }

    public void sendCommand(final AbstractGameCommand pGameCommand) {
        try {
            final ByteBufOutputStream outputStream = new ByteBufOutputStream(Unpooled.buffer());
            pGameCommand.write(outputStream);
            mChannel.writeAndFlush(outputStream.buffer());
        } catch (Exception pE) {
            LOGGER.warn("Couldn't send command! {}", pE.getMessage());
        }
    }

}
