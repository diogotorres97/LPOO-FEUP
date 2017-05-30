package com.lpoo.bombic.net.commands;

import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 Created by pedro on 07/05/2017.
 */
public class LoginRequest
        extends AbstractGameCommand {

    public static final int COMMAND_ID = 4;

    private String mUsername;

    public LoginRequest() {
        super(COMMAND_ID);
    }

    @Override
    public void read(final ByteBufInputStream pDataIn)
            throws IOException {
        mUsername = pDataIn.readUTF();
    }

    @Override
    protected void writeInternal(final ByteBufOutputStream pDataOut)
            throws IOException {
        pDataOut.writeUTF(mUsername);
    }

    public String getUsername() {
        return mUsername;
    }
}
