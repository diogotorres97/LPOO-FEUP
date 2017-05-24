package com.lpoo.bombic.net.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

import io.netty.buffer.ByteBufInputStream;

/**
 Created by pedro on 07/05/2017.
 */
public class CommandLookup {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLookup.class);

    private static HashMap<Integer, Class> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put(AddPlayerCommand.COMMAND_ID, AddPlayerCommand.class);
        COMMANDS.put(BombExplosionCommand.COMMAND_ID, BombExplosionCommand.class);
        COMMANDS.put(DropBombCommand.COMMAND_ID, DropBombCommand.class);
        COMMANDS.put(LoginRequest.COMMAND_ID, LoginRequest.class);
        COMMANDS.put(MoveCommand.COMMAND_ID, MoveCommand.class);
        COMMANDS.put(NameInUseCommand.COMMAND_ID, NameInUseCommand.class);
        COMMANDS.put(ReadyCommand.COMMAND_ID, ReadyCommand.class);
    }

    @SuppressWarnings("unchecked")
    public static AbstractGameCommand getCommand(final ByteBufInputStream pDataIn)
            throws IOException {
        final int commandID = pDataIn.readShort();
        final Class commandClass = COMMANDS.get(commandID);
        if (commandClass != null) {
            AbstractGameCommand gameCommand = null;
            try {
                gameCommand = (AbstractGameCommand) commandClass.newInstance();
            } catch (Exception e) {
                LOGGER.warn("Something went wrong getting GameCommand!", e);
            }
            if (gameCommand != null) {
                gameCommand.read(pDataIn);
                return gameCommand;
            }
        } else {
            LOGGER.warn("Couldn't retrieve GameCommand {}", commandID);
        }
        return null;
    }


}
