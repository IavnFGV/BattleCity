package ua.drozda.battlecity.core.actors;

/**
 * Created by GFH on 14.05.2015.
 */
public class Tank extends Actor {
    public Tank(ActorController controller) {
        super(controller);
    }

    public Tank(Direction direction, Integer velocity) {
        super(direction, velocity);
    }
}
