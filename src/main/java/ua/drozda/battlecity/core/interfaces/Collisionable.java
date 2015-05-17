package ua.drozda.battlecity.core.interfaces;

/**
 * Created by GFH on 17.05.2015.
 */
public interface Collisionable<T, O> {
    public O collise(T other) throws Exception;
}
