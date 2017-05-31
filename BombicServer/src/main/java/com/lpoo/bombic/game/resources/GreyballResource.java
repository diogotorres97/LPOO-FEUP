package com.lpoo.bombic.game.resources;

import org.jdom2.Element;

/**
 Created by pedro on 11/05/2017.
 */
public class GreyballResource {

    private static final String POS_X_ATTRIBUTE = "x";
    private static final String POS_Y_ATTRIBUTE = "y";

    private int mPosX;
    private int mPosY;

    public void init(final Element pGreyballXML) {
        mPosX = Integer.valueOf(pGreyballXML.getAttributeValue(POS_X_ATTRIBUTE));
        mPosY = Integer.valueOf(pGreyballXML.getAttributeValue(POS_Y_ATTRIBUTE));
    }

    public int getPosX() {
        return mPosX;
    }

    public int getPosY() {
        return mPosY;
    }
}
