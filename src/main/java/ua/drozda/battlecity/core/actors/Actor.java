package ua.drozda.battlecity.core.actors;

import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.collisions.CollisionBounds;
import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.core.interfaces.Togglable;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 12.05.2015.
 */
public abstract class Actor implements Togglable<Actor>, Observer { // observable too through    private ObservableProxy observableProxy = new ObservableProxy();


    protected Integer heartState;
    protected Long remainTimeInState;
    protected ActorState actorState;
    private CollisionManager collisionManager;
    private MoveStrategy<? extends Actor> moveStrategy;
    private boolean pause;
    private Double velocity;
    private Integer acceleration;
    private Direction direction;
    private long lastUpdate;
    private long deltaTime;
    private Point2D position = Point2D.ZERO;
    private CollisionBounds collisionBounds;
    private ObservableProxy observableProxy = new ObservableProxy();
    private volatile Boolean ready; /*  true after Observer.Update()
                                        false after ObservableProxy.notifyObservers()*/

    public Actor(Double velocity, CollisionManager collisionManager) {
        this(collisionManager);
        this.velocity = velocity;
    }

    public Actor(CollisionManager collisionManager) {
        super();
        setCollisionManager(collisionManager);
        setActorState(getIntialState());
    }

    protected ActorState getIntialState() {
        return ActorState.STATE_SPAWNING;
    }

    public CollisionBounds getCollisionBounds() {
        return collisionBounds;
    }

    public void setCollisionBounds(CollisionBounds collisionBounds) {
        this.collisionBounds = collisionBounds;
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(long deltaTime) {
        this.deltaTime = deltaTime;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public void setCollisionManager(CollisionManager collisionManager) {
        this.collisionManager = collisionManager;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public ActorState getActorState() {
        return actorState;
    }

    public void setActorState(ActorState actorState) {
        remainTimeInState = ActorState.getTimeInState(actorState);
        this.actorState = actorState;
    }

    public abstract Integer getMaxToggle();

    public synchronized void update(Observable o, Object arg) {
        //1 - time nanoseconds
        //2 - command (Direction)
        //3 - velocity (Integer)
        //4 - pause (Boolean)
        performUpdate(arg);
    }

    protected void performUpdate(Object arg) {
        if ((arg instanceof ActorCommand)) {
            ActorCommand command = (ActorCommand) arg;
            if ((command.isPause() != null) && (command.isPause())) {
                pause = !pause;
            }
            if (!pause) {
                deltaTime = command.getNanoseconds() - lastUpdate;
                setRemainTimeInState(getRemainTimeInState() - deltaTime);
                if (command.getDirection() == null) {
                    velocity = 0.;
                } else {
                    direction = command.getDirection();
                }
                if (actorState != ActorState.STATE_SPAWNING) {
                    if (command.getVelocity() != null) {
                        velocity = command.getVelocity();
                    }
                }
                ready = true;
                this.calc();
                this.notifyObservers();
            }
            lastUpdate = command.getNanoseconds();
        }
    }

    public Long getRemainTimeInState() {
        return remainTimeInState;
    }

    public void setRemainTimeInState(Long remainTimeInState) {
        if (remainTimeInState <= 0) {
            actorState = ActorState.nextState(actorState);
        } else {
            this.remainTimeInState = remainTimeInState;
        }

    }

    protected void calc() {
        getMoveStrategy().performMove();
    }

    public void notifyObservers() {
        if (ready) {
            observableProxy.setChanged();
            observableProxy.notifyObservers(this);
            ready = false;
        }
    }

    public MoveStrategy<? extends Actor> getMoveStrategy() {
        return moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy<? extends Actor> moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void addObserver(Observer o) {
        observableProxy.addObserver(o);
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Actor toggle(Object o) throws Exception {
        if (pause) {
            return this;
        }
        if (velocity != 0) {
            nextToggle();
        }
        return this;
    }

    public abstract void nextToggle();

    public class ObservableProxy extends Observable {

        public ObservableProxy() {
            super();

        }

        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }

        public Actor getActor() {
            return Actor.this;
        }
    }
}
