package com.lpoo.bombic.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

import java.util.HashMap;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Logic.GameLogic.Game.GAMESPEED;
import static com.lpoo.bombic.Tools.Constants.V_HEIGHT;
import static com.lpoo.bombic.Tools.Constants.V_WIDTH;

/**
 * Interface used to implement the creation of the table according to the number of players in the game.
 */
interface createPlayersInfo {
    void create();
}
/**
 * Hud used in the PlayScreen to display players information on the bonus they have.
 */
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Image[] playersImgs;
    private Image[][] bonusImages;
    private Label[][] playersLabels;
    private Stack[] playersStacks;

    private HashMap<Integer,createPlayersInfo > playersInfoMap;

    private Label speedLabel, pausedLabel;

    protected TextureAtlas atlasBonus, atlasHud;

    private boolean paused;

    private int numPlayers;

    private Table table;

    public Hud(SpriteBatch sb, int numPlayers) {
        viewport = new FitViewport(V_WIDTH, V_HEIGHT);
        stage = new Stage(viewport, sb);
        atlasBonus = gam.manager.get("bonus.atlas");
        atlasHud = gam.manager.get("hud.atlas");
        this.numPlayers = numPlayers;

        speedLabel = new Label("GAME SPEED: " + GAMESPEED, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pausedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        paused = false;

        playersInfoMap = new HashMap<Integer, createPlayersInfo>();

        playersImgs = new Image[4];
        bonusImages = new Image[4][7];
        playersLabels = new Label[4][3];
        playersStacks = new Stack[4];

        createPlayersImgs();
        createBonusImgs();
        createStacks();

        createTable();

    }

    /**
     * Creates the table with all its components.
     */
    private void createTable(){
        table= new Table();
        table.top();
        table.setFillParent(true);

        initiatePlayersInfoMap();

        playersInfoMap.get(numPlayers).create();

        Table speedTable = new Table();
        speedTable.setFillParent(true);
        speedTable.top();

        speedTable.add(speedLabel);

        stage.addActor(table);
        stage.addActor(speedTable);

        Table pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();

        pauseTable.add(pausedLabel);
        stage.addActor(pauseTable);
    }

    /**
     * Initiates the hashMap
     */
    private void initiatePlayersInfoMap(){
        playersInfoMap.put(1, new createPlayersInfo(){
            public void create(){
                table.add(playersStacks[0]).expand().align(Align.topLeft);
            }
        });

        playersInfoMap.put(2, new createPlayersInfo(){
            public void create(){
                table.add(playersStacks[0]).expandX().align(Align.topLeft);
                table.row().expandY();
                table.add(playersStacks[1]).expandX().align(Align.bottomRight);
            }
        });

        playersInfoMap.put(3, new createPlayersInfo(){
            public void create(){
                table.add(playersStacks[0]).expandX().align(Align.topLeft);
                table.row().expandY();
                table.add(playersStacks[2]).expandX().align(Align.bottomLeft);
                table.add(playersStacks[1]).expandX().align(Align.bottomRight);
            }
        });

        playersInfoMap.put(4, new createPlayersInfo(){
            public void create(){
                table.add(playersStacks[0]).expandX().align(Align.topLeft);
                table.add(playersStacks[3]).expandX().align(Align.topRight);
                table.row().expandY();
                table.add(playersStacks[2]).expand().align(Align.bottomLeft);
                table.add(playersStacks[1]).expandX().align(Align.bottomRight);
            }
        });
    }

    /**
     * Creates players images to the background of their info Panel
     */
    private void createPlayersImgs() {
        TextureRegion region = new TextureRegion(atlasHud.findRegion("hud"), 0, 0, 118, 68);
        playersImgs[0] = new Image(region);
        region = new TextureRegion(atlasHud.findRegion("hud"), 118, 0, 118, 68);
        playersImgs[1] = new Image(region);
        region = new TextureRegion(atlasHud.findRegion("hud"), 236, 0, 118, 68);
        playersImgs[2] = new Image(region);
        region = new TextureRegion(atlasHud.findRegion("hud"), 354, 0, 118, 68);
        playersImgs[3] = new Image(region);

    }
    /**
     * Create bonus images
     */
    private void createBonusImgs() {
        for (int i = 0; i < 4; i++) {
            TextureRegion region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 120, 0, 20, 20);
            bonusImages[i][0] = new Image(region);
            bonusImages[i][0].setVisible(false);
            region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 100, 0, 20, 20);
            bonusImages[i][1] = new Image(region);
            bonusImages[i][1].setVisible(false);
            region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 0, 0, 20, 20);
            bonusImages[i][2] = new Image(region);
            bonusImages[i][2].setVisible(false);
            region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 20, 0, 20, 20);
            bonusImages[i][3] = new Image(region);
            bonusImages[i][3].setVisible(false);
            region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 60, 0, 20, 20);
            bonusImages[i][4] = new Image(region);
            bonusImages[i][4].setVisible(false);
            region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 80, 0, 20, 20);
            bonusImages[i][5] = new Image(region);
            bonusImages[i][5].setVisible(false);
            region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 100, 0, 20, 20);
            bonusImages[i][6] = new Image(region);
            bonusImages[i][6].setVisible(false);
        }
    }
    /**
     * Creates players info stacks
     */
    private void createStacks() {
        for (int i = 0; i < 4; i++) {
            Stack stack1 = new Stack();
            stack1.add(playersImgs[i]);

            Stack stack2 = new Stack();
            stack2.add(bonusImages[i][2]);
            stack2.add(bonusImages[i][3]);

            Stack stack3 = new Stack();
            stack3.add(bonusImages[i][4]);
            stack3.add(bonusImages[i][5]);
            //Second add wrapped overlay object
            Table overlay2 = new Table().align(Align.right).padRight(4f);
            playersLabels[i][0] = new Label("0X", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            playersLabels[i][1] = new Label("0X", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            playersLabels[i][2] = new Label("99%", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            playersLabels[i][2].setVisible(false);
            overlay2.add(bonusImages[i][6]).align(Align.left);
            overlay2.add(playersLabels[i][1]).align(Align.left);
            overlay2.row();
            overlay2.add(stack2).align(Align.left);
            overlay2.add(playersLabels[i][0]).align(Align.left);
            overlay2.row();
            overlay2.add(stack3).align(Align.left).padTop(1f);
            overlay2.add(playersLabels[i][2]).align(Align.left).padTop(1f);

            Table overlay1 = new Table().align(Align.left);
            overlay1.add(bonusImages[i][0]).align(Align.left).padTop(playersImgs[i].getHeight() / 1.5f).padLeft(5f);
            overlay1.add(bonusImages[i][1]).align(Align.right).padTop(playersImgs[i].getHeight() / 1.5f).padLeft(15f);


            stack1.add(overlay1);
            stack1.add(overlay2);

            playersStacks[i] = stack1;
        }
    }
    /**
     * Sets players values on their info Panel
     * @param player - the player which info will be altered
     */
    public void setValues(Player player) {
        int id = player.getId() - 1;

        playersLabels[id][1].setText(java.lang.String.format("%01d" + "X", player.getFlames()));
        if (player.isKickingBombs())
            bonusImages[id][0].setVisible(true);
        if (player.isSendingBombs())
            bonusImages[id][1].setVisible(true);

        setBadBonusValues(player, id);
        setBombValues(player, id);
    }
    /**
     * Sets bad bonus values
     * @param player - the player which info will be altered
     * @param id - id of the player
     */
    private void setBadBonusValues(Player player, int id) {
        if (player.isBadBonusActive()) {
            if (player.getBadBonus().getId() == Constants.DEAD_BONUS) {
                bonusImages[id][5].setVisible(false);
                bonusImages[id][4].setVisible(true);
            } else {
                bonusImages[id][5].setVisible(true);
                bonusImages[id][4].setVisible(false);
            }
            playersLabels[id][2].setVisible(true);
            playersLabels[id][2].setText(java.lang.String.format("%02d", (int) player.getBadBonus().getStrategy().getTimeLeft()) + "%");
        } else {
            playersLabels[id][2].setVisible(false);
            bonusImages[id][4].setVisible(false);
            bonusImages[id][5].setVisible(false);
        }
    }
    /**
     * Sets bombs values
     * @param player - the player which info will be altered
     * @param id - id of the player
     */
    private void setBombValues(Player player, int id) {
        if (player.getnNBombs() > 0) {
            bonusImages[id][3].setVisible(true);
            playersLabels[id][0].setText(java.lang.String.format("%01d" + "X", player.getnNBombs()));
        } else if (player.getnLBombs() > 0) {
            bonusImages[id][3].setVisible(false);
            bonusImages[id][2].setVisible(true);
            playersLabels[id][0].setText(java.lang.String.format("%01d" + "X", player.getnLBombs()));
        } else {
            bonusImages[id][2].setVisible(false);
            bonusImages[id][3].setVisible(false);
            playersLabels[id][0].setText(java.lang.String.format("%01d" + "X", player.getBombs() - player.getPlacedBombs()));
        }
    }
    /**
     * Sets speed label to the value of gameSpeed
     */
    public void setSpeedLabel() {
        String str_speed = java.lang.String.format("%.1f", GAMESPEED);
        speedLabel.setText("GAME SPEED: " + str_speed);
    }
    /**
     * Sets pause Label according to whether the game is paused or not
     * @param set - represents whether the game is paused or not
     */
    public void setPauseLabel(boolean set) {
        if (set) {
            if (!paused) {
                pausedLabel.setText("GAME PAUSED");
                paused = true;
            }
        } else {
            if (paused) {
                pausedLabel.setText("");
                paused = false;
            }
        }
    }
    /**
     * Disposes of the hud stage
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
