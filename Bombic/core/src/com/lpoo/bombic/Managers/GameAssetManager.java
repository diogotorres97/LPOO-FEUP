package com.lpoo.bombic.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Rui Quaresma on 12/05/2017.
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

        for (int i = 0; i < 5; i++)
            manager.load("menus/level" + i + ".png", Texture.class);

        loadAtlas();

        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        //loadMaps();

    }


    private void loadMaps() {
        for (int i = 1; i < 4; i++)
            manager.load("levels/lvl" + i + ".tmx", TiledMap.class);

        for (int i = 1; i < 2; i++)
            manager.load("levels/dm_" + i + ".tmx", TiledMap.class);
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


