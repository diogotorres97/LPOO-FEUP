package com.lpoo.bombic.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Tools.Constants.V_HEIGHT;
import static com.lpoo.bombic.Tools.Constants.V_WIDTH;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Image player1Img, player2Img, player3Img, player4Img;

    private Image[] bonusImagesPlayer1;
    private Image[] bonusImagesPlayer2;
    private Image[] bonusImagesPlayer3;
    private Image[] bonusImagesPlayer4;

    private Label bombLabel1, flameLabel1, timeLabel1;
    private Label bombLabel2, flameLabel2, timeLabel2;
    private Label bombLabel3, flameLabel3, timeLabel3;
    private Label bombLabel4, flameLabel4, timeLabel4;

    private Label speedLabel, pausedLabel;

    private PlayScreen screen;

    protected TextureAtlas atlasBonus;

    private boolean paused;

    public Hud(PlayScreen screen, SpriteBatch sb) {
        viewport = new FitViewport(V_WIDTH, V_HEIGHT);
        stage = new Stage(viewport, sb);
        this.screen = screen;
        atlasBonus = new TextureAtlas("bonus.atlas");

        TextureRegion region = new TextureRegion(screen.getAtlasHud().findRegion("hud"), 0, 0, 118, 68);
        player1Img = new Image(region);
        region = new TextureRegion(screen.getAtlasHud().findRegion("hud"), 118, 0, 118, 68);
        player2Img = new Image(region);
        region = new TextureRegion(screen.getAtlasHud().findRegion("hud"), 236, 0, 118, 68);
        player3Img = new Image(region);
        region = new TextureRegion(screen.getAtlasHud().findRegion("hud"), 354, 0, 118, 68);
        player4Img = new Image(region);

        speedLabel = new Label("GAME SPEED: " + screen.getGame().getGameSpeed(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        pausedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        paused = false;

        createPlayer1Images();

        //define a table used to show bombers info
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);


        switch (screen.getGame().getNumPlayers()) {
            case 4:
                table.add(getStack1()).expandX().align(Align.topLeft);
                table.add(getStack4()).expandX().align(Align.topRight);

                table.row().expandY();

                table.add(getStack3()).expand().align(Align.bottomLeft);
                table.add(getStack2()).expandX().align(Align.bottomRight);

                break;

            case 3:
                table.add(getStack1()).expandX().align(Align.topLeft);

                table.row().expandY();

                table.add(getStack3()).expandX().align(Align.bottomLeft);
                table.add(getStack2()).expandX().align(Align.bottomRight);
                break;
            case 2:
                table.add(getStack1()).expandX().align(Align.topLeft);

                table.row().expandY();

                table.add(getStack2()).expandX().align(Align.bottomRight);
                break;
            default:
            case 1:
                table.add(getStack1()).expand().align(Align.topLeft);
                break;

        }

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

    private void createPlayer1Images() {
        bonusImagesPlayer1 = new Image[7];
        TextureRegion region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 120, 0, 20, 20);
        bonusImagesPlayer1[0] = new Image(region);
        bonusImagesPlayer1[0].setVisible(false);
        region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 100, 0, 20, 20);
        bonusImagesPlayer1[1] = new Image(region);
        bonusImagesPlayer1[1].setVisible(false);
        region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 0, 0, 20, 20);
        bonusImagesPlayer1[2] = new Image(region);
        bonusImagesPlayer1[2].setVisible(false);
        region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 20, 0, 20, 20);
        bonusImagesPlayer1[3] = new Image(region);
        bonusImagesPlayer1[3].setVisible(false);
        region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 60, 0, 20, 20);
        bonusImagesPlayer1[4] = new Image(region);
        bonusImagesPlayer1[4].setVisible(false);
        region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 80, 0, 20, 20);
        bonusImagesPlayer1[5] = new Image(region);
        bonusImagesPlayer1[5].setVisible(false);
        region = new TextureRegion(atlasBonus.findRegion("mini_bonus"), 100, 0, 20, 20);
        bonusImagesPlayer1[6] = new Image(region);
        bonusImagesPlayer1[6].setVisible(false);
    }

    private Stack getStack1() {
        Stack stack1 = new Stack();
        stack1.add(player1Img);

        Stack stack2 = new Stack();
        stack2.add(bonusImagesPlayer1[2]);
        stack2.add(bonusImagesPlayer1[3]);


        Stack stack3 = new Stack();
        stack3.add(bonusImagesPlayer1[4]);
        stack3.add(bonusImagesPlayer1[5]);
        //Second add wrapped overlay object
        Table overlay2 = new Table().align(Align.right).padRight(4f);
        bombLabel1 = new Label("0X", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        flameLabel1 = new Label("0X", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel1 = new Label("99%", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel1.setVisible(false);
        overlay2.add(bonusImagesPlayer1[6]).align(Align.left);
        overlay2.add(flameLabel1).align(Align.left);
        overlay2.row();
        overlay2.add(stack2).align(Align.left);
        overlay2.add(bombLabel1).align(Align.left);
        overlay2.row();
        overlay2.add(stack3).align(Align.left).padTop(1f);
        overlay2.add(timeLabel1).align(Align.left).padTop(1f);

        Table overlay1 = new Table().align(Align.left);
        overlay1.add(bonusImagesPlayer1[0]).align(Align.left).padTop(player1Img.getHeight() / 1.5f).padLeft(5f);
        overlay1.add(bonusImagesPlayer1[1]).align(Align.right).padTop(player1Img.getHeight() / 1.5f).padLeft(15f);


        stack1.add(overlay1);
        stack1.add(overlay2);

        return stack1;
    }

    private Stack getStack2() {
        Stack stack2 = new Stack();
        stack2.add(player2Img);
        //Second add wrapped overlay object
        Table overlay2 = new Table();
        bombLabel2 = new Label("0X", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        flameLabel2 = new Label("0X", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        overlay2.add(flameLabel2).expandX().align(Align.center).padLeft(player2Img.getWidth() / 1.5f);
        overlay2.row();
        overlay2.add(bombLabel2).expandX().align(Align.center).padBottom(player2Img.getHeight() / 3).padLeft(player2Img.getWidth() / 1.5f);
        stack2.add(overlay2);
        return stack2;
    }

    private Stack getStack3() {
        Stack stack3 = new Stack();
        stack3.add(player3Img);
        //Second add wrapped overlay object
        Table overlay3 = new Table();
        bombLabel3 = new Label("0X", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        flameLabel3 = new Label("0X", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        overlay3.add(flameLabel3).expandX().align(Align.center).padLeft(player3Img.getWidth() / 1.5f);
        overlay3.row();
        overlay3.add(bombLabel3).expandX().align(Align.center).padBottom(player3Img.getHeight() / 3).padLeft(player3Img.getWidth() / 1.5f);
        stack3.add(overlay3);

        return stack3;
    }

    private Stack getStack4() {
        Stack stack4 = new Stack();
        stack4.add(player4Img);
        //Second add wrapped overlay object
        Table overlay4 = new Table();
        bombLabel4 = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        flameLabel4 = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        overlay4.add(flameLabel4).expandX().align(Align.center).padLeft(player4Img.getWidth() / 1.5f);
        overlay4.row();
        overlay4.add(bombLabel4).expandX().align(Align.center).padBottom(player4Img.getHeight() / 3).padLeft(player4Img.getWidth() / 1.5f);
        stack4.add(overlay4);

        return stack4;
    }

    public void setValues(Player player) {
        switch (player.getId()) {
            case 1:

                flameLabel1.setText(java.lang.String.format("%01d" + "X", player.getFlames()));
                if (player.isKickingBombs())
                    bonusImagesPlayer1[0].setVisible(true);
                if (player.isSendingBombs())
                    bonusImagesPlayer1[1].setVisible(true);
                if (player.isBadBonusActive()) {
                    if (player.getBadBonus().getId() == Constants.DEAD_BONUS) {
                        bonusImagesPlayer1[5].setVisible(false);
                        bonusImagesPlayer1[4].setVisible(true);
                    }
                    else {
                        bonusImagesPlayer1[5].setVisible(true);
                        bonusImagesPlayer1[4].setVisible(false);
                    }
                    timeLabel1.setVisible(true);
                    timeLabel1.setText(java.lang.String.format("%02d", (int) player.getBadBonus().getStrategy().getTimeLeft()) + "%");
                } else {
                    timeLabel1.setVisible(false);
                    bonusImagesPlayer1[4].setVisible(false);
                    bonusImagesPlayer1[5].setVisible(false);
                }

                if (player.getnNBombs() > 0) {
                    bonusImagesPlayer1[3].setVisible(true);
                    bombLabel1.setText(java.lang.String.format("%01d" + "X", player.getnNBombs()));
                } else if (player.getnLBombs() > 0) {
                    bonusImagesPlayer1[3].setVisible(false);
                    bonusImagesPlayer1[2].setVisible(true);
                    bombLabel1.setText(java.lang.String.format("%01d" + "X", player.getnLBombs()));
                } else {
                    bonusImagesPlayer1[2].setVisible(false);
                    bonusImagesPlayer1[3].setVisible(false);
                    bombLabel1.setText(java.lang.String.format("%01d" + "X", player.getBombs() - player.getPlacedBombs()));
                }
                break;
            case 2:
                bombLabel2.setText(java.lang.String.format("%01d" + "X", player.getBombs() - player.getPlacedBombs()));
                flameLabel2.setText(java.lang.String.format("%01d" + "X", player.getFlames()));
                break;
            case 3:
                bombLabel3.setText(java.lang.String.format("%01d" + "X", player.getBombs() - player.getPlacedBombs()));
                flameLabel3.setText(java.lang.String.format("%01d" + "X", player.getFlames()));
                break;
            case 4:
                bombLabel4.setText(java.lang.String.format("%01d" + "X", player.getBombs() - player.getPlacedBombs()));
                flameLabel4.setText(java.lang.String.format("%01d" + "X", player.getFlames()));
                break;
            default:
                break;

        }

    }

    public void setSpeedLabel() {
        String str_speed = java.lang.String.format("%.1f", screen.getGame().getGameSpeed());
        speedLabel.setText("GAME SPEED: " + str_speed);
    }

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


    @Override
    public void dispose() {

        stage.dispose();
    }
}
