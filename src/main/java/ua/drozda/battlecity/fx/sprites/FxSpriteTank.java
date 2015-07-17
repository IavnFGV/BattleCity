package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.fx.FxWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteTank extends FxSprite<TankUnit> {
    private static Long tankToggleTimer = 50_000_000l;
    private static Map<TankUnit.TankType, HashMap<ActiveUnit.Direction, Rectangle2D[][]>> tankActiveMap = new
            HashMap<>();

    static {
        //for players tanks
        HashMap<ActiveUnit.Direction, Rectangle2D[][]> bufHashMap = getPlayerDirectionHashMap(FxWorld.firstPlayerZoneX);
        tankActiveMap.put(TankUnit.TankType.FIRST_PLAYER, bufHashMap);
        bufHashMap = getPlayerDirectionHashMap(FxWorld.secondPlayerZoneX);
        tankActiveMap.put(TankUnit.TankType.SECOND_PLAYER, bufHashMap);

    }

    public FxSpriteTank(TankUnit gameUnit) {
        super(gameUnit);
        maxToggle = 2;

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

    @Override
    public Boolean canToggle(Long now) {
        return (super.canToggle(now)) && (getGameUnit().isEngineOn()) && (now - toggleTime >= tankToggleTimer);
    }

    @Override
    protected int getMaxToggle() {
        return maxToggle;
    }

    @Override
    protected void updateSprite() {
        setViewPort(tankActiveMap.get(getGameUnit().getTankType()).get(getGameUnit().getDirection())[getGameUnit()
                .getStarCount()][curToggle]);
    }

    @Override
    public void doToggle(Long now) {
        curToggle = ++curToggle % getMaxToggle();
        updateSprite();
        toggleTime = now;
    }
}
