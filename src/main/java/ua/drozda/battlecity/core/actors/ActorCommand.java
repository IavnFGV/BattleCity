package ua.drozda.battlecity.core.actors;

/**
 * Created by GFH on 15.05.2015.
 */
public class ActorCommand {
    private Long nanoseconds;
    private Direction direction;
    private Integer velocity;
    private Boolean pause;
    private Boolean fire;

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public Boolean isFire() {
        return fire;
    }

    public void setFire(Boolean fire) {
        this.fire = fire;
    }

    public ActorCommand() {
        super();
    }

    public Long getNanoseconds() {
        return nanoseconds;
    }

    public void setNanoseconds(Long nanoseconds) {
        this.nanoseconds = nanoseconds;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Boolean isPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
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
