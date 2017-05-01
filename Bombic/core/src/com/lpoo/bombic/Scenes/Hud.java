package com.lpoo.bombic.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Bomber;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Image player1Img, player2Img, player3Img, player4Img;

    private Label bombLabel1, flameLabel1;
    private Label bombLabel2, flameLabel2;
    private Label bombLabel3, flameLabel3;
    private Label bombLabel4, flameLabel4;

    public Hud(PlayScreen screen, SpriteBatch sb){
        viewport = new FitViewport(Bombic.V_WIDTH, Bombic.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        TextureRegion region = new TextureRegion(screen.getAtlasHud().findRegion("hud"),0, 0, 118, 68 );
        player1Img = new Image(region);
        region = new TextureRegion(screen.getAtlasHud().findRegion("hud"),118, 0, 118, 68 );
        player2Img = new Image(region);
        region = new TextureRegion(screen.getAtlasHud().findRegion("hud"),236, 0, 118, 68 );
        player3Img = new Image(region);
        region = new TextureRegion(screen.getAtlasHud().findRegion("hud"),354, 0, 118, 68 );
        player4Img = new Image(region);



        //define a table used to show bombers info
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);


        switch (screen.getNumPlayers()){
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


        stage.addActor(table);
    }

    private Stack getStack1(){
        Stack stack1 = new Stack();
        stack1.add(player1Img);
        //Second add wrapped overlay object
        Table overlay = new Table();
        bombLabel1 = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        flameLabel1 = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        overlay.add(flameLabel1).expandX().align(Align.center).padLeft(player1Img.getWidth() / 1.5f);
        overlay.row();
        overlay.add(bombLabel1).expandX().align(Align.center).padBottom(player1Img.getHeight() / 3).padLeft(player1Img.getWidth() / 1.5f);
        stack1.add(overlay);

        return stack1;
    }

    private Stack getStack2(){
        Stack stack2 = new Stack();
        stack2.add(player2Img);
        //Second add wrapped overlay object
        Table overlay2 = new Table();
        bombLabel2 = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        flameLabel2 = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        overlay2.add(flameLabel2).expandX().align(Align.center).padLeft(player2Img.getWidth() / 1.5f);
        overlay2.row();
        overlay2.add(bombLabel2).expandX().align(Align.center).padBottom(player2Img.getHeight() / 3).padLeft(player2Img.getWidth() / 1.5f);
        stack2.add(overlay2);
        return stack2;
    }

    private Stack getStack3(){
        Stack stack3 = new Stack();
        stack3.add(player3Img);
        //Second add wrapped overlay object
        Table overlay3 = new Table();
        bombLabel3 = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        flameLabel3 = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        overlay3.add(flameLabel3).expandX().align(Align.center).padLeft(player3Img.getWidth() / 1.5f);
        overlay3.row();
        overlay3.add(bombLabel3).expandX().align(Align.center).padBottom(player3Img.getHeight() / 3).padLeft(player3Img.getWidth() / 1.5f);
        stack3.add(overlay3);

        return stack3;
    }

    private Stack getStack4(){
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

    public void setValues(Bomber bomber, int id){
        switch (id){
            case 1:
                bombLabel1.setText(java.lang.String.format("%01d", bomber.getBombs() - bomber.getPlacedBombs()));
                flameLabel1.setText(java.lang.String.format("%01d", bomber.getFlames()));
                break;
            case 2:
                bombLabel2.setText(java.lang.String.format("%01d", bomber.getBombs() - bomber.getPlacedBombs()));
                flameLabel2.setText(java.lang.String.format("%01d", bomber.getFlames()));
                break;
            case 3:
                bombLabel3.setText(java.lang.String.format("%01d", bomber.getBombs() - bomber.getPlacedBombs()));
                flameLabel3.setText(java.lang.String.format("%01d", bomber.getFlames()));
                break;
            case 4:
                bombLabel4.setText(java.lang.String.format("%01d", bomber.getBombs() - bomber.getPlacedBombs()));
                flameLabel4.setText(java.lang.String.format("%01d", bomber.getFlames()));
                break;
            default:
                break;

        }
    }

    private void miniBonus(){

    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
