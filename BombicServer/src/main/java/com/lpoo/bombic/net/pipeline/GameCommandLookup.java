package com.lpoo.bombic.net.pipeline;

import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.CommandLookup;
import com.lpoo.bombic.net.handlers.HandlerLookup;
import com.lpoo.bombic.net.handlers.IGameCommandHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class GameCommandLookup
        extends SimpleChannelInboundHandler<ByteBuf> {

    private final GameConnection mGameConnection;

    public GameCommandLookup(final GameConnection pGameConnection) {
        mGameConnection = pGameConnection;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
            throws Exception {
        final ByteBufInputStream inputStream = new ByteBufInputStream(msg);
        final AbstractGameCommand gameCommand = CommandLookup.getCommand(inputStream);
        if (gameCommand != null) {
            final IGameCommandHandler gameHandler = HandlerLookup.getCommandHandler(mGameConnection, gameCommand);
            if (gameHandler != null) {
                mGameConnection.getGameManager()
                               .getGameScheduler()
                               .runSyncRunnable(gameHandler);
            }
        }
    }
}
