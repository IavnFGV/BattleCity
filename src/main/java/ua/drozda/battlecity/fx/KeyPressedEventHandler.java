package ua.drozda.battlecity.fx;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by GFH on 21.06.2015.
 */
public class KeyPressedEventHandler implements EventHandler<KeyEvent> {

    Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);

    @Override
    public void handle(KeyEvent event) {

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            synchronized (pressedKeys) {
                pressedKeys.add(event.getCode());
            }
        }
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            synchronized (pressedKeys) {
                pressedKeys.remove(event.getCode());
            }
        }
    }

    public boolean isKeyDown(KeyCode keyCode) {
        synchronized (pressedKeys) {
            return pressedKeys.contains(keyCode);
        }
    }

    public boolean isAnyKeyDown(Set<KeyCode> keyCodes) {
        synchronized (pressedKeys) {
            return IntersectionHelper.intersect(pressedKeys, keyCodes);
        }
    }

    public boolean wasKeyPressed(KeyCode keyCode) { // remove from set after answer
        synchronized (pressedKeys) {
            if (pressedKeys.contains(keyCode)) {
                pressedKeys.remove(keyCode);
                return true;
            }
            return false;
        }
    }

    private static class IntersectionHelper {
        static boolean intersect(Set<KeyCode> s1, Set<KeyCode> s2) {
            Set<KeyCode> intersection = EnumSet.copyOf(s1);
            intersection.retainAll(s2);
            return !intersection.isEmpty();
        }
    }
}
