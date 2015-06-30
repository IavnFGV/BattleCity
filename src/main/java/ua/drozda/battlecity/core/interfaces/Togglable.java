package ua.drozda.battlecity.core.interfaces;

/**
 * Created by GFH on 12.05.2015.
 */
public interface Togglable {

    default void toggle(Long now) {
        if (!canToggle(now)) {
            return;
        }
        beforeToggle(now);
        doToggle(now);
        afterToggle(now);
    }

    default Boolean canToggle(Long now) {
        return true;
    }

    default void beforeToggle(Long now) {
        return;
    }

    void doToggle(Long now);

    default void afterToggle(Long now) {
        return;
    }

}
