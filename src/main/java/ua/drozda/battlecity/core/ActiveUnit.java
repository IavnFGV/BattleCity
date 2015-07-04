package ua.drozda.battlecity.core;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.function.Function;

import static java.lang.Math.abs;
import static java.lang.Math.round;

/**
 * Created by GFH on 10.06.2015.
 */
public abstract class ActiveUnit extends GameUnit {
    protected Boolean engineOn = false;
    protected MoveStrategy moveStrategy;
    protected Long moveAccumulator;
    private Bounds newBounds;
    private Direction direction;
    private Long velocity = 8L;
    private Integer cellSize = 16;//bad idea TODO maybe we can use EasyDI lib???

    public ActiveUnit(double x, double y, double width, double height, Long lives, Long currentTime, BasicState
            currentBasicState, Direction direction, Long velocity, Function<GameUnit, Boolean> registerAction,
                      Function<GameUnit, Boolean> unRegisterAction) {
        super(x, y, width, height, lives, currentTime, currentBasicState, registerAction, unRegisterAction);
        this.setDirection(direction);
        this.setVelocity(velocity);
        this.setNewBounds(getBounds());
    }

    public Direction getDirection() {
        return direction;
    }

    private void fixPosition() {
        // System.out.println("fixPosition for" + this);
        Long x = nearest(getBounds().getMinX(), cellSize);
        Long y = nearest(getBounds().getMinY(), cellSize);
        Double newX = getBounds().getMinX();
        Double newY = getBounds().getMinY();

        if (abs(newX - x) < (cellSize / 2 + 1)) {
            newX = Double.valueOf(x);
        }
        if (abs(newY - y) < (cellSize / 2 + 1)) {
            newY = Double.valueOf(y);
        }
        setBounds(new BoundingBox(newX, newY, cellSize * 2, cellSize * 2));
    }

    private long nearest(double num, Integer base) {
        return (round(num / (base * 1.)) * base);
    }

    public void setDirection(Direction direction) {
        System.out.println("setDirection for" + this);
        if (direction != getDirection()) {
            fixPosition();
        }
        this.direction = direction;

    }

    @Override
    public String toString() {
        return "ActiveUnit{" +
                ", engineOn=" + engineOn +
                ", moveStrategy=" + moveStrategy +
                ", moveAccumulator=" + moveAccumulator +
                ", newBounds=" + newBounds +
                ", direction=" + direction +
                ", velocity=" + velocity +
                "} " + super.toString();
    }

    @Override
    public void initUnit(Long now) {
        super.initUnit(now);
        setMoveAccumulator(0l);
        setEngineOn(false);
    }

    public void setEngineOn(Boolean engineOn) {
        this.engineOn = engineOn;
        if (!engineOn) {
            setMoveAccumulator(0l);
        }
    }

    public synchronized Boolean isEngineOn() {
        return engineOn;
    }

    public Long getVelocity() {
        return velocity;
    }

    public void setVelocity(Long velocity) {
        this.velocity = velocity;
    }

    public void move(Long now) {

        if (!isPause()) {
            getMoveStrategy().perform(getDeltaHeartBeat());// getMoveAccumulator());
        }
        //      setMoveAccumulator(now);
        //this.setBounds(this.getNewBounds());
        setChanged();
        ///  notifyObservers(); TODO AFTER COLLISION AFTERCHECK
    }

    public MoveStrategy getMoveStrategy() {
        if (this.moveStrategy == null) {
            //ActiveUnit.class.getDeclaredClasses()
            this.setMoveStrategy(this.new MoveStrategy());
        }
        return moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public Bounds getNewBounds() {
        return newBounds;
    }

    public void setNewBounds(Bounds newBounds) {
        this.newBounds = newBounds;
    }

    public Long getMoveAccumulator() {
        return moveAccumulator;
    }

    public void setMoveAccumulator(Long moveAccumulator) {
        this.moveAccumulator = moveAccumulator;
    }


    public enum Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT;
    }

    protected class MoveStrategy {

        public void perform(Long deltaTime) {
            calcNewPosition(deltaTime);
        }

        protected Bounds calcNewPosition(Long deltaTime) { // this method can be override in children
            if (isEngineOn() && getVelocity() > 0) {
                setMoveAccumulator(getMoveAccumulator() + deltaTime);
                if (getMoveAccumulator() < 1000000000 / 64) {
                    setNewBounds(getBounds());
                    return getBounds();

                }
                setMoveAccumulator(0l);
                Double deltaPosition = (Double.valueOf(getVelocity())); // TODO MAY FROZE???
                switch (getDirection()) {
                    case UP:
                        newBounds = new BoundingBox(getBounds().getMinX(), getBounds().getMinY() - deltaPosition,
                                getBounds().getWidth(), getBounds().getHeight());
                        break;
                    case LEFT:
                        newBounds = new BoundingBox(getBounds().getMinX() - deltaPosition, getBounds().getMinY(),
                                getBounds().getWidth(), getBounds().getHeight());
                        break;
                    case DOWN:
                        newBounds = new BoundingBox(getBounds().getMinX(), getBounds().getMinY() + deltaPosition,
                                getBounds().getWidth(), getBounds().getHeight());
                        break;
                    case RIGHT:
                        newBounds = new BoundingBox(getBounds().getMinX() + deltaPosition, getBounds().getMinY(),
                                getBounds().getWidth(), getBounds().getHeight());
                        break;
                }
            }
            return newBounds;
        }
    }
}
