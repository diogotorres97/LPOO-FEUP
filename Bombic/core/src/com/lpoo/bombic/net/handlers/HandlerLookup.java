package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.Logic.GameLogic.MultiPlayerGame;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.MoveCommand;
import com.lpoo.bombic.net.commands.NameInUseCommand;
import com.lpoo.bombic.net.commands.ReadyCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class HandlerLookup {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerLookup.class);

    private static HashMap<Integer, Class> HANDLERS = new HashMap<Integer, Class>();

    static {
        HANDLERS.put(ReadyCommand.COMMAND_ID, ReadyCommandHandler.class);
        HANDLERS.put(MoveCommand.COMMAND_ID, MoveCommandHandler.class);
        HANDLERS.put(NameInUseCommand.COMMAND_ID, NameInUseCommandHandler.class);
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
