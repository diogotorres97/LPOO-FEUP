package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

public class NullGameSessionCommand
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 10;

    public NullGameSessionCommand() {
        super(COMMAND_ID);
    }

    @Override
    public void read(final ByteBufInputStream pDataIn)
            throws IOException {
    }

    @Override
    protected void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException {
    }
}
