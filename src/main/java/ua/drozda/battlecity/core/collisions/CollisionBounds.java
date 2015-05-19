package ua.drozda.battlecity.core.collisions;

import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.actors.Actor;

import static java.lang.Math.round;

/**
 * Created by GFH on 16.05.2015.
 */
public class CollisionBounds {
    private int xF;//Floor
    private int yF;
    private int xU;//Up
    private int yU;
    private World world;

    public CollisionBounds(World world, int x, int y) {
        this.world = world;
        this.xU = x * world.getCellWidth();
        this.xF = xU + world.getCellWidth();
        this.yU = y * world.getCellHeight();
        this.yF = yU + world.getCellHeight();
    }

    public CollisionBounds(World world, Actor actor) { //TODO WRONG
        this.world = world;
        this.xU = (int) round(actor.getPosition().getX());
        this.xF = xU + 2 * world.getCellWidth();
        this.yU = (int) round(actor.getPosition().getY());
        this.yF = yU - 2 * world.getCellHeight();

    }

    public int getxF() {
        return xF;
    }

    public void setxF(int xF) {
        this.xF = xF;
    }

    public int getyF() {
        return yF;
    }

    public void setyF(int yF) {
        this.yF = yF;
    }

    public int getxU() {
        return xU;
    }

    public void setxU(int xU) {
        this.xU = xU;
    }

    public int getyU() {
        return yU;
    }

    public void setyU(int yU) {
        this.yU = yU;
    }

    @Override
    public String toString() {
        return "CollisionBounds{" +
                "xF=" + xF +
                ", yF=" + yF +
                ", xU=" + xU +
                ", yU=" + yU +
                '}';
    }
}
