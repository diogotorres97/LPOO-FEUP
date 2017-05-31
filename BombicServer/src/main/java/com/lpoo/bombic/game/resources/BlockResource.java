package com.lpoo.bombic.game.resources;

import org.jdom2.Element;

/**
 Created by pedro on 11/05/2017.
 */
public class BlockResource {

    private static final String BLOCK_TYPE_ATTRIBUTE = "type";
    private static final String POS_X_ATTRIBUTE      = "x";
    private static final String POS_Y_ATTRIBUTE      = "y";

    private int mBlockType;
    private int mPosX;
    private int mPosY;

    public void init(final Element pBlockXML) {
        mBlockType = Integer.valueOf(pBlockXML.getAttributeValue(BLOCK_TYPE_ATTRIBUTE));
        mPosX = Integer.valueOf(pBlockXML.getAttributeValue(POS_X_ATTRIBUTE));
        mPosY = Integer.valueOf(pBlockXML.getAttributeValue(POS_Y_ATTRIBUTE));
    }

    public int getBlockType() {
        return mBlockType;
    }

    public int getPosX() {
        return mPosX;
    }

    public int getPosY() {
        return mPosY;
    }
}
