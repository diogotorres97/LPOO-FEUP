package com.lpoo.bombic.Managers;

import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.AbstractScreen;
import com.lpoo.bombic.Screens.ChooseLevelScreen;
import com.lpoo.bombic.Screens.DeathmatchIntermidiateScreen;
import com.lpoo.bombic.Screens.DeathmatchScreen;
import com.lpoo.bombic.Screens.IntermidiateLevelsScreen;
import com.lpoo.bombic.Screens.MenuScreen;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Screens.StoryModeScreen;

import java.util.HashMap;

/**
 * Created by Rui Quaresma on 12/05/2017.
 */

public class GameScreenManager {


    private Bombic bombicGame;

    public HashMap<STATE, AbstractScreen> gameScreens;

    public enum STATE {
        CHOOSE_LEVEL,
        DEATHMATCH_INTERMIDIATE,
        DEATHMATCH,
        INTERMIDIATE_LEVELS,
        MENU,
        PLAY,
        STORY
    }

    public GameScreenManager(final Bombic bombicGame) {
        this.bombicGame = bombicGame;

        initGameScreens();
        setScreen(STATE.MENU);

    }

    private void initGameScreens() {
        gameScreens = new HashMap<STATE, AbstractScreen>();
        this.gameScreens.put(STATE.CHOOSE_LEVEL, new ChooseLevelScreen(bombicGame));
        this.gameScreens.put(STATE.DEATHMATCH_INTERMIDIATE, new DeathmatchIntermidiateScreen(bombicGame));
        this.gameScreens.put(STATE.DEATHMATCH, new DeathmatchScreen(bombicGame));
        this.gameScreens.put(STATE.INTERMIDIATE_LEVELS, new IntermidiateLevelsScreen(bombicGame));
        this.gameScreens.put(STATE.MENU, new MenuScreen(bombicGame));
        this.gameScreens.put(STATE.PLAY, new PlayScreen(bombicGame));
        this.gameScreens.put(STATE.STORY, new StoryModeScreen(bombicGame));


    }

    public void setScreen(STATE nextScreen) {
        bombicGame.setScreen(gameScreens.get(nextScreen));
    }

    public AbstractScreen getScreen(STATE nextScreen) {
        return gameScreens.get(nextScreen);
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null)
                screen.dispose();
        }
    }


}
