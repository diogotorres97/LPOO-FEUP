package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class HandlerLookup {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerLookup.class);

    private static HashMap<Integer, Class> HANDLERS = new HashMap<>();

    static {
        HANDLERS.put(LoginRequest.COMMAND_ID, LoginRequestHandler.class);
        HANDLERS.put(MoveRequest.COMMAND_ID, MoveRequestHandler.class);
        HANDLERS.put(NameInUseCommand.COMMAND_ID, NameInUseRequestHandler.class);
        HANDLERS.put(NullGameSessionCommand.COMMAND_ID, NullGameSessionHandler.class);
    }

    @SuppressWarnings("unchecked")
    public static IGameCommandHandler getCommandHandler(final GameConnection pGameConnection,
                                                        final AbstractGameCommand pGameCommand) {
        try {
            final Class commandHandlerClass = HANDLERS.get(pGameCommand.getCommandId());
            if (commandHandlerClass != null) {
                return (IGameCommandHandler) commandHandlerClass.getConstructor(GameConnection.class,
                                                                                AbstractGameCommand.class)
                                                                .newInstance(pGameConnection, pGameCommand);
            } else {
                LOGGER.warn("Couldn't get GameCommand handler = {}", pGameCommand.getCommandId());
            }

        } catch (Exception e) {
            LOGGER.warn("Something went wrong getting GameCommand handler!", e);
        }
        return null;
    }
}
