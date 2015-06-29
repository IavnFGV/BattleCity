package ua.drozda.battlecity.core.interfaces;

/**
 * Created by GFH on 12.05.2015.
 */
public interface Togglable<T> {
    T toggle(Long now);
}
