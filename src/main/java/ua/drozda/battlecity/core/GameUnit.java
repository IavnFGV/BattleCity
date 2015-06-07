package ua.drozda.battlecity.core;

import javafx.geometry.Bounds;

import java.util.Observable;

/**
 * Created by GFH on 08.06.2015.
 * everything in game will be a GameUnit
 * and everything in game can have observers
 * <p>
 * it has Bounds (in classical game pixels)
 */
public abstract class GameUnit extends Observable {
    protected Bounds bounds;

    public enum BasicState {
        CREATING,
        ACTIVE,
        EXPLODING,
        DEAD;
    }
}
