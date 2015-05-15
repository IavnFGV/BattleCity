package ua.drozda.battlecity.core.actors;

/**
 * Created by GFH on 14.05.2015.
 */
public class Tank extends Actor {

    @Override
    public int getMaxToggle() {
        return 2;
    }

    @Override
    public void nextToggle() {
        heartState = (heartState + 1) % getMaxToggle();
    }

}
