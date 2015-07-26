package ua.drozda.battlecity.core.interfaces;

import ua.drozda.battlecity.core.GameUnit;

import java.util.List;

/**
 * Created by GFH on 25.07.2015.
 */
public interface BattleCityStage {
    default void heartBeat(Long now) {
        for (GameUnit gameUnit : getUnitList()) {
            gameUnit.heartBeat(now);
        }
    }

    List<GameUnit> getUnitList();

    int getEnemiesCount();
}
