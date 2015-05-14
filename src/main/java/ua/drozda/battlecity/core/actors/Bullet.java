package ua.drozda.battlecity.core.actors;

/**
 * Created by GFH on 12.05.2015.
 */
public class Bullet extends Actor {
    private int power = 1;

    public Bullet(Direction direction, Integer velocity, int power) {
        super(direction, velocity);
        this.power = power;
    }
}
