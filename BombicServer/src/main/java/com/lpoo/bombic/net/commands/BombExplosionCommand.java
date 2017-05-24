package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 Created by pedro on 07/05/2017.
 */
public class BombExplosionCommand
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 3;

    private int mBombId;

    public BombExplosionCommand(final int pBombId) {
        super(COMMAND_ID);
        mBombId = pBombId;
    }

    @Override
    public void read(final ByteBufInputStream pDataIn)
            throws IOException {
        mBombId = pDataIn.readInt();
    }

    @Override
    protected void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeInt(mBombId);
    }
}
