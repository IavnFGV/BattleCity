package ua.drozda.battlecity.core.actors;

import java.util.Observable;

/**
 * Created by GFH on 15.05.2015.
 */
public class ActorCommand extends Observable {
    private Long nanoseconds;
    private Direction direction;
    private Double velocity;
    private Boolean pause;
    private Boolean fire;
    private Boolean bullet;
    private Boolean friendBullet;

    public ActorCommand() {
        super();
    }

    public Boolean isBullet() {
        return bullet;
    }

    public void setBullet(Boolean bullet) {
        this.bullet = bullet;
    }

    public Boolean isFriendBullet() {
        return friendBullet;
    }

    public void setFriendBullet(Boolean friendBullet) {
        this.friendBullet = friendBullet;
    }

    public Boolean isFire() {
        return fire;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
        setChanged();
    }

    public void setFire(Boolean fire) {
        this.fire = fire;
        setChanged();
    }

    public Long getNanoseconds() {
        return nanoseconds;
    }

    public void setNanoseconds(Long nanoseconds) {
        this.nanoseconds = nanoseconds;
        setChanged();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        setChanged();
    }

    public Boolean isPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
        setChanged();
    }

    @Override
    public String toString() {
        return "ActorCommand{" +
                "nanoseconds=" + nanoseconds +
                ", direction=" + direction +
                ", velocity=" + velocity +
                ", pause=" + pause +
                ", fire=" + fire +
                '}';
    }

}
