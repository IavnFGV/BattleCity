package ua.drozda.battlecity.core.interfaces;

/**
 * Created by GFH on 12.05.2015.
 */
public interface Updatable<T> {
    T update(Object... args) throws Exception; // first MUST be LONG - nanoseconds
}
