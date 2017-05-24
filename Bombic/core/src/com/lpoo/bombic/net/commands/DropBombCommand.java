package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 Created by pedro on 07/05/2017.
 */
public class DropBombCommand
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 2;

    private int mPlayerId;

    public DropBombCommand() {
        super(COMMAND_ID);
    }

    public DropBombCommand(final int pPlayerId) {
        super(COMMAND_ID);
        mPlayerId = pPlayerId;
    }

    public void read(final ByteBufInputStream pDataIn)
            throws IOException {
        mPlayerId = pDataIn.readInt();
    }

    protected void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeInt(mPlayerId);
    }
}
