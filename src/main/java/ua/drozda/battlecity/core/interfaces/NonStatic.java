package ua.drozda.battlecity.core.interfaces;

/**
 * Created by GFH on 12.05.2015.
 */
public interface NonStatic<T> {
    T heartBeat(Object o) throws Exception;
}
