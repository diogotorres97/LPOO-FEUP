package com.lpoo.bombic.Managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Asset manager, responsible for loading all the assets
 */

public class GameAssetManager {
    public static AssetManager manager;

    public void create() {
        manager = new AssetManager();
        load();
    }

    public void load() {
        manager.load("background.png", Texture.class);
        manager.load("mouse.png", Texture.class);
        manager.load("menus/labels/labelStory.png", Texture.class);
        manager.load("menus/labels/labelDeathmatch.png", Texture.class);
        manager.load("menus/labels/labelNetworking.png", Texture.class);
        manager.load("menus/labels/labelSettings.png", Texture.class);
        manager.load("menus/labels/labelMonstersInfo.png", Texture.class);
        manager.load("menus/labels/labelHelp.png", Texture.class);
        manager.load("menus/labels/labelCredits.png", Texture.class);
        manager.load("menus/labels/labelQuit.png", Texture.class);

        manager.load("menus/labels/labelStart.png", Texture.class);
        manager.load("menus/labels/labelNumPlayers.png", Texture.class);
        manager.load("menus/labels/labelChooseLevel.png", Texture.class);
        manager.load("menus/labels/labelBack.png", Texture.class);

        manager.load("menus/box_dm.png", Texture.class);

        manager.load("menus/labels/labelBonusAvailable.png", Texture.class);
        manager.load("menus/labels/labelNumVictories.png", Texture.class);
        manager.load("menus/labels/labelMonsters.png", Texture.class);
        manager.load("menus/labels/labelMap5.png", Texture.class);
        manager.load("menus/labels/labelMap4.png", Texture.class);
        manager.load("menus/labels/labelMap3.png", Texture.class);
        manager.load("menus/labels/labelMap2.png", Texture.class);
        manager.load("menus/labels/labelMap1.png", Texture.class);
        manager.load("menus/labels/labelMapRandom.png", Texture.class);
        manager.load("menus/labels/labelFight.png", Texture.class);

        manager.load("skin/craftacular-ui.json", Skin.class);
        manager.load("menus/dm_menu1.png", Texture.class);
        manager.load("menus/dm_menu2.png", Texture.class);

        manager.load("menus/buttons/btnSoundChecked.png", Texture.class);
        manager.load("menus/buttons/btnSoundUnChecked.png", Texture.class);

        manager.load("menus/buttons/btnJoystickChecked.png", Texture.class);
        manager.load("menus/buttons/btnJoystickUnChecked.png", Texture.class);

        manager.load("menus/buttons/btnAccelerometerChecked.png", Texture.class);
        manager.load("menus/buttons/btnAccelerometerUnChecked.png", Texture.class);

        manager.load("joystickBack.png", Texture.class);
        manager.load("joystickKnob.png", Texture.class);
        manager.load("bombButton.png", Texture.class);
        manager.load("enterButton.png", Texture.class);
        manager.load("btnEscape.png", Texture.class);
        manager.load("btnPause.png", Texture.class);
        manager.load("btnPlus.png", Texture.class);
        manager.load("btnMinus.png", Texture.class);

        for (int i = 0; i < 5; i++)
            manager.load("menus/level" + i + ".png", Texture.class);

        loadAtlas();

    }

    private void loadAtlas(){
        manager.load("menu_icons.atlas", TextureAtlas.class);
        manager.load("bonus.atlas", TextureAtlas.class);
        manager.load("players_imgs.atlas", TextureAtlas.class);
        manager.load("enemies.atlas", TextureAtlas.class);
        manager.load("hud.atlas", TextureAtlas.class);
        manager.load("bombs.atlas", TextureAtlas.class);
        manager.load("player.atlas", TextureAtlas.class);
    }

    public static void dispose() {
        manager.dispose();
    }
}


