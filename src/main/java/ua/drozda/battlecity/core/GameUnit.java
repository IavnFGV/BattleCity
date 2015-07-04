package ua.drozda.battlecity.core;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.EnumMap;
import java.util.Map;
import java.util.Observable;
import java.util.function.Function;

/**
 * Created by GFH on 08.06.2015.
 * everything in game will be a GameUnit
 * and everything in game can have observers
 * <p>
 * it has Bounds (in classical game pixels)
 */
public abstract class GameUnit extends Observable {
    private static Boolean pause = false;
    private static Map<BasicState, Long> timeInState = new EnumMap<>(BasicState.class);

    static {
        timeInState.put(BasicState.CREATING, 500000000L);
        timeInState.put(BasicState.ACTIVE, 0L);
        timeInState.put(BasicState.EXPLODING, 500000000L);
        timeInState.put(BasicState.DEAD, 0L);
    }

    protected Bounds bounds;
    protected Long leftTimeInBasicState = 0L;//
    protected BasicState currentBasicState;
    protected Boolean blockCurrentState = false;
    protected Long lastHeartBeat;
    protected Long lifes = 0L;
    protected BasicHeartBeatStrategy heartBeatStrategy;
    protected Function<GameUnit, Boolean> registrateAction;
    protected Function<GameUnit, Boolean> unRegistrateAction;
    protected Long deltaHeartBeat;

    public GameUnit(double x, double y, double width, double height, Long lifes, Long currentTime, Function<GameUnit, Boolean> registerAction, Function<GameUnit, Boolean> unRegisterAction) {
        this(x, y, width, height, lifes, currentTime, BasicState.CREATING, registerAction, unRegisterAction);
    }

    public GameUnit(double x, double y, double width, double height, Long lifes, Long currentTime, BasicState
            currentBasicState, Function<GameUnit, Boolean> registerAction, Function<GameUnit, Boolean> unRegisterAction) {
        this.setBounds(new BoundingBox(x, y, width, height));
        this.setLifes(lifes);
        this.setLastHeartBeat(currentTime);
        this.setCurrentBasicState(currentBasicState);
        this.setRegistrateAction(registerAction);
        this.setUnRegistrateAction(unRegisterAction);
        registrateAction.apply(this);
        //   unitList.add(this);
    }

    public static void setPause(Boolean pause) {
        GameUnit.setPause(pause);
    }

    @Override
    public String toString() {
        return "GameUnit{" +
                "bounds=" + bounds +
                ", leftTimeInBasicState=" + leftTimeInBasicState +
                ", currentBasicState=" + currentBasicState +
                ", blockCurrentState=" + blockCurrentState +
                ", lastHeartBeat=" + lastHeartBeat +
                ", lifes=" + lifes +
                ", heartBeatStrategy=" + heartBeatStrategy +
                ", registrateAction=" + registrateAction +
                ", unRegistrateAction=" + unRegistrateAction +
                ", deltaHeartBeat=" + deltaHeartBeat +
                "} ";
    }

    public Function<GameUnit, Boolean> getRegistrateAction() {
        return registrateAction;
    }

    public void setRegistrateAction(Function<GameUnit, Boolean> registrateAction) {
        this.registrateAction = registrateAction;
    }

    public void initUnit(Long now) {
        lastHeartBeat = now;
        deltaHeartBeat = 0l;
    }

    public Long getDeltaHeartBeat() {
        return deltaHeartBeat;
    }

    public void setDeltaHeartBeat(Long deltaHeartBeat) {
        this.deltaHeartBeat = deltaHeartBeat;
    }

    public Function<GameUnit, Boolean> getUnRegistrateAction() {
        return unRegistrateAction;
    }

    public void setUnRegistrateAction(Function<GameUnit, Boolean> unRegistrateAction) {
        this.unRegistrateAction = unRegistrateAction;
    }

    public void scale(Integer x, Integer y) {
        setBounds(new BoundingBox(getBounds().getMinX() * x, getBounds().getMinY() * y,
                getBounds().getWidth() * x, getBounds().getHeight() * y));
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        System.out.println("setBounds for" + this);
        if (checkBounds(bounds)) {
            this.bounds = bounds;
        }
    }

    protected Boolean checkBounds(Bounds bounds) {
        return true;
    }

    public void decLifes(Long lifes) {
        Long newLifes = this.getLifes() - lifes;
        this.setLifes(newLifes);
    }

    public Long getLifes() {
        return lifes;
    }

    public void setLifes(Long lifes) {
        this.lifes = lifes;
        if (this.lifes <= 0) {
            setCurrentBasicState(BasicState.EXPLODING);
        }
    }

    protected Long getTimeInState(BasicState state) {
        return timeInState.get(state);
    }

    public void heartBeat(Long now) {
        if (!isPause()) {
            deltaHeartBeat = now - getLastHeartBeat();
            getHeartBeatStrategy().perform(deltaHeartBeat);
        }
        setLastHeartBeat(now);
        notifyObservers();
    }

    public static Boolean isPause() {
        return pause;
    }

    public Long getLastHeartBeat() {
        return lastHeartBeat;
    }

    public BasicHeartBeatStrategy getHeartBeatStrategy() {
        if (this.heartBeatStrategy == null) {
            this.setHeartBeatStrategy(this.new BasicHeartBeatStrategy());
        }
        return heartBeatStrategy;
    }

    public void setHeartBeatStrategy(BasicHeartBeatStrategy heartBeatStrategy) {
        this.heartBeatStrategy = heartBeatStrategy;
    }

    public void setLastHeartBeat(Long lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }

    public Long getLeftTimeInBasicState() {
        return leftTimeInBasicState;
    }

    public void setLeftTimeInBasicState(Long leftTimeInBasicState) {
        this.leftTimeInBasicState = leftTimeInBasicState;
    }

    public BasicState getCurrentBasicState() {
        return currentBasicState;
    }

    public void setCurrentBasicState(BasicState currentBasicState) {
        this.currentBasicState = currentBasicState;
        this.setLeftTimeInBasicState(getTimeInState(currentBasicState));
        if (currentBasicState == BasicState.DEAD) {
            unRegistrateAction.apply(this);
            //      GameUnit.unitList.remove(this);
        }
    }

    public Boolean isBlockCurrentState() {
        return blockCurrentState;
    }

    public void setBlockCurrentState(Boolean blockCurrentState) {
        this.blockCurrentState = blockCurrentState;
    }

    public enum BasicState {
        CREATING,
        ACTIVE,
        EXPLODING,
        DEAD;
    }

    protected class BasicHeartBeatStrategy {

        public void perform(Long deltaTime) {
            changeBasicState(deltaTime);
        }

        public void changeBasicState(Long deltaTime) {
            if (!isBlockCurrentState() && getTimeInState(currentBasicState) > 0) { // so we can tick time
                setLeftTimeInBasicState(getLeftTimeInBasicState() - deltaTime);
                if ((getLeftTimeInBasicState()) <= 0) {
                    if (getCurrentBasicState() == BasicState.CREATING) {
                        setCurrentBasicState(BasicState.ACTIVE);
                    } else {
                        setCurrentBasicState(BasicState.DEAD);
                    }
                    setChanged();
                }
            }
        }
    }
}
