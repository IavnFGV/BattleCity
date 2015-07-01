package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by GFH on 21.06.2015.
 */
public class FxTankUnit extends FxGameUnit {

    private static Map<TankUnit.TankType, HashMap<ActiveUnit.Direction, Rectangle2D[][]>> tankActiveMap = new
            HashMap<>();
    private static Long tankTogggleTimer = 1000000l;

    static {
        //for player tank
        HashMap<ActiveUnit.Direction, Rectangle2D[][]> bufHashMap = new HashMap<>();
        //UP
        Rectangle2D[][] rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 0, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * j + FxWorld.tankSize * i * 2, FxWorld.tankSize, FxWorld
                        .tankSize);
            }
        }
        bufHashMap.put(ActiveUnit.Direction.UP, rectangle2Ds);
        //RIGHT
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 1, FxWorld
                        .tankSize *
                        j + FxWorld.tankSize * i * 2, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(ActiveUnit.Direction.RIGHT, rectangle2Ds);
        //DOWN
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 2, FxWorld
                        .tankSize *
                        j + FxWorld.tankSize * i * 2, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(ActiveUnit.Direction.DOWN, rectangle2Ds);
        //LEFT
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 3, FxWorld
                        .tankSize * j + FxWorld.tankSize * i * 2, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(ActiveUnit.Direction.LEFT, rectangle2Ds);
        tankActiveMap.put(TankUnit.TankType.FIRST_PLAYER, bufHashMap);
    }

    ThreadFactory threadFactory =
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
            };
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(threadFactory);

    public FxTankUnit(TankUnit gameUnit) {
        super(gameUnit);
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (getTank().isEngineOn()) {
                    SoundManager.soundEventsQueue.add("moving");
                    //   SoundManager.playSound("moving");
                }
            }
        }, 0, 154, TimeUnit.MILLISECONDS);
        maxToggle = 2;
    }

    public TankUnit getTank() {
        return (TankUnit) getGameUnit();
    }

    @Override
    protected void nextSprite() {
        if (getGameUnit().getCurrentBasicState() == GameUnit.BasicState.ACTIVE) {
            if (gameUnit instanceof TankUnit) {
                TankUnit tankUnit = (TankUnit) gameUnit;
                setCurSprite(tankActiveMap.get(tankUnit.getTankType()).get(tankUnit.getDirection())[tankUnit
                        .getStarCount()][getCurToggle()]);
            }
        }
    }

    @Override
    protected void handleSounds() {
//        if(getTank().isEngineOn()){
//            SoundManager.soundEventsQueue.add("moving");
//        }
    }

    @Override
    public void doToggle(Long now) {
        curToggle = ++curToggle % maxToggle;
        updateSprite();
        toggleTime = now;
    }

    @Override
    public Boolean canToggle(Long now) {
        return (getTank().isEngineOn()) && (now - toggleTime >= tankTogggleTimer);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
