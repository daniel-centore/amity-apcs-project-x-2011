/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation.
 **/
package org.amityregion5.projectx.common.entities.items.field;

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.Buyable;
import org.amityregion5.projectx.common.entities.Damageable;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * The basic Block. Used to protect prevent direct attack or movement. Has health which is drained by attacks.
 * 
 * @author Mike DiBuduo
 * @author Mike Wenke
 */
public class Block extends FieldItem implements Damageable, Buyable {

    private static final long serialVersionUID = 602L;
    public static final int DEFAULT_HEALTH = 100;
    private int hp;
    private final int maxHealth;
    public final static int PRICE = 5;
    private int stage = 0;
    public static final BufferedImage[] STAGES = new BufferedImage[]
    {
        ImageHandler.loadImage("BlockMask_0"),
        ImageHandler.loadImage("BlockMask_1"),
        ImageHandler.loadImage("BlockMask_2"),
        ImageHandler.loadImage("BlockMask_3"),
        ImageHandler.loadImage("BlockMask_4"),
    };
    private static final BufferedImage image = ImageHandler.loadImage("Block");

    public Block(int x, int y)
    {
        this(x, y, DEFAULT_HEALTH);
    }

    public Block(int x, int y, int health)
    {
        super(x, y);
        hp = health;
        maxHealth = health;
    }

    @Override
    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
        stage = 4 - (int) (((double) hp / maxHealth) * 4);
    }

    public int damage(int damage)
    {
        hp -= damage;
        return damage;
    }

    /**
     * Not sure if Blocks should have a max health. For now it can be infinitely reinforced. Perhaps a fence has a max health and a wall can be infinitely upgraded?
     * 
     * @param health amount to be healed by
     */
    public void heal(int health)
    {
        hp += health;
    }

    /**
     * If hp drops to zero, the block is destroyed and removed
     */
    public boolean killed()
    {
        return hp <= 0;
    }
//    @Override
//    public String getDefaultImage()
//    {
//        return "sprites/Block";
//    }

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }

    public int getStage()
    {
        return stage;
    }

    public int getMaxHp()
    {
        return maxHealth;
    }

    public int getCost()
    {
        return PRICE;
    }
}
