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

    private int mPlayerId;
    private int mKey;

    public MoveCommand() {
        super(COMMAND_ID);
    }

    public MoveCommand(final int pPlayerId, final int pKey) {
        super(COMMAND_ID);
        mPlayerId = pPlayerId;
        mKey = pKey;
    }

    public void read(final ByteBufInputStream pDataIn)
            throws IOException {
        mPlayerId = pDataIn.readInt();
        mKey = pDataIn.readInt();
    }

    protected void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeInt(mPlayerId);
        pDataOut.writeInt(mKey);
    }

    public int getPlayerId() {
        return mPlayerId;
    }

    public int getKey() {
        return mKey;
    }
}
