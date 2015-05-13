package ua.drozda.battlecity.core.interfaces;

/**
 * Created by GFH on 12.05.2015.
 */
public interface Updatable {
    void update(Object... args); // first MUST be LONG - nanoseconds
}
