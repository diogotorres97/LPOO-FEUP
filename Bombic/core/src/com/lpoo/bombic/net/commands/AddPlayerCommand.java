package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 Created by pedro on 11/05/2017.
 */

public class AddPlayerCommand
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 7;

    private int   mPlayerId;
    private float mPosX;
    private float mPosY;

    public AddPlayerCommand() {
        super(COMMAND_ID);
    }

    public AddPlayerCommand(final int pPlayerId, final float pPosX, final float pPosY) {
        super(COMMAND_ID);
        mPlayerId = pPlayerId;
        mPosX = pPosX;
        mPosY = pPosY;
    }

    @Override
    public void read(final ByteBufInputStream pDataIn)
            throws IOException {
        mPlayerId = pDataIn.readInt();
        mPosX = pDataIn.readFloat();
        mPosY = pDataIn.readFloat();
    }

    @Override
    protected void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeInt(mPlayerId);
        pDataOut.writeFloat(mPosX);
        pDataOut.writeFloat(mPosY);
    }

    public int getPlayerId() {
        return mPlayerId;
    }

    public float getPosX() {
        return mPosX;
    }

    public float getPosY() {
        return mPosY;
    }
}
