package ua.drozda.battlecity.core.actors;

import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.interfaces.NonStatic;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 12.05.2015.
 */
public abstract class Actor implements NonStatic<Actor>, Observer {

    private boolean pause;
    private Integer velocity;
    private Integer acceleration;
    private Direction direction;
    private long lastUpdate;
    private Point2D position;
    protected Integer heartState;
    protected ActorState actorState;
    protected Long remainTimeInState;

    public ActorState getActorState() {
        return actorState;
    }

    public void setActorState(ActorState actorState) {
        remainTimeInState = ActorState.getTimeInState(actorState);
        this.actorState = actorState;
    }

    public Long getRemainTimeInState() {
        return remainTimeInState;
    }

    public void setRemainTimeInState(Long remainTimeInState) {
        if (remainTimeInState <= 0) {
            if (this.remainTimeInState != 0) {
                actorState = ActorState.nextState(actorState);
                this.remainTimeInState = remainTimeInState;
            }
        }
    }

    public abstract int getMaxToggle();

    public abstract void nextToggle();

    private ObservableProxy observableProxy = new ObservableProxy();
    private volatile Boolean ready; /*  true after Observer.Update()
                                        false after ObservableProxy.notifyObservers()*/

    protected ActorState getIntialState() {
        return ActorState.STATE_SPAWNING;
    }

    public Actor() {
        super();
        setActorState(getIntialState());
    }

    public Actor(Integer velocity) {
        this();
        this.velocity = velocity;
    }

    public synchronized void update(Observable o, Object arg) {
        //1 - time nanoseconds
        //2 - command (Direction)
        //3 - velocity (Integer)
        //4 - pause (Boolean)
        if (arg instanceof ActorCommand) {
            ActorCommand command = (ActorCommand) arg;
            if (command.isPause()) {
                pause = !pause;
            }
            if (!pause) {
                setRemainTimeInState(getRemainTimeInState() + lastUpdate - command.getNanoseconds());
                if (command.getDirection() == null) {
                    velocity = 0;
                } else {
                    direction = command.getDirection();
                }
                if (actorState != ActorState.STATE_SPAWNING) {
                    if (command.getVelocity() != null) {
                        velocity = command.getVelocity();
                    }
                }
                ready = true;
            }
            lastUpdate = command.getNanoseconds();
        }
    }

    public void calc() {
        if (!pause) {

        }
    }

    public void calcPosition() {
        if (velocity > 0) {

        }
    }

    public void addObserver(Observer o) {
        observableProxy.addObserver(o);
    }

    public void notifyObservers() {
        if (ready) {
            observableProxy.notifyObservers(this);
            ready = false;
        }
    }

    public class ObservableProxy extends Observable {
        public ObservableProxy() {
            super();
        }
    }

    public Point2D getPosition() {
        return position;
    }

    public Actor heartBeat() throws Exception {
        if (pause) {
            return this;
        }
        if (velocity != 0) {
            nextToggle();
        }
        return this;
    }
}
