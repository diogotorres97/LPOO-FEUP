package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

import java.util.HashMap;
import java.util.Random;

interface DirectionVelocities {
    Vector2 get();
}

/**
 * Creates enemies movement strategy
 */
public abstract class Strategy {

    /**
     * Generates the random direction (within its limitations)
     */
    protected Random rand;
    /**
     * Enemy to move
     */
    protected Enemy enemy;
    /**
     * Array with xAdd used to calculate free cells around enemy
     */
    protected int[] xAddCell;
    /**
     * Array with yAdd used to calculate free cells around enemy
     */
    protected int[] yAddCell;
    /**
     * Available directions to move to
     */
    protected int[] availableDirs;
    /**
     * Number of available directions
     */
    protected int numDirs;
    /**
     * No directions available, so stay still until there is one
     */
    protected boolean stayStill;
    /**
     * Only blank tiles around
     */
    protected boolean originalBlankMove;
    /**
     * Only ticking tiles around
     */
    protected boolean originalTickingMove;
    /**
     * Only flame tiles around
     */
    protected boolean originalFlameMove;
    /**
     * Only blank tiles around
     */
    protected boolean blankMove;
    /**
     * Only ticking tiles around
     */
    protected boolean tickingMove;
    /**
     * Only flame tiles around
     */
    protected boolean flameMove;
    /**
     * New velocity to make it change its direction
     */
    protected Vector2 newVelocity;
    /**
     * Followed player
     */
    protected boolean followed;
    /**
     * Player to chase coordinates
     */
    float[] playerToChase = new float[2];

    protected boolean centered;


    protected int aiLevel;

    protected HashMap<Integer, DirectionVelocities> directionVelocitiesHashMap;

    /**
     * Constructor
     */
    public Strategy() {
        rand = new Random();
        xAddCell = new int[4];
        yAddCell = new int[4];
        stayStill = blankMove = tickingMove = flameMove = false;
        originalBlankMove = originalTickingMove = originalFlameMove = false;
        directionVelocitiesHashMap = new HashMap<Integer, DirectionVelocities>();

    }

    /**
     * Initiates directionVelocitiesHashMap with the correspondent methods
     */
    protected void initiateDirectionVeloctiesMap() {
        directionVelocitiesHashMap.put(0, new DirectionVelocities() {
            @Override
            public Vector2 get() {
                return new Vector2(0, enemy.getSpeed());
            }
        });

        directionVelocitiesHashMap.put(1, new DirectionVelocities() {
            @Override
            public Vector2 get() {
                return new Vector2(enemy.getSpeed(), 0);
            }
        });

        directionVelocitiesHashMap.put(2, new DirectionVelocities() {
            @Override
            public Vector2 get() {
                return new Vector2(0, -enemy.getSpeed());
            }
        });

        directionVelocitiesHashMap.put(3, new DirectionVelocities() {
            @Override
            public Vector2 get() {
                return new Vector2(-enemy.getSpeed(), 0);
            }
        });

        directionVelocitiesHashMap.put(4, new DirectionVelocities() {
            @Override
            public Vector2 get() {
                return new Vector2(0, 0);
            }
        });
    }

    /**
     * Make enemy move
     *
     * @param enemy
     */
    public void move(Enemy enemy){
        this.enemy = enemy;
        numDirs = 0;
        availableDirs = new int[4];
        newVelocity = new Vector2();
        initiateDirectionVeloctiesMap();

    }

    /**
     * If enemy is centered in the square, move
     */
    protected void centeredMove() {
        if (getCentered()) {
            if (stayStill) {
                if (freeForFirstMoveCells()) {
                    changeDir();

                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    stayStill = false;
                }
            } else {
                int dir = changeDir();
                if (dir == 4) {
                    stayStill = true;
                } else {

                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                }
            }


        } else {
            setEnemyKeepVelocity();
        }
    }

    protected void trapsCenteredMove(){
        if (centered && enemy.isToMove()) {
            if (stayStill) {
                if (freeForFirstMoveCells()) {
                    changeDir();
                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    enemy.setToMove(false);
                    stayStill = false;
                }
            } else {
                int dir = changeDir();
                if (dir == 4) {
                    stayStill = true;
                } else {
                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    enemy.setToMove(false);
                }
            }


        } else {
            setEnemyKeepVelocity();
        }
    }

    /**
     * If enemy is centered in the square, move towards the player to chase
     */
    protected void chaseCenteredMove() {
        if (getCentered()) {
            if (stayStill) {
                if (freeForFirstMoveCells()) {
                    changeDir();

                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    stayStill = false;

                }
            } else {
                if ((playerToChase = enemy.checkPlayerNear())[0] != 0) {
                    followed = isFollowPlayer();

                }
                if (!followed) {
                    int dir = changeDir();
                    if (dir == 4) {
                        stayStill = true;
                    } else {
                        enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                        enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    }
                } else {
                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    followed = false;

                    if (tickingMove)
                        tickingMove = false;
                    else if (flameMove)
                        flameMove = false;
                }
            }


        } else {
            setEnemyKeepVelocity();
        }
    }

    /**
     * Mantain enemy velocity, according to the game speed
     */
    protected void setEnemyKeepVelocity() {
        if (enemy.getVelocity().x > 0) {
            enemy.setVelocity(directionVelocitiesHashMap.get(1).get());
        } else if (enemy.getVelocity().x < 0) {
            enemy.setVelocity(directionVelocitiesHashMap.get(3).get());
        } else if (enemy.getVelocity().y > 0) {
            enemy.setVelocity(directionVelocitiesHashMap.get(0).get());
        } else if (enemy.getVelocity().y < 0) {
            enemy.setVelocity(directionVelocitiesHashMap.get(2).get());
        }
    }

    /**
     * Change enemy direction
     *
     * @return
     */
    protected int changeDir() {
        getFreeCells();
        int dir;

        if (numDirs == 0)
            dir = 4;
        else
            dir = getDir();
        enemy.setVelocity(directionVelocitiesHashMap.get(dir).get());

        return dir;
    }

    /**
     * Choose a direction between the available ones
     *
     * @return
     */
    private int getDir() {
        int dir = rand.nextInt(numDirs);
        int firstPos = 0;
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            if (availableDirs[i] != 0 && !found)
                if (firstPos == dir) {
                    found = true;
                    dir = i;
                } else {
                    firstPos++;
                }

        }
        return dir;
    }

    /**
     * Check which cells around the enemy are free for its first move (only blank tiles or not flames)
     *
     * @return
     */
    protected boolean freeForFirstMoveCells() {
        switch (aiLevel) {
            case 1:
                return freeForFirstMoveCellsAI1();
            case 2:
                return freeForFirstMoveCellsAI2();
            case 3:
                return freeForFirstMoveCellsAI3();
        }
        return false;
    }

    private boolean freeForFirstMoveCellsAI1() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};
        tickingMove = true;
        flameMove = false;
        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);
            if (isFreeCell(auxCell)) {
                tickingMove = false;
                flameMove = true;
                return true;
            }

        }
        tickingMove = false;
        flameMove = true;
        return false;
    }

    private boolean freeForFirstMoveCellsAI2() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};
        TiledMapTileLayer.Cell auxCell = getCell(0, 0);
        if (findOneFreeTile())
            return true;
        else if (isFlameTile(auxCell.getTile().getId()) || isContinuosFlame(auxCell.getTile().getId())) {
            flameMove = true;
            tickingMove = false;
            return findOneFreeTile();
        }
        return false;
    }

    private boolean freeForFirstMoveCellsAI3() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};
        TiledMapTileLayer.Cell auxCell = getCell(0, 0);
        if (findOneFreeTile())
            return true;
        else if (isTickingTile(auxCell.getTile().getId())) {
            tickingMove = true;
            blankMove = false;
            return findOneFreeTile();
        } else if (isFlameTile(auxCell.getTile().getId()) || isContinuosFlame(auxCell.getTile().getId())) {
            tickingMove = true;
            blankMove = false;
            if (findOneFreeTile())
                return true;
            else {
                flameMove = true;
                tickingMove = false;
                return findOneFreeTile();
            }
        }

        return false;
    }

    /**
     * Check which cells around the enemy are free to change direction for, depending on the AI level
     */
    protected void getFreeCells() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};

        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);
            if (isFreeCell(auxCell)) {
                availableDirs[i] = 1;
                numDirs++;
            }
        }
        blankMove = originalBlankMove;
        tickingMove = originalTickingMove;
        flameMove = originalFlameMove;
    }

    /**
     * Chekcs whether enemy position is centered in a square different from the previous one
     *
     * @return
     */
    protected boolean getCentered() {
        float xPos = enemy.b2body.getPosition().x;
        float yPos = enemy.b2body.getPosition().y;

        if (((xPos + enemy.getWidth() / 2) * 2) < enemy.getLastSquareX()) {
            return true;
        } else if (((xPos - enemy.getWidth() / 2) * 2) > (enemy.getLastSquareX() + 1)) {
            return true;
        }

        if (((yPos + enemy.getHeight() / 2) * 2) < enemy.getLastSquareY()) {

            return true;
        } else if (((yPos - enemy.getHeight() / 2) * 2) > (enemy.getLastSquareY() + 1)) {
            return true;
        }

        return false;
    }

    /**
     * Get the cell the enemy is in
     *
     * @param xAdd
     * @param yAdd
     * @return
     */
    protected TiledMapTileLayer.Cell getCell(int xAdd, int yAdd) {

        TiledMapTileLayer layer = (TiledMapTileLayer) enemy.getGame().getMap().getLayers().get(1);

        int xPos = (int) ((enemy.b2body.getPosition().x * Constants.PPM / 50) + xAdd / 50);
        int yPos = (int) (enemy.b2body.getPosition().y * Constants.PPM / 50 + yAdd / 50);

        return ((xPos >= 0 && yPos >= 0) ? layer.getCell(xPos, yPos) : null);
    }

    /**
     * Recalculate the square X and Y
     */
    protected void generateNewSquares() {
        int newSquareX = (int) (enemy.b2body.getPosition().x * Constants.PPM / 50);
        int newSquareY = (int) (enemy.b2body.getPosition().y * Constants.PPM / 50);
        if (enemy.getLastSquareX() > newSquareX)
            enemy.setLastSquareX(newSquareX + 1);
        else if (enemy.getLastSquareX() < newSquareX)
            enemy.setLastSquareX(newSquareX - 1);

        if (enemy.getLastSquareY() > newSquareY)
            enemy.setLastSquareY(newSquareY + 1);
        else if (enemy.getLastSquareY() < newSquareY)
            enemy.setLastSquareY(newSquareY - 1);
    }

    /**
     * Whether the enemy hit an object and must change its direction (only for traps)
     */
    protected void hitChangeDir() {
        if (enemy.velocity.x != 0)
            hitChangeDirX();
        else if (enemy.velocity.y != 0)
            hitChangeDirY();


        enemy.setObjectHit(false);

    }

    private void hitChangeDirX() {
        if (enemy.velocity.x > 0) {
            if (isObjectTile(getCell(50, 0).getTile().getId()) || getCell(50, 0).getTile().getId() == Constants.BARREL_TILE) {
                enemy.setToMove(true);
            }
        } else if (isObjectTile(getCell(-50, 0).getTile().getId()) || getCell(-50, 0).getTile().getId() == Constants.BARREL_TILE) {
            enemy.setToMove(true);
        }

    }

    private void hitChangeDirY() {
        if (enemy.velocity.y > 0) {
            if (isObjectTile(getCell(0, 50).getTile().getId()) || getCell(0, 50).getTile().getId() == Constants.BARREL_TILE) {
                enemy.setToMove(true);
            }
        } else if (isObjectTile(getCell(0, -50).getTile().getId()) || getCell(0, -50).getTile().getId() == Constants.BARREL_TILE) {
            enemy.setToMove(true);
        }

    }

    /**
     * Whether it is for the enemy to chase the player
     *
     * @return
     */
    protected boolean isFollowPlayer() {
        if ((enemy.getLastSquareX() + 1 < playerToChase[0]) && isFreeCell(getCell(50, 0))) {
            newVelocity.x = enemy.getSpeed();
            newVelocity.y = 0;
            enemy.setVelocity(newVelocity);
            return true;
        } else if ((enemy.getLastSquareX() - 1 > playerToChase[0]) && isFreeCell(getCell(-50, 0))) {
            newVelocity.x = -enemy.getSpeed();
            newVelocity.y = 0;
            enemy.setVelocity(newVelocity);
            return true;
        }
        if ((enemy.getLastSquareY() + 1 < playerToChase[1]) && isFreeCell(getCell(0, 50))) {
            newVelocity.x = 0;
            newVelocity.y = enemy.getSpeed();
            enemy.setVelocity(newVelocity);
            return true;
        } else if ((enemy.getLastSquareY() - 1 > playerToChase[1]) && isFreeCell(getCell(0, -50))) {
            newVelocity.x = 0;
            newVelocity.y = -enemy.getSpeed();
            enemy.setVelocity(newVelocity);
            return true;
        }

        return false;
    }

    /**
     * Whether a certain tile is object tile
     *
     * @param id
     * @return
     */
    protected boolean isObjectTile(int id) {
        for (int i = 0; i < Constants.OBJECTS_TILES.length; i++) {
            if (id == Constants.OBJECTS_TILES[i])
                return true;
        }
        if (id == Constants.BARREL_TILE)
            return true;
        return false;

    }

    /**
     * Whether certain tile is a continuous flame
     *
     * @param id
     * @return
     */
    protected boolean isContinuosFlame(int id) {

        int aux_id = Constants.FIRST_CONTINUOS_FLAME_TILE;
        for (int i = 0; i < 8; i++) {

            if (id == aux_id)
                return true;

            aux_id++;
        }
        return false;
    }

    /**
     * Whether certain tile is a ticking tile
     *
     * @param id
     * @return
     */
    protected boolean isTickingTile(int id) {
        int aux_id = Constants.FIRST_FLAME_TILE + 8;
        for (int i = 0; i < 3; i++) {
            if (id == aux_id)
                return true;
            aux_id += 10;
        }
        return false;
    }

    /**
     * Whether certain tile is a flame
     *
     * @param id
     * @return
     */
    protected boolean isFlameTile(int id) {
        int aux_id = Constants.FIRST_FLAME_TILE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (id == aux_id)
                    return true;
                aux_id++;
            }
            aux_id += 3;
        }
        return false;
    }

    /**
     * Whether a cell is free or not
     *
     * @param cell
     * @return
     */
    protected boolean isFreeCell(TiledMapTileLayer.Cell cell) {
        if (blankMove && cell.getTile().getId() == Constants.BLANK_TILE)
            return true;
        else if (tickingMove && (cell.getTile().getId() == Constants.BLANK_TILE || isTickingTile(cell.getTile().getId()))) {
            return true;
        } else if (flameMove && !isObjectTile(cell.getTile().getId()))
            return true;

        return false;

    }

    protected boolean findOneFreeTile() {
        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);
            if (isFreeCell(auxCell))
                return true;
        }
        return false;
    }

}
