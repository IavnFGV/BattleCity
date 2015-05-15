package ua.drozda.battlecity.core.actors;

/**
 * Created by GFH on 12.05.2015.
 */
public class Bullet extends Actor {
    private int power = 1;

    @Override
    protected ActorState getIntialState() {
        return ActorState.STATE_ALIVE;
    }

    @Override
    public int getMaxToggle() {
        return 0;
    }

    @Override
    public void nextToggle() {
    }
}
