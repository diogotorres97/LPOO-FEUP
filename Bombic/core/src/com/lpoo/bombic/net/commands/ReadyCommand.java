package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

public class ReadyCommand
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 5;

    private int    mPlayerId;
    private String mUsername;

    public ReadyCommand() {
        super(COMMAND_ID);
    }

    public ReadyCommand(final int pPlayerId, final String pUsername) {
        super(COMMAND_ID);
        mPlayerId = pPlayerId;
        mUsername = pUsername;
    }

    @Override
    public void read(final ByteBufInputStream pDataIn)
            throws IOException {
        mPlayerId = pDataIn.readInt();
        mUsername = pDataIn.readUTF();
    }

    @Override
    protected void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeInt(mPlayerId);
        pDataOut.writeUTF(mUsername);
    }

    public int getPlayerId() {
        return mPlayerId;
    }

    public String getUsername() {
        return mUsername;
    }
}
