package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

public class MoveRequest
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 8;

    private int mKey;

    public MoveRequest() {
        super(COMMAND_ID);
    }

    public MoveRequest(int pKey) {
        super(COMMAND_ID);
        mKey = pKey;
    }


    @Override
    public void read(ByteBufInputStream pDataIn)
            throws IOException {
        mKey = pDataIn.readInt();
    }

    @Override
    protected void writeInternal(ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeInt(mKey);
    }

    public int getKey() {
        return mKey;
    }
}
