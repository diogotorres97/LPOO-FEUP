package com.lpoo.bombic.main;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.net.GameServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Created by pedro on 07/05/2017.
 */
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    private static final String PROPERTIES_FILE_PATH = "config.properties";


    public static void main(final String[] pArgs) {
        LOGGER.info("Bombic Server v1.0.0");
        final PropertiesManager propertiesManager = new PropertiesManager(PROPERTIES_FILE_PATH);
        final GameManager gameManager = new GameManager();
        gameManager.init();
        final GameServer gameServer = new GameServer(gameManager);
        gameServer.startListening();
    }
}
