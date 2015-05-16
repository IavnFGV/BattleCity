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

    public CollisionBounds(int x, int y) {
        this.xU = x * World.CELL_WIDTH;
        this.xF = xU + World.CELL_WIDTH;
        this.yU = y * World.CELL_HEIGH;
        this.yF = yU + World.CELL_HEIGH;
    }

    public CollisionBounds(Actor actor) { //TODO WRONG
        this.xU = (int) round(actor.getPosition().getX());
        this.xF = xU + 2 * World.CELL_WIDTH;
        this.yU = (int) round(actor.getPosition().getY());
        this.yF = yU - 2 * World.CELL_HEIGH;

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
