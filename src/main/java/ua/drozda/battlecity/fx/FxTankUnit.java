package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private static Long tankToggleTimer = 50000000l;
    private static Rectangle2D[] shieldSprites = {new Rectangle2D(228, 288, FxWorld.tankSize, FxWorld.tankSize),
            new Rectangle2D(260, 288, FxWorld.tankSize, FxWorld.tankSize)};

    static {
        //for players tanks
        HashMap<ActiveUnit.Direction, Rectangle2D[][]> bufHashMap = getPlayerDirectionHashMap(FxWorld.firstPlayerZoneX);
        tankActiveMap.put(TankUnit.TankType.FIRST_PLAYER, bufHashMap);
        bufHashMap = getPlayerDirectionHashMap(FxWorld.secondPlayerZoneX);
        tankActiveMap.put(TankUnit.TankType.SECOND_PLAYER, bufHashMap);

    }

    ThreadFactory threadFactory =
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
            };
    private ImageView shield = new ImageView(FxWorld.sprites);
    private Rectangle2D curShieldSprite;
    //    private static HashMap<ActiveUnit.Direction, Rectangle2D[][]> loadSprites(Integer offset) {
//
//    }
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(threadFactory);

    public FxTankUnit(TankUnit gameUnit) {
        super(gameUnit);
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (getTank().isEngineOn()) {
                    //     SoundManager.soundEventsQueue.add("moving");
                    SoundManager.playSound("moving");
                }
            }
        }, 0, 154, TimeUnit.MILLISECONDS);
        maxToggle = 2;
    }

    public TankUnit getTank() {
        return (TankUnit) getGameUnit();
    }

    private static HashMap<ActiveUnit.Direction, Rectangle2D[][]> getPlayerDirectionHashMap(Integer offsetX) {
        HashMap<ActiveUnit.Direction, Rectangle2D[][]> hashMap = new HashMap<>();
        //UP
        Rectangle2D[][] rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(offsetX + FxWorld.tankSize * 0, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * j + FxWorld.tankSize * i * 2, FxWorld.tankSize, FxWorld
                        .tankSize);
            }
        }
        hashMap.put(ActiveUnit.Direction.UP, rectangle2Ds);
        //RIGHT
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(offsetX + FxWorld.tankSize * 1, FxWorld
                        .tankSize *
                        j + FxWorld.tankSize * i * 2, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        hashMap.put(ActiveUnit.Direction.RIGHT, rectangle2Ds);
        //DOWN
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(offsetX + FxWorld.tankSize * 2, FxWorld
                        .tankSize *
                        j + FxWorld.tankSize * i * 2, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        hashMap.put(ActiveUnit.Direction.DOWN, rectangle2Ds);
        //LEFT
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(offsetX + FxWorld.tankSize * 3, FxWorld
                        .tankSize * j + FxWorld.tankSize * i * 2, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        hashMap.put(ActiveUnit.Direction.LEFT, rectangle2Ds);
        return hashMap;
    }

    public Rectangle2D getCurShieldSprite() {
        return curShieldSprite;
    }

    public void setCurShieldSprite(Rectangle2D curShieldSprite) {
        this.curShieldSprite = curShieldSprite;
        shield.setViewport(curShieldSprite);
    }

    @Override
    public List<ImageView> getImageViews() {
        return Arrays.asList(new ImageView[]{imageView, shield});
    }

    @Override
    protected void updateSprite() {
        super.updateSprite();
        shield.relocate(gameUnit.getBounds().getMinX(), gameUnit.getBounds().getMinY());
    }

    @Override
    protected void nextSprite() {
        if (getGameUnit().getCurrentBasicState() == GameUnit.BasicState.ACTIVE) {
            if ((gameUnit instanceof TankUnit) && // must be current value!!!
                    (((TankUnit) gameUnit).isEngineOn())
                    ) {
                TankUnit tankUnit = (TankUnit) gameUnit;
                setCurSprite(tankActiveMap.get(tankUnit.getTankType()).get(tankUnit.getDirection())[tankUnit
                        .getStarCount()][getCurToggle()]);
                shield.setVisible(tankUnit.isShield());
                if (tankUnit.isShield()) {
                    setCurShieldSprite(shieldSprites[getCurToggle()]);
                }
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
        return (getTank().isEngineOn() || getTank().isShield()) && (now - toggleTime >= tankToggleTimer);
    }
}
