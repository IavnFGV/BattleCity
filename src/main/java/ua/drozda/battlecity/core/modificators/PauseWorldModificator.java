package ua.drozda.battlecity.core.modificators;

import ua.drozda.battlecity.core.World;

/**
 * Created by GFH on 16.07.2015.
 */
public class PauseWorldModificator extends WorldModificator {

    public PauseWorldModificator(World world, State state) {
        super(world, state);
        stateProperty().addListener((observable, oldValue, newValue) -> {
            if ((oldValue == State.SUSPEND) && (newValue == State.ACTIVE)) {
                getWorld().getUnitList().forEach(gameUnit -> gameUnit.setPause(true));
            } else {
                getWorld().getUnitList().forEach(gameUnit -> gameUnit.setPause(false));
            }

        });
    }

    @Override
    public void handle(Long now) {
    }
}
