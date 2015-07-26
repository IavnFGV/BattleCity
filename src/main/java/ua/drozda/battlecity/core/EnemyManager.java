package ua.drozda.battlecity.core;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 26.07.2015.
 */
public class EnemyManager extends GameUnit {
    private static List<List<TankUnit.TankType>> enemiesForStage = new ArrayList();

    static {
    /*1*/
        {
            List<TankUnit.TankType> enemies = new ArrayList<>(
                    asList(new TankUnit.TankType[]{TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY_X,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY_X,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY,
                                    TankUnit.TankType.SIMPLE_ENEMY_X,
                                    TankUnit.TankType.FAST_ENEMY,
                                    TankUnit.TankType.FAST_ENEMY}
                    ));

        }
        enemiesForStage.add(new ArrayList<>());
    }

    public EnemyManager() {
        super(0, 0, 0, 0, 1, EnemyManager::CreationEvent, EnemyManager::DestroyingEvent);
    }

    protected static boolean CreationEvent(GameUnit gameUnit) {
        return true;
    }

    protected static boolean DestroyingEvent(GameUnit gameUnit) {
        return true;
    }

    protected class EnemyManagerHeartBeatStrategy extends BasicHeartBeatStrategy {
        @Override
        public void perform(Long deltaTime) {

        }
    }
}
