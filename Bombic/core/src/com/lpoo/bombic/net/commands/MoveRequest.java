package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 Created by torres on 30-05-2017.
 */

public class MoveRequest
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 8;

    private int mKey;

    public MoveRequest(int pKey) {
        super(COMMAND_ID);
        mKey = pKey;
    }


    @Override
    public void read(ByteBufInputStream pDataIn)
            throws IOException {

    }

    @Override
    protected void writeInternal(ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeInt(mKey);
    }
}
