package com.lpoo.bombic.game.resources;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 Created by pedro on 11/05/2017.
 */
public class ResourcesManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesManager.class);

    private static final String WORLD_XML            = "/world.xml";
    private static final String BLOCKS_CHILD_NAME    = "blocks";
    private static final String GREYBALLS_CHILD_NAME = "greyballs";


    private final ArrayList<BlockResource>    mBlockResources    = new ArrayList<>();
    private final ArrayList<GreyballResource> mGreyballResources = new ArrayList<>();


    public void init() {
        try {
            final SAXBuilder builder = new SAXBuilder();
            final Document document = builder.build(getClass().getResourceAsStream(WORLD_XML));
            final Element worldXML = document.getRootElement();
            this.loadBlocks(worldXML);
            this.loadGreyballs(worldXML);
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
    }


    private void loadBlocks(final Element pWorldXML) {
        final Element blocksXML = pWorldXML.getChild(BLOCKS_CHILD_NAME);
        if (blocksXML != null) {
            for (final Element blockXML : blocksXML.getChildren()) {
                final BlockResource blockResource = new BlockResource();
                blockResource.init(blockXML);
                mBlockResources.add(blockResource);
            }
        }
        LOGGER.info("Loaded {} blocks!", mBlockResources.size());
    }

    private void loadGreyballs(final Element pWorldXML) {
        final Element greyballsXML = pWorldXML.getChild(GREYBALLS_CHILD_NAME);
        if (greyballsXML != null) {
            for (final Element greyballXML : greyballsXML.getChildren()) {
                final GreyballResource greyballResource = new GreyballResource();
                greyballResource.init(greyballXML);
                mGreyballResources.add(greyballResource);
            }
        }
        LOGGER.info("Loaded {} greyballs!", mGreyballResources.size());
    }

    public ArrayList<BlockResource> getBlockResources() {
        return mBlockResources;
    }

    public ArrayList<GreyballResource> getGreyballResources() {
        return mGreyballResources;
    }
}
