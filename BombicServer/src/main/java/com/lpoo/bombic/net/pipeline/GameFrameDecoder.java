package com.lpoo.bombic.net.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


public class GameFrameDecoder
        extends ByteToMessageDecoder {

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out)
            throws Exception {
        if (in.readableBytes() < Short.BYTES) {
            return;
        }
        in.markReaderIndex();
        final int frameLength = in.readUnsignedShort();
        if (in.readableBytes() < frameLength) {
            in.resetReaderIndex();
            return;
        }
        out.add(in.readSlice(frameLength)
                  .retain());
    }
}
