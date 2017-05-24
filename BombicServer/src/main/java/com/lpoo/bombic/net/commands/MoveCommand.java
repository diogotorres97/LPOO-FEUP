package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 Created by pedro on 07/05/2017.
 */
public class MoveCommand
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 1;

    public static final short KEY_DOWN  = 0;
    public static final short KEY_UP    = 1;
    public static final short KEY_LEFT  = 2;
    public static final short KEY_RIGHT = 3;

    private int   mAssetId;
    private short mDirection;

    public MoveCommand() {
        super(COMMAND_ID);
    }

    public MoveCommand(final int pAssetId, final short pDirection) {
        super(COMMAND_ID);
        mAssetId = pAssetId;
        mDirection = pDirection;
    }

    public void read(final ByteBufInputStream pDataIn)
            throws IOException {
        mAssetId = pDataIn.readInt();
        mDirection = pDataIn.readShort();
    }

    protected void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeInt(mAssetId);
        pDataOut.writeShort(mDirection);
    }
}
