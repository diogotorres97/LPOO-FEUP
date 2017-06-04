package com.lpoo.bombic.Screens;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.GameLogic.Game;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Scenes.Hud;
import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Logic.Sprites.Items.Item;
import com.lpoo.bombic.Tools.AndroidController;
import com.lpoo.bombic.Tools.Constants;
import com.lpoo.bombic.Tools.InputController;

import static com.lpoo.bombic.Bombic.isAndroid;

/**
 * Screen used to display the game
 */
public class PlayScreen extends AbstractScreen {

    private Game game;

    private AndroidController androidController;

    private OrthographicCamera gamecam;
    private Hud hud;

    private OrthogonalTiledMapRenderer renderer;

    private Box2DDebugRenderer b2dr;

    /**
     * Constructor
     * @param bombicGame
     */
    public PlayScreen(final Bombic bombicGame) {

        super(bombicGame);


    }

    @Override
    public void show() {

        gamecam = new OrthographicCamera();

        gamePort = new FitViewport(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM, gamecam);

        hud = new Hud(bombicGame.batch, game.getNumPlayers());

        renderer = new OrthogonalTiledMapRenderer(game.getMap(), 1 / Constants.PPM);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        b2dr = new Box2DDebugRenderer();

        androidController = new AndroidController(bombicGame.batch, 1);
        InputController.getInstance().resetVariables();
        InputController.getInstance().setAndroidController(androidController);

    }

    @Override
    public void setAvailableLevels(int level) {

    }

    @Override
    public void setMultiGame(boolean multiGame) {

    }

    @Override
    public void setNumLevel(int num) {

    }

    public Game getGame() {
        return game;
    }


    @Override
    public void setNumPlayers(int numPlayers) {

    }

    @Override
    public void setCurrentLevel(int level) {

    }

    @Override
    public void setMapId(int map_id) {

    }

    @Override
    public void setMonsters(boolean monsters) {

    }

    @Override
    public void setNumBonus(int numBonus) {

    }

    @Override
    public void setMaxVictories(int maxVictories) {

    }

    @Override
    public void setCurrentVictories(int[] currentVictories) {

    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Updates game
     * @param dt
     */
    public void update(float dt) {
        game.getWorld().step(1 / 60f, 6, 2);

        InputController.getInstance().handleCommonInput();
        if(InputController.getInstance().isPaused()){
            game.setGamePaused(true);
            hud.setPauseLabel(true);
        }else{
            game.setGamePaused(false);
            hud.setPauseLabel(false);
        }
        hud.setSpeedLabel();

        if (!game.getGamePaused()) {
            game.update(dt);
            for (Player player : game.getPlayers()) {
                hud.setValues(player);
            }

            if (!game.isGameOver())
                changeCamPosition();

            gamecam.update();
            renderer.setView(gamecam);
        } else {
            game.pause();
        }

    }

    /**
     * Players average x position
     * @return
     */
    private float getPlayersXAverage() {
        float sum = 0;

        for (Player player : game.getPlayers()) {
            sum += player.getX();
        }

        return sum / game.getPlayers().length;
    }
    /**
     * Players average y position
     * @return
     */
    private float getPlayersYAverage() {
        float sum = 0;

        for (Player player : game.getPlayers()) {
            sum += player.getY();
        }

        return sum / game.getPlayers().length;
    }

    public void changeCamPosition() {
        changeCamX();
        changeCamY();
    }

    private void changeCamX() {
        int map_width = Integer.parseInt(game.getMap().getProperties().get("width").toString());
        float avgX = getPlayersXAverage();

        if (map_width > gamePort.getWorldWidth())
            if (avgX > gamePort.getWorldWidth() / 2 && avgX < (map_width / Constants.PPM - gamePort.getWorldWidth() / 2))
                gamecam.position.x = avgX;
            else if (avgX < gamePort.getWorldWidth() / 2)
                gamecam.position.x = gamePort.getWorldWidth() / 2;
            else
                gamecam.position.x = (map_width / Constants.PPM) - (gamePort.getWorldWidth() / 2);
    }

    private void changeCamY() {
        int map_height = Integer.parseInt(game.getMap().getProperties().get("height").toString());
        float avgY = getPlayersYAverage();
        if (avgY > gamePort.getWorldHeight() / 2 && avgY < (map_height / Constants.PPM - gamePort.getWorldHeight() / 2))
            gamecam.position.y = avgY;
        else if (avgY < gamePort.getWorldHeight() / 2)
            gamecam.position.y = gamePort.getWorldHeight() / 2;
        else
            gamecam.position.y = map_height / Constants.PPM - gamePort.getWorldHeight() / 2;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        renderer.render();

        bombicGame.batch.setProjectionMatrix(gamecam.combined);
        bombicGame.batch.begin();

        for (Item item : game.getItems())
            item.draw(bombicGame.batch);
        if (game.getMode() == 2 && game.hasEnemies())
            for (Enemy enemy : game.getEnemies())
                enemy.draw(bombicGame.batch);
        else if (game.getMode() == 1)
            for (Enemy enemy : game.getEnemies())
                enemy.draw(bombicGame.batch);
        for (Player player : game.getPlayers()) {
            player.draw(bombicGame.batch);
        }
        bombicGame.batch.end();

        if(isAndroid)
            androidController.stage.draw();

        hud.stage.draw();
        switch (game.getMode()) {
            case 1:
                if (game.isGameOver()) {
                    bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setCurrentLevel(0);
                    bombicGame.gsm.setScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS);
                } else if (game.isLevelWon()) {
                    bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setCurrentLevel(game.getMap_id() + 1);
                    bombicGame.gsm.setScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS);
                }
                break;
            case 2:
                if (game.isGameOver() || game.isLevelWon()) {
                    bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setCurrentVictories(game.getCurrent_vics());
                    bombicGame.gsm.setScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE);
                }
                break;
            default:
                break;
        }


    }
    @Override
    public void dispose() {
        super.dispose();
        if (game != null) {
            game.dispose();
            renderer.dispose();
            b2dr.dispose();
            hud.dispose();
        }
    }
}
