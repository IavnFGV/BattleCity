package ua.drozda.battlecity.core;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.EnumMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by GFH on 08.06.2015.
 * everything in game will be a GameUnit
 * and everything in game can have observers
 * <p>
 * it has Bounds (in classical game pixels)
 */
public abstract class GameUnit extends Observable {
    private static Map<BasicState, Long> timeInState = new EnumMap<>(BasicState.class);

    static {
        timeInState.put(BasicState.CREATING, 500000000L);
        timeInState.put(BasicState.ACTIVE, 0L);
        timeInState.put(BasicState.EXPLODING, 500000000L);
        timeInState.put(BasicState.DEAD, 0L);
    }

    protected Bounds bounds;
    protected Long timeChangeBasicState = 0L;//
    protected BasicState currentBasicState;
    protected Boolean blockCurrentState = false;
    protected Long lastHeartBeat;
    protected Long lives = 0L;

    public GameUnit(double x, double y, double width, double height, Long lives, Long currentTime) {
        this(x, y, width, height, lives, currentTime, BasicState.CREATING);
    }

    public GameUnit(double x, double y, double width, double height, Long lives, Long currentTime, BasicState currentBasicState) {
        this.bounds = new BoundingBox(x, y, width, height);
        this.lives = lives;
        this.timeChangeBasicState = currentTime;
        this.lastHeartBeat = currentTime;
        this.currentBasicState = currentBasicState;
    }

    public void decLives(Long lives) {
        this.lives -= lives;
        if (this.lives <= 0) {
            setCurrentBasicState(BasicState.EXPLODING);
        }
    }

    public void heartBeat(Long now) {
        if (!blockCurrentState && getTimeInState(currentBasicState) > 0) { // so we can tick time
            if ((now - timeChangeBasicState) > getTimeInState(currentBasicState)) {
                timeChangeBasicState = now;
                if (currentBasicState == BasicState.CREATING) {
                    currentBasicState = BasicState.ACTIVE;
                } else {
                    currentBasicState = BasicState.DEAD;
                }
                setChanged();
                notifyObservers();
            }
        }
    }

    protected Long getTimeInState(BasicState state) {
        return timeInState.get(state);
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Long getTimeChangeBasicState() {
        return timeChangeBasicState;
    }

    public void setTimeChangeBasicState(Long timeChangeBasicState) {
        this.timeChangeBasicState = timeChangeBasicState;
    }

    public BasicState getCurrentBasicState() {
        return currentBasicState;
    }

    public void setCurrentBasicState(BasicState currentBasicState) {
        this.currentBasicState = currentBasicState;
    }

    public Boolean isBlockCurrentState() {
        return blockCurrentState;
    }

    public void setBlockCurrentState(Boolean blockCurrentState) {
        this.blockCurrentState = blockCurrentState;
    }

    public Long getLastHeartBeat() {
        return lastHeartBeat;
    }

    public void setLastHeartBeat(Long lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }

    public enum BasicState {
        CREATING,
        ACTIVE,
        EXPLODING,
        DEAD;
    }
}
