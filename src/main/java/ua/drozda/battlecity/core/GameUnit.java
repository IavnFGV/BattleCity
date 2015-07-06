package ua.drozda.battlecity.core;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
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
    public static Long ONE_SECOND = 1000000000l;
    private static Boolean pause = false;
    private static Map<BasicState, Long> timeInState = new EnumMap<>(BasicState.class);

    static {
        timeInState.put(BasicState.CREATING, 10 * 500000000L);
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
    private LongProperty lifesCount;

    private DoubleProperty y;
    private DoubleProperty x;

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

    public final double getY() {
        return y == null ? 0.0 : y.get();
    }

    public final void setY(double value) {
        yProperty().set(value);
    }

    public final DoubleProperty yProperty() {
        if (y == null) {
            y = new SimpleDoubleProperty(0);
        }
        return y;
    }

    public final double getX() {
        return x == null ? 0.0 : x.get();
    }

    public final void setX(double value) {
        xProperty().set(value);
    }

    public final DoubleProperty xProperty() {
        if (x == null) {
            x = new SimpleDoubleProperty(0);
        }
        return x;
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
        if (lifes > 9) {
            lifes = 9l;
        }
        this.lifes = lifes;
        setLifesCount(lifes);
        if (this.lifes <= 0) {
            setCurrentBasicState(BasicState.EXPLODING);
        }

    }

    public final LongProperty lifesCountProperty() {
        if (lifesCount == null) {
            lifesCount = new SimpleLongProperty(this, "lifesCount", 2);
        }
        return lifesCount;
    }

    protected Long getTimeInState(BasicState state) {
        return timeInState.get(state);
    }

    @Deprecated
    protected final long getLifesCount() {
        return lifesCount == null ? 0 : lifesCount.getValue();
    }

    @Deprecated
    protected final void setLifesCount(long value) {
        lifesCountProperty().setValue(value);
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
