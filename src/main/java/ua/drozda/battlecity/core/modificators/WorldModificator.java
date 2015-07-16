package ua.drozda.battlecity.core.modificators;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ua.drozda.battlecity.core.World;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by GFH on 15.07.2015.
 */
public abstract class WorldModificator {
    private World world;

    private ObjectProperty<State> stateProperty = new SimpleObjectProperty<>(State.ACTIVE);

    public WorldModificator(World world, State state) {
        this.world = world;
        stateProperty().setValue(state);
    }

    public ObjectProperty<State> stateProperty() {
        return stateProperty;
    }

    public State getState() {
        return stateProperty().getValue();
    }

    public void setState(State state) {
        stateProperty().setValue(state);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public abstract void handle(Long now);

    public enum State {
        ACTIVE,
        SUSPEND
    }

    public static final class WorldModificators {
        private List<WorldModificator> modificators = new LinkedList<>();

        public WorldModificator getModificator(Class<? extends WorldModificator> aClass) {
            return modificators.stream()
                    .filter(worldModificator -> worldModificator.getClass() == aClass)
                    .findFirst().get();
        }

        public List<WorldModificator> getMultiplyModificator(Class<? extends WorldModificator> aClass) {
            return modificators.stream()
                    .filter(worldModificator -> worldModificator.getClass() == aClass)
                    .collect(Collectors.toList());
        }


        public void addModificator(WorldModificator modificator) {
            modificators.add(modificator);
        }

        public void handle(Long now) {
            handle(now, null);
        }

        public void handle(Long now, State state) {
            Predicate<WorldModificator> onlyInState = modificator -> modificator.getState() == state;
            Predicate<WorldModificator> all = modificator -> true;
            modificators.stream()
                    .filter(state == null ? onlyInState : all)
                    .forEach(modificator -> modificator.handle(now));
        }
    }
}
