package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;


public class MoveCommand
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 1;

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
