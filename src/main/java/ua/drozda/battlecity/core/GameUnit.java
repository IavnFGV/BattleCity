package ua.drozda.battlecity.core;

import javafx.beans.property.*;
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
    public static Long ONE_SECOND = 1000_000_000l;
    private static Map<BasicState, Long> timeInState = new EnumMap<>(BasicState.class);

    static {
        timeInState.put(BasicState.CREATING, 2 * ONE_SECOND);
        timeInState.put(BasicState.ACTIVE, 0L);
        timeInState.put(BasicState.EXPLODING, ONE_SECOND / 2);
        timeInState.put(BasicState.DEAD, 0L);
    }

    protected Bounds bounds;
    protected Long leftTimeInBasicState = 0L;//
    //protected BasicState currentBasicState;
    protected Boolean blockCurrentState = false;
    protected Long lastHeartBeat;
    protected Integer lifes = 0;
    protected BasicHeartBeatStrategy heartBeatStrategy;
    protected Function<GameUnit, Boolean> registrateAction;
    protected Function<GameUnit, Boolean> unRegistrateAction;
    protected Long deltaHeartBeat;
    protected Double width;
    protected Double height;
    private Boolean pause = false;
    private ObjectProperty<BasicState> basicStateProperty;
    private IntegerProperty lifesCount;
    private DoubleProperty x;
    private DoubleProperty y;

    public GameUnit(double x, double y, double width, double height, Integer lifes, Function<GameUnit, Boolean> registerAction, Function<GameUnit, Boolean> unRegisterAction) {
        this(x, y, width, height, lifes, BasicState.CREATING, registerAction, unRegisterAction, true);
    }

    public GameUnit(double x, double y, double width, double height, Integer lifes, BasicState
            currentBasicState, Function<GameUnit, Boolean> registerAction, Function<GameUnit, Boolean>
                            unRegisterAction, Boolean addListeners) {
        this.width = width;
        this.height = height;
        this.setLifes(lifes);
        this.setBasicState(currentBasicState);
        this.setRegistrateAction(registerAction);
        this.setUnRegistrateAction(unRegisterAction);
        this.setBounds(new BoundingBox(x, y, width, height));
        setX(x);
        if (addListeners) {
            xProperty().addListener((observable, oldValue, newValue) -> {
                this.setBounds(new BoundingBox(newValue.doubleValue(), this.getY(), this.width, this.height));
            });
            yProperty().addListener((observable, oldValue, newValue) -> {
                this.setBounds(new BoundingBox(this.getX(), newValue.doubleValue(), this.width, this.height));
            });

        }
        setY(y);
        registrateAction.apply(this);
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public final DoubleProperty yProperty() {
        if (y == null) {
            y = new SimpleDoubleProperty();
        }
        return y;
    }

    public final DoubleProperty xProperty() {
        if (x == null) {
            x = new SimpleDoubleProperty();
        }
        return x;
    }

    @Override
    public String toString() {
        return "GameUnit{" +
                "bounds=" + bounds +
                ", leftTimeInBasicState=" + leftTimeInBasicState +
                ", blockCurrentState=" + blockCurrentState +
                ", lastHeartBeat=" + lastHeartBeat +
                ", lifes=" + lifes +
                ", heartBeatStrategy=" + heartBeatStrategy +
                ", registrateAction=" + registrateAction +
                ", unRegistrateAction=" + unRegistrateAction +
                ", deltaHeartBeat=" + deltaHeartBeat +
                ", width=" + width +
                ", height=" + height +
                ", basicStateProperty=" + basicStateProperty +
                ", lifesCount=" + lifesCount +
                ", x=" + x +
                ", y=" + y +
                "} " + super.toString();
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
        if (bounds == null) {
            bounds = new BoundingBox(getX(), getY(), width, height);
        }
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public final double getX() {
        return x == null ? 0.0 : x.get();
    }

    public final double getY() {
        return y == null ? 0.0 : y.get();
    }

    public final void setY(double value) {
        if (checkBounds(getX(), value)) {
            yProperty().set(value);
        }
    }

    public final void setX(double value) {
        if (checkBounds(value, getY())) {
            xProperty().set(value);
        }
    }

    protected Boolean checkBounds(double newX, double newY) {
        return true;
    }

    public void decLifes(Integer lifes) {
        Integer newLifes = this.getLifes() - lifes;
        this.setLifes(newLifes);
    }

    public Integer getLifes() {
        return lifes;
    }

    public void setLifes(Integer lifes) {
        if (lifes > 9) {
            lifes = 9;
        }
        this.lifes = lifes;
        setLifesCount(lifes);
        if (this.lifes <= 0) {
            setBasicState(BasicState.EXPLODING);
        }

    }

    public final IntegerProperty lifesCountProperty() {
        if (lifesCount == null) {
            lifesCount = new SimpleIntegerProperty(this, "lifesCount", 2);
        }
        return lifesCount;
    }

    public final ObjectProperty<BasicState> basicStateProperty() {
        if (basicStateProperty == null) {
            basicStateProperty = new SimpleObjectProperty<>();
        }
        return basicStateProperty;
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

    public Boolean isPause() {
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

    public BasicState getBasicState() {
        return basicStateProperty().getValue();
    }

    public void setBasicState(BasicState basicState) {
        basicStateProperty().setValue(basicState);
        this.setLeftTimeInBasicState(getTimeInState(basicState));
        if (basicState == BasicState.DEAD) {
            unRegistrateAction.apply(this);  //TODO MAYBE IT IS UNNECESSARY??
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
            if (!isBlockCurrentState() && getTimeInState(getBasicState()) > 0) { // so we can tick time
                setLeftTimeInBasicState(getLeftTimeInBasicState() - deltaTime);
                if ((getLeftTimeInBasicState()) <= 0) {
                    if (getBasicState() == BasicState.CREATING) {
                        setBasicState(BasicState.ACTIVE);
                    } else {
                        setBasicState(BasicState.DEAD);
                    }
                    setChanged();
                }
            }
        }
    }
}
