package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;


public abstract class AbstractGameCommand {

    private final int mCommandId;

    public AbstractGameCommand(final int pCommandId) {
        mCommandId = pCommandId;
    }

    public abstract void read(final ByteBufInputStream pDataIn)
            throws IOException;

    public void write(final ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeShort(mCommandId);
        this.writeInternal(pDataOut);
    }

    protected abstract void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException;

    public int getCommandId() {
        return this.mCommandId;
    }
}
