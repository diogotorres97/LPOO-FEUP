package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.Logic.MultiPlayerGame;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.AddPlayerCommand;
import com.lpoo.bombic.net.commands.ReadyCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 Created by pedro on 07/05/2017.
 */
public class HandlerLookup {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerLookup.class);

    private static HashMap<Integer, Class> HANDLERS = new HashMap<Integer, Class>();

    static {
        HANDLERS.put(AddPlayerCommand.COMMAND_ID, AddPlayerCommandHandler.class);
        HANDLERS.put(ReadyCommand.COMMAND_ID, ReadyCommandHandler.class);
    }

    @SuppressWarnings("unchecked")
    public static IGameCommandHandler getCommandHandler(final MultiPlayerGame pMultiPlayerGame,
                                                        final AbstractGameCommand pGameCommand) {
        try {
            final Class commandHandlerClass = HANDLERS.get(pGameCommand.getCommandId());
            if (commandHandlerClass != null) {
                return (IGameCommandHandler) commandHandlerClass.getConstructor(MultiPlayerGame.class,
                                                                                AbstractGameCommand.class)
                                                                .newInstance(pMultiPlayerGame, pGameCommand);
            } else {
                LOGGER.warn("Couldn't get GameCommand handler = {}", pGameCommand.getCommandId());
            }

        } catch (Exception e) {
            LOGGER.warn("Something went wrong getting GameCommand handler!", e);
        }
        return null;
    }
}
