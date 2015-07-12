package ua.drozda.battlecity.core;

import ua.drozda.battlecity.core.collisions.CollisionManager;

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
    protected CollisionManager collisionManager;
    //  private Bounds newBounds;
    private Direction direction;
    private Long velocity = 8L;
    private Integer cellSize = 16;//bad idea TODO maybe we can use EasyDI lib???

    public ActiveUnit(double x, double y, double width, double height, Integer lives, BasicState
            currentBasicState, Direction direction, Long velocity, Function<GameUnit, Boolean> registerAction,
                      Function<GameUnit, Boolean> unRegisterAction, CollisionManager collisionManager) {
        super(x, y, width, height, lives, currentBasicState, registerAction, unRegisterAction, true);
        this.setDirection(direction);
        this.setVelocity(velocity);
        // this.setNewBounds(getBounds());

//        xProperty().addListener((observable, oldValue, newValue) -> {
//            this.setBounds(new BoundingBox(newValue.doubleValue(), this.getY(), this.width, this.height));
//
//        });
//        yProperty().addListener((observable, oldValue, newValue) -> {
//            this.setBounds(new BoundingBox(this.getX(), newValue.doubleValue(), this.width, this.height));
//        });


        this.collisionManager = collisionManager;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (direction != getDirection()) {
            fixPosition();
        }
        this.direction = direction;

    }

    private void fixPosition() {
        Long x = nearest(getX(), cellSize);
        Long y = nearest(getY(), cellSize);
        Double newX = getX();
        Double newY = getY();

        if (abs(newX - x) < (cellSize / 2 + 1)) {
            newX = Double.valueOf(x);
        }
        if (abs(newY - y) < (cellSize / 2 + 1)) {
            newY = Double.valueOf(y);
        }
//        setNewBounds(new BoundingBox(newX, newY, cellSize * 2, cellSize * 2));
        if (collisionManager != null) {
            collisionManager.fixPosition(this, newX, newY);
        }
    }

    private long nearest(double num, Integer base) {
        return (round(num / (base * 1.)) * base);
    }

    @Override
    public String toString() {
        return "ActiveUnit{" +
                ", engineOn=" + engineOn +
                ", moveStrategy=" + moveStrategy +
                ", moveAccumulator=" + moveAccumulator +
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


        protected double newX;
        protected double newY;

        public void perform(Long deltaTime) {
            if (calcNewPosition(deltaTime)) {
                confirmNewPosition();
            }
        }

        protected boolean calcNewPosition(Long deltaTime) { // this method can be override in children
            setMoveAccumulator(getMoveAccumulator() + deltaTime);
            if (isEngineOn() && (getMoveAccumulator() >= 1000000000 / 64)) {
                newX = ActiveUnit.this.getX();
                newY = ActiveUnit.this.getY();
                double deltaPosition = (Double.valueOf(getVelocity()));
                setMoveAccumulator(0l);
                switch (getDirection()) {
                    case UP:
                        newY = (getY() - deltaPosition);
                        break;
                    case LEFT:
                        newX = (getX() - deltaPosition);
                        break;
                    case DOWN:
                        newY = (getY() + deltaPosition);
                        break;
                    case RIGHT:
                        newX = (getX() + deltaPosition);
                        break;
                }
                return true;
            }
            return false;
        }

        protected void confirmNewPosition() {
            collisionManager.newPosition(ActiveUnit.this, newX, newY);
        }
    }
}

