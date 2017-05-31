package com.lpoo.bombic.net;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.game.session.GameSession;

import com.lpoo.bombic.net.commands.AbstractGameCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;

/**
 Created by pedro on 07/05/2017.
 */
public class GameConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameConnection.class);

    private final GameManager   mGameManager;
    private final SocketChannel mSocketChannel;


    private GameSession mGameSession;

    public GameConnection(final GameManager pGameManager, final SocketChannel pSocketChannel) {
        mGameManager = pGameManager;
        mSocketChannel = pSocketChannel;
        LOGGER.info("Received new connection!");
    }

    public void sendCommand(final AbstractGameCommand pGameCommand) {
        try {
            final ByteBufOutputStream outputStream = new ByteBufOutputStream(Unpooled.buffer());
            pGameCommand.write(outputStream);

            mSocketChannel.writeAndFlush(outputStream.buffer());
        } catch (Exception pE) {
            LOGGER.warn("Couldn't send command! {}", pE.getMessage());
        }
    }

    public GameManager getGameManager() {
        return mGameManager;
    }

    public GameSession getGameSession() {
        return mGameSession;
    }

    public void setGameSession(final GameSession pGameSession) {
        mGameSession = pGameSession;
    }
}
