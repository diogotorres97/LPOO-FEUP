package com.lpoo.bombic.Logic.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Item definition, for spawning purposes
 */
public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    /**
     * Constructor
     * @param position - position of the item
     * @param type - class of the item
     */
    public ItemDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }
}
